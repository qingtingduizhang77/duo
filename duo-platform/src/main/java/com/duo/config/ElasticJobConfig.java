package com.duo.config;
//
//import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
//import com.dangdang.ddframe.job.api.script.ScriptJob;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//import com.dangdang.ddframe.job.config.JobCoreConfiguration;
//import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
//import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
//import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
//import com.dangdang.ddframe.job.lite.api.JobScheduler;
//import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
/**
 * @author [ Yonsin ] on [2019/4/13]
 * @Description： [ ElasticJob添加zookeeper配置类，和动态添加方法 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
//@Configuration
@Slf4j
public class ElasticJobConfig {
    
//    @Bean(initMethod = "init")
//    public ZookeeperRegistryCenter regCenter(@Value("${elaticjob.zookeeper.server-lists}") final String serverList, @Value("${elaticjob.zookeeper.namespace}") final String namespace) {
//        log.info("ElasticJob注册Zookeeper："+serverList);
//        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
//    }
//
//    @Autowired
//    private ZookeeperRegistryCenter regCenter;
//
//    /**
//     * 动态添加
//     * @param jobClass
//     * @param cron
//     * @param shardingTotalCount
//     * @param shardingItemParameters
//       *  例子
//       *   elasticJobConfig.addSimpleJobScheduler(new MySimpleJob().getClass(),"* * * * * ?",shardingTotalCount,"0=A,1=B");
//     */
//     public void addSimpleJobScheduler(final Class<? extends SimpleJob> jobClass,
//                                      final String cron,
//                                      final int shardingTotalCount,
//                                      final String shardingItemParameters){
//
//         log.info("ElasticJob 动态添加Simple任务："+jobClass.getClass().getName());
//         // 定义作业核心配置
//        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).jobParameter("job参数").build();
//         // 定义SIMPLE类型配置
//        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, jobClass.getCanonicalName());
//         // 定义Lite作业根配置
//        JobScheduler jobScheduler = new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(simpleJobConfig).build());
//         // 启动
//        jobScheduler.init();
//     }
//
//    public void addDataflowJobScheduler(final Class<? extends DataflowJob> jobClass,
//                                      final String cron,
//                                      final int shardingTotalCount,
//                                      final String shardingItemParameters){
//
//        log.info("ElasticJob 动态添加Dataflow任务："+jobClass.getClass().getName());
//        // 定义作业核心配置
//        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).jobParameter("job参数").build();
//        // 定义Dataflow类型配置
//        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(coreConfig, jobClass.getCanonicalName(),true);
//        // 定义Lite作业根配置
//        JobScheduler jobScheduler = new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(dataflowJobConfig).build());
//        // 启动
//        jobScheduler.init();
//    }
//
//    public void addScriptJobScheduler(final Class<? extends ScriptJob> jobClass,
//                                      final String cron,
//                                      final int shardingTotalCount,
//                                      final String shardingItemParameters){
//
//        log.info("ElasticJob 动态添加Script任务："+jobClass.getClass().getName());
//        // 定义作业核心配置
//        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).jobParameter("job参数").build();
//        // 定义Script类型配置
//        ScriptJobConfiguration scriptJobConfig = new ScriptJobConfiguration(coreConfig, jobClass.getCanonicalName());
//        // 定义Lite作业根配置
//        JobScheduler jobScheduler = new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(scriptJobConfig).build());
//        // 启动
//        jobScheduler.init();
//    }
}
