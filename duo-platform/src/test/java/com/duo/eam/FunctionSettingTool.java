package com.duo.eam;

import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseService;
import com.duo.core.LogCenter;
import com.duo.core.utils.E2MapUtil;
import com.duo.modules.common.service.CommonService;
import com.duo.modules.common.service.LayoutService;
//import com.duo.modules.eam.entity.*;
//import com.duo.modules.eam.mapper.FunBaseMapper;
//import com.duo.modules.eam.mapper.FunColMapper;
//import com.duo.modules.eam.mapper.FunEventMapper;
//import com.duo.modules.eam.mapper.FunallModuleMapper;
import com.duo.modules.tool.entity.ToolDbsource;
import com.duo.modules.tool.mapper.*;
import org.junit.jupiter.api.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 将EAM系统的表结构导入新系统
 */
//这里只写SpringBootTest这个注解; 如果是junit4的话, 就要加上@RunWith(SpringRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionSettingTool {
//    @Resource
//    public LogCenter log;
//    //eam
//    @Autowired
//    private CommonService baseService;
//    @Autowired
//    private FunBaseMapper funBaseMapper;
//    @Autowired
//    private FunEventMapper funEventMapper;
//    @Autowired
//    private FunColMapper funColMapper;
//    @Autowired
//    private FunallModuleMapper funallModuleMapper;
//    @Autowired
//    private LayoutService layoutService;
//
//
//    //defalt
//    @Autowired
//    private ToolModuleMapper toolModuleMapper;
//    @Autowired
//    private ToolFunctionMapper toolFunctionMapper;
//    @Autowired
//    private ToolFunctionColumnMapper toolFunctionColumnMapper;
//
//    @Test
//    public void test2(){
//        //数据源设置
//        ToolDbsource dbsource=new ToolDbsource();
//        dbsource.setDbsource_name("eam");
//        dbsource.setJdbc_url("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
//        dbsource.setDb_driver("oracle.jdbc.OracleDriver");
//        dbsource.setUser_name("eam2020");
//        dbsource.setUser_password("eam");
//        DynamicDataSource.managerDbSource(dbsource);
//
//        //获取数据
//        DynamicDataSource.setDataSource("eam");//设置指定数据源
//        List<PropApply> apply=(List<PropApply>)baseService.getMapper("prop_apply").selectAll();
//        System.out.println(apply.size());
//        DynamicDataSource.clearDataSource();
//        log.info("CommonService gridSave:"   );
//    }
//
//
//    public void importFunction() throws Exception {
//        //数据源设置
//        ToolDbsource dbsource=new ToolDbsource();
//        dbsource.setDbsource_name("eam");
//        dbsource.setJdbc_url("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
//        dbsource.setDb_driver("oracle.jdbc.OracleDriver");
//        dbsource.setUser_name("eam2020");
//        dbsource.setUser_password("eam");
//        DynamicDataSource.managerDbSource(dbsource);
//
//        //获取数据
//        DynamicDataSource.setDataSource("eam");//设置指定数据源
//        List<FunBase> funList=funBaseMapper.selectAll();//获取功能清单
//        List<FunallModule> funModuleList=funallModuleMapper.selectAll();//获取模块
//        List<FunCol> funColList=funColMapper.selectAll();//获取功能字段
//        List<FunEvent> funEvenList=funEventMapper.selectAll();//获取事件
//
//        //转移模块清单
//        for(int i=0;i<1;i++){
//            FunallModule module=funModuleList.get(i);
//            Map<String,Object> mp= E2MapUtil.E2Map(module,FunallModule.class);
//
//            for (Map.Entry<String, Object> entry : mp.entrySet()) {
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            }
//        }
//
//
//
//        //  System.out.println("columnList==="+funList.get(0).getFun_name());
//        DynamicDataSource.clearDataSource();
//
//        DynamicDataSource.setDataSource(DataSourceNames.PLATFORM);//设置指定数据源
//
//
//        DynamicDataSource.clearDataSource();
//    }
}

