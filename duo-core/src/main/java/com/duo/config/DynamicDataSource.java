package com.duo.config;

import com.duo.DataSourceNames;
import com.duo.core.utils.StringUtils;
import com.duo.modules.tool.entity.ToolDbsource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源
 * @author yonsin
 * @date 2019/3/19 1:03
 */
@Slf4j
@NoArgsConstructor
@Order(1)
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static String lastDb=DataSourceNames.DEFAULT;
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
//    private static Map<Object,Object> dataSourceMap=new HashMap<Object, Object>();
    public static ConcurrentHashMap<String, DataSource> dsMap=new ConcurrentHashMap();
    public static ConcurrentHashMap<String, SqlSessionFactoryBean> dsSqlSessionMap=new ConcurrentHashMap();
    private static DynamicDataSource dynamicDataSource;
    private static byte[] lock=new byte[0];


    @PostConstruct
    public void init() {
        dynamicDataSource = this;

    }
//    // 重写setTargetDataSources，通过入参targetDataSources进行数据源的添加
//    @Override
//    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
//        super.setTargetDataSources(targetDataSources);
//        dataSourceMap.putAll(targetDataSources);
//        super.afterPropertiesSet();
//    }

//    // 单例模式，保证获取到都是同一个对象，
//    public static synchronized DynamicDataSource getInstance(){
//        if(dynamicDataSource==null){
//            synchronized (lock){
//                if(dynamicDataSource==null){
//                    dynamicDataSource=new DynamicDataSource();
//                }
//            }
//        }
//        return dynamicDataSource;
//    }
//    // 获取到原有的多数据源，并从该数据源基础上添加一个或多个数据源后，
//    // 通过上面的setTargetDataSources进行加载
//    public Map<Object, Object> getDataSourceMap() {
//        return dataSourceMap;
//    }

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<String, DataSource> targetDataSources) {
        if(dsMap.isEmpty()){
            targetDataSources.keySet().forEach(key -> {
                try {
                    this.dsMap.put(key,targetDataSources.get(key));
                    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
                    sqlSessionFactoryBean.setDataSource(targetDataSources.get(key));

                    //手工配置
                    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
                    configuration.setCallSettersOnNulls(true);
                    configuration.setMapUnderscoreToCamelCase(true);
                    configuration.setCacheEnabled(true);
                    configuration.setLazyLoadingEnabled(true);
                    configuration.setJdbcTypeForNull(JdbcType.NULL);
                    configuration.setAggressiveLazyLoading(true);
                    sqlSessionFactoryBean.setConfiguration(configuration);

                    dsSqlSessionMap.put(key,sqlSessionFactoryBean);
                    log.info("DataSource::" + key
                            + "   DBType=" + targetDataSources.get(key).getConnection().getMetaData().getDatabaseProductName()
                            +"    version=" +targetDataSources.get(key).getConnection().getMetaData().getDatabaseProductVersion());
                } catch (SQLException e) {
                   // e.printStackTrace();
                }
            });
        }
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(new HashMap<>(targetDataSources));
        super.afterPropertiesSet();
    }


    /**
     * 动态失效数据源
     */
    public static boolean removeDbSource(String key){
            //判断是否已存在，存在则删除
            dsMap.remove(key);
            dsSqlSessionMap.remove(key);
            for (Map.Entry<String, DataSource> entry : dsMap.entrySet()) {
                System.out.println("DbSource Key = " + entry.getKey()  );
            }
            return true;
    }

    /**
     * 动态添加数据源
     */
    public static boolean managerDbSource(ToolDbsource toolDbsource){
        try {
              String key=toolDbsource.getDbsource_name();
              //判断是否已存在，存在则删除
               if(dsMap.containsKey(key)) dsMap.remove(key);
                if(dsSqlSessionMap.containsKey(key)) dsSqlSessionMap.remove(key);
              //创建数据源
              DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
              dataSourceBuilder.type(com.alibaba.druid.pool.DruidDataSource.class);//druid连接池
              dataSourceBuilder.driverClassName(toolDbsource.getDb_driver());
              dataSourceBuilder.url(toolDbsource.getJdbc_url());
              dataSourceBuilder.username(toolDbsource.getUser_name());
              dataSourceBuilder.password(StringUtils.isEmpty(toolDbsource.getUser_password())?"":toolDbsource.getUser_password());
              DataSource db=dataSourceBuilder.build();
            dsMap.put(key,db);
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            //手工配置
            Configuration configuration = new Configuration();
            configuration.setCallSettersOnNulls(true);
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setCacheEnabled(true);
            configuration.setLazyLoadingEnabled(true);
            configuration.setJdbcTypeForNull(JdbcType.NULL);
            configuration.setAggressiveLazyLoading(true);
            sqlSessionFactoryBean.setConfiguration(configuration);

            sqlSessionFactoryBean.setDataSource(db);
            dsSqlSessionMap.put(key,sqlSessionFactoryBean);

            dynamicDataSource.setTargetDataSources(new HashMap<>(dsMap));
            dynamicDataSource.afterPropertiesSet();


            for (Map.Entry<String, DataSource> entry : dsMap.entrySet()) {
                System.out.println("DbSource Key = " + entry.getKey()  );
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public static void setDataSource(String dataSource) {
        if(dataSource.equals(contextHolder.get())) return;
        log.info("setDataSource........"+dataSource);
        lastDb=contextHolder.get();
        contextHolder.set(dataSource);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        if(contextHolder.get()==null||contextHolder.get().equals(lastDb)) return;
        log.info("clearDataSource........");
        contextHolder.remove();
        log.info("revertDataSource........"+lastDb);
        contextHolder.set(lastDb==null?DataSourceNames.DEFAULT:lastDb);
        lastDb = DataSourceNames.DEFAULT;
    }

    /**
     * 连接数据源前,调用该方法
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();

//        //1.获取手动设置的数据源参数DataSourceBean
//        DataSourceBean dataSourceBean = DataSourceContext.getDataSource();
//        if(dataSourceBean == null) {
//            return null;
//        }
//        try {
//            //2.获取AbstractRoutingDataSource的targetDataSources属性,该属性存放数据源属性
//            Map<Object, Object> targetSourceMap = getTargetSource();
//            synchronized(this) {
//                /*
//                 * 3.判断targetDataSources中是否已经存在要设置的数据源bean
//                 * 存在的话,则直接返回beanName
//                 *
//                 */
//                if(!targetSourceMap.keySet().contains(dataSourceBean.getBeanName())) {
//					/*不存在，则进行以下几步
//					3.1 先在spring容器中创建该数据源bean
//					*/
//                    Object dataSource = createDataSource(dataSourceBean);
//                    //3.2 在创建后的bean,放入到targetDataSources Map中
//                    targetSourceMap.put(dataSourceBean.getBeanName(), dataSource);
//					/*
//					 * 3.3 通知spring有bean更新
//					 * 主要更新AbstractRoutingDataSource的resolvedDefaultDataSource(Map)属性,
//					 * 更新完以后,AbstractRoutingDataSource的determineTargetDataSource()中,才能找到数据源
//					 * 代码如下：
//					 * Object lookupKey = determineCurrentLookupKey();
//					   DataSource dataSource = this.resolvedDataSources.get(lookupKey);
//					 */
//                    super.afterPropertiesSet();
//                }
//            }
//            for(Map.Entry<Object, Object> entry : targetSourceMap.entrySet()) {
//                System.out.println(entry.getKey()+"-"+entry.getValue());
//            }
//            return dataSourceBean.getBeanName();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }
    //通过反射获取AbstractRoutingDataSource的targetDataSources属性
    @SuppressWarnings("unchecked")
    public Map<Object, Object> getTargetSource() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = AbstractRoutingDataSource.class.getDeclaredField("targetDataSources");
        field.setAccessible(true);
        return (Map<Object, Object>) field.get(this);
    }
}
