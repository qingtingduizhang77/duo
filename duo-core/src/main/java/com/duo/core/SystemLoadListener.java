package com.duo.core;

import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.annotation.DataSource;
import com.duo.core.utils.FindClassInPackageUtil;
import com.duo.core.utils.PropertiesUtil;
import com.duo.modules.tool.entity.ToolDbsource;
import com.duo.modules.tool.mapper.ToolDbsourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

/**
 * @author [ Yonsin ] on [2019/5/20]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Component
public class SystemLoadListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ToolDbsourceMapper toolDbsourceMapper;

    public static String _ip="";//当前服务器IP


    @Override
    @DataSource(name="platform")
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("初始化DUO配置文件信息:");
//        if(contextRefreshedEvent.getApplicationContext().getParent() != null){
//            return;
//        }
        initProperties();
        //获取服务器IP
        getIP();
        log.info("服务启动-加载GloblContext信息");
        log.info("ip:"+ SystemLoadListener._ip);

        log.info("LoadClass Begin." );

        Properties prop= PropertiesUtil.getInstance().load("entity-list");
        Iterator<Map.Entry<Object, Object>> it = prop.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            Object dir = entry.getValue();
            if(dir!=null&&!dir.equals("")) {
                FindClassInPackageUtil.getClasses(dir+".entity");
                FindClassInPackageUtil.getClasses(dir+".mapper");
            }
        }
        log.info("LoadClass End." );
        log.info("LoadDbSource Begin." );
        ToolDbsource query=new ToolDbsource();
        query.setDbsource_type("system");
      //  query.setAuditing("1");
        List<ToolDbsource> dbsourceLs=toolDbsourceMapper.select(query);
        try{
            for(ToolDbsource dbsource:dbsourceLs) {
                dbsource.setLoad_status("0");
                log.info("LoadDbSource ....." + dbsource.getDbsource_name());
                if ("1".equals(dbsource.getAuditing())) {//生效的
                    if (DynamicDataSource.dsMap.containsKey(dbsource.getDbsource_name())) {
                        log.info("已加载 ....."  );
                        dbsource.setLoad_status("1");
                    } else {
                        if (DynamicDataSource.managerDbSource(dbsource)) { //增加数据源
                            dbsource.setLoad_status("1");
                            log.info("已加载 ....."  );
                        }else{
                            log.info("加载失败 ....."  );
                        }
                    }
                }
                toolDbsourceMapper.updateByPrimaryKey(dbsource);
            }
        }catch(Exception e){
            log.error("加载数据源出错！",e);
        }
        log.info("LoadDbSource End." );
    }

    public void initProperties(){
        //初始化DUO配置文件信息
        Properties prop= PropertiesUtil.getInstance().load("duo");
        Iterator<Map.Entry<Object, Object>> it = prop.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if(value!=null&&!value.equals("")){
                MemCache.setSystemParameter(String.valueOf(key).trim(),String.valueOf(value).trim());
                log.info("----- "+key+"="+value);
            }
        }

        SQLFormatProperties sqlprop= SpringContextHolder.getBean("SQLFormatProperties");
        MemCache.setSystemParameter("fileServerType",sqlprop.getFileServerType());
        MemCache.setSystemParameter("filePath",sqlprop.getFilePath());

        Properties properties = System.getProperties();
        log.info(properties.get("user.dir").toString());

    }


    //获取服务器IP
    public void getIP() {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        if (ip.getHostAddress() != null && !ip.getHostAddress().equals("127.0.0.1")) {
                            if (SystemLoadListener._ip.length() > 190) {
                                continue;
                            }
                            SystemLoadListener._ip += (SystemLoadListener._ip.equals("") ? "" : ";")
                                    + ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
