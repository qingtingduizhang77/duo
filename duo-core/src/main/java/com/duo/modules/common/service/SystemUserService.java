package com.duo.modules.common.service;

import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.utils.E2MapUtil;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.PageResultSet;
import com.duo.modules.system.entity.SystemRole;
import com.duo.modules.system.entity.SystemRoleUser;
import com.duo.modules.system.entity.SystemUser;
import com.duo.modules.system.mapper.SystemRoleMapper;
import com.duo.modules.system.mapper.SystemRoleUserMapper;
import com.duo.modules.system.mapper.SystemUserMapper;
import com.duo.modules.tool.entity.ToolFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author [ Yonsin ] on [2019/8/26]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Service
public class SystemUserService extends BaseService{
    @Autowired
    private SystemRoleMapper systemRoleMapper;
    @Autowired
    private SystemRoleUserMapper systemRoleUserMapper;
    @Autowired
    private SystemUserMapper  systemUserMapper;
    @Autowired
    private CommonQueryService commonQueryService;
    @Autowired
    private CommonService commonService;

    public SystemUser findOne(String userid) {

        return systemUserMapper.selectByPrimaryKey(userid);
    }


    public SystemUser findByUsername(String username) {
        if(username==null||username.equals("")) return null;
        SystemUser user = new SystemUser();
        user.setUser_code(username);
        return systemUserMapper.selectOne(user);
    }

    public SystemUser getUser(String userId){
        return systemUserMapper.selectByPrimaryKey(userId);
    }


    public SystemUser getUser(SystemUser record){
        return systemUserMapper.selectOne(record);
    }


    public void setUser(SystemUser user){
        systemUserMapper.updateByPrimaryKey(user);
    }

    //是否有systemId访问权限
    public boolean hasSystemRule(String userCode, String systemId){
        if(StringUtils.isEmpty(userCode)) return false;
       if(userCode.equals("admin")) return true;
        SystemUser user=findByUsername(userCode);
        if(user==null)  return false;
        SystemRoleUser query=new SystemRoleUser();
        query.setUser_id(user.getUser_id());
        List<SystemRoleUser> rolels=systemRoleUserMapper.select(query);
        if(rolels==null||rolels.size()==0) return false;
        List<String> roleIds=new ArrayList<>();
        for(SystemRoleUser role:rolels){
            roleIds.add(role.getRole_id());
        }
        Example example=new Example(SystemRole.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("role_id",roleIds);
        List<SystemRole> ls=systemRoleMapper.selectByExample(example);
        for(SystemRole r:ls){
            if(StringUtils.isEmpty(r.getSystem_id())) return true;// 角色无限制
            if(r.getSystem_id().equals(systemId)) return true;
        }
        return false;
    }

    /**
     *  角色授权管理——功能授权
     * @param funId
     * @param requestMap
     * @return
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    public PageResultSet<Map<String,Object>> getFunctionPage(String funId, Map<String, Object> requestMap) throws Exception {
        PageResultSet<Map<String,Object>> resultSet = commonQueryService.findBySQLPage(funId,requestMap);
        List<Map<String, Object>> rowList =resultSet.getRows();
        List<Map<String, Object>> newRowList=new ArrayList<Map<String, Object>>();
        if(rowList!=null&&rowList.size()>0){//处理结果集
            String onmenu="",towmenu="";
            for(Map<String, Object> mp:rowList){
                if(onmenu.equals("")||!onmenu.equals(mp.get("one_module"))) {
                    onmenu=(String)mp.get("one_module");
                }else{
                    mp.put("one_module","");
                }
                if(towmenu.equals("")||!towmenu.equals(mp.get("module_name"))) {
                    towmenu=(String)mp.get("module_name");
                }else{
                    mp.put("module_name","");
                }

                newRowList.add(mp);
            }

        }
        resultSet.setRows(newRowList);
        return resultSet;
    }


}
