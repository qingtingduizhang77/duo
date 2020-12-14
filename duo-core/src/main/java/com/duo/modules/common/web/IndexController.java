package com.duo.modules.common.web;

import com.duo.MemCache;
import com.duo.core.BaseController;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.ShiroUtils;
import com.duo.core.utils.StringUtils;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.system.entity.SystemDept;
import com.duo.modules.system.entity.SystemMajorfunc;
import com.duo.modules.tool.entity.*;
import com.duo.modules.tool.mapper.ToolProjectMapper;
import com.duo.modules.tool.mapper.ToolProjectSystemMapper;
import com.duo.modules.tool.mapper.ToolSystemModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.duo.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 
 * @Description： 首页
 * @author [ Yonsin ] on [2019年1月31日上午10:39:17]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Controller
@Slf4j
public class IndexController extends BaseController{
    @Autowired
    private ToolProjectMapper toolProjectMapper;
    @Autowired
    private ToolProjectSystemMapper toolProjectSystemMapper;
    @Autowired
    private ToolSystemModuleMapper toolSystemModuleMapper;


    /**
     * 首页管理界面
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String home(Model model) {

        return "base/home";
    }

    @GetMapping("/home-company")
    public String homecompany(Model model) {

        return "base/home-company";
    }

    @GetMapping("/home-shop")
    public String homeshop(Model model) {

        return "base/home-shop";
    }

    @GetMapping("/home-doctor")
    public String homedoctor(Model model) {

        return "base/home-doctor";
    }

    @GetMapping("/")
    public String index(Model model) throws Exception {
        System.out.println("index ......");
        String ProjectID= ShiroUtils.getProjectId();
        String SystemID=ShiroUtils.getSystemId();

//        System.out.println("index ProjectID = " + ShiroUtils.getSessionAttribute("ProjectID") + ", SystemID = " + ShiroUtils.getSessionAttribute("SystemID"));
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        StringBuilder dom = new StringBuilder(""); //大屏界面待实现

        StringBuilder newdom = new StringBuilder("");//功能菜单
        StringBuilder majordom = new StringBuilder("");//常用功能
        getMenuTreeNew(model,newdom,majordom,getMajorFun());
//        model.addAttribute("cacheUserDatas", layoutService.getUserJson());
//        model.addAttribute("cacheDeptDatas", layoutService.getDeptJson());
        model.addAttribute("menuTree", "<div id='menu-home'>"+majordom.toString()+dom.toString()+"</div>"+newdom.toString());

        //当前用户信息
        model.addAttribute("CURRENTUSERNAME",ShiroUtils.getUserName());
        model.addAttribute("CURRENTUSERID",ShiroUtils.getUserId());
        model.addAttribute("CURRENTDEPTNAME",ShiroUtils.getDeptName());
        model.addAttribute("CURRENTDEPTID",ShiroUtils.getDeptId());
        model.addAttribute("CURRENTCOMPANYID",ShiroUtils.getCompanyId());
        model.addAttribute("CURRENTONEDEPTID",ShiroUtils.getOneDeptId());
        model.addAttribute("CURRENTTWODEPTID",ShiroUtils.getTwoDeptId());
        model.addAttribute("CURRENTPROJECTID",ShiroUtils.getProjectId());
        model.addAttribute("CURRENTSYSTEMID",ShiroUtils.getSystemId());

        if(ProjectID!=null&&SystemID!=null){
            ToolProjectSystem ps = new ToolProjectSystem();
            ps.setProject_id(ProjectID);
            ps.setSystem_id(SystemID);
            ToolProjectSystem system=toolProjectSystemMapper.selectOne(ps);
            model.addAttribute("ProjectSystemName",system.getSystem_name());
            model.addAttribute("HOMEPAGE",StringUtils.emptyToDefault(system.getMain_home(),"#home"));

            if(StringUtils.isNotEmpty(system.getMain_layout())) {
                log.info("forward to ::"+"base/"+system.getMain_layout());
                return "base/"+system.getMain_layout();//指定模版
            }
        }
        return "base/main";
    }


    @GetMapping("/login")
    public String login(Model model) throws Exception {
       //非SaaS项目，取第一个可用项目信息
        String isSaaS=  MemCache.getSystemParameter("isSaaS");
        System.out.println("isSaaS=============="+isSaaS);
        if(isSaaS!=null&&isSaaS.equals("false")) {
            ToolProject query=new ToolProject();
            query.setAuditing("1");
            ToolProject obj=toolProjectMapper.selectOne(query);
            if(obj!=null) {
                ToolSystemModule system = new ToolSystemModule();
                ToolProjectSystem ps = new ToolProjectSystem();
                ps.setProject_id(obj.getProject_id());
                List<ToolProjectSystem> lssystem = toolProjectSystemMapper.select(ps);
                StringBuffer systemList = new StringBuffer("");

                for (ToolProjectSystem sys : lssystem) {
                    ToolSystemModule o = toolSystemModuleMapper.selectByPrimaryKey(sys.getSystem_id());
                    systemList.append("<option value=\"" + o.getSystem_id() + "\"  " + (sys.getIs_show() != null && sys.getIs_show().equals("1") ? "selected" : "") + " >" + o.getSystem_name() + "</option>");
                }

                model.addAttribute("projectID", obj.getProject_id());
                model.addAttribute("systemList", systemList);
            }
        }
        if(!ShiroUtils.isLogin()) {
            return "login";
        }else {

            return index(model);
        }
    }

    /**
     * 获取常用功能列表
     * @return
     */
    public Map<String,String> getMajorFun(){
        Map<String,String> mp=new HashMap<String,String>();
        Example example = new Example(SystemMajorfunc.class);
        example.setOrderByClause("hit_num desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_id", ShiroUtils.getUserId());
        criteria.andEqualTo("memo",ShiroUtils.getSystemId());
        List<SystemMajorfunc> ls=systemMajorfuncMapper.selectByExample(example);
        for(int i=0;i<ls.size()&&i<10;i++){
            SystemMajorfunc majorfunc=ls.get(i);
            mp.put(majorfunc.getFun_id(),majorfunc.getFun_id());
        }

        return mp;
    }


    //默认图标
    static private   String[] icoArr=new String[]{"fa fa-sitemap","fa fa-user","fa fa-desktop","fa fa-child","fa fa-wrench","fa fa-suitcase","fa fa-cubes","fa fa-wrench","fa fa-history","fa fa-users"};
    /**
     *     获取功能菜单
     */
    private void getMenuTreeNew(Model model,StringBuilder dom,StringBuilder majordom,Map<String,String> majorMp) throws Exception {
        majordom.append("<li class='treeview menu-open'>");
        majordom.append("<a href='#'>");
        majordom.append("<i class='fa fa-suitcase'></i>");
        majordom.append("<span class='oneMenu'>常见功能</span>");
        majordom.append("<span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i> </span>");
        majordom.append("</a>");
        majordom.append("<ul class='treeview-menu' style='display:block;'>");

        String userId=ShiroUtils.getUserId();
        //初始化缓存加上权限
        MemCache._userIsAdmin.remove(userId);//每次登录清掉权限缓存
        layoutService.cacheRoleInfo();
        //判断是否有权限
        List<ToolSystemModule> oneMenu = layoutService.getSystemMenuOne();
        StringBuffer mainMenu=new StringBuffer("<li  class=\"li-menu\"  module-id=\"menu-home\"> 首&nbsp;页</li>");
        int n=0;
        for(ToolSystemModule module:oneMenu){
            if(!MemCache._userIsAdmin.get(userId)&&!MemCache._userRoleModule.containsKey(userId+"--"+module.getSystem_id())){//不是系统管理员，也没有该模块权限则跳过
                continue;
            }
            mainMenu.append("<li  class=\"li-menu\"  module-id=\"menu-"+module.getSystem_id()+"\">"+module.getSystem_name()+" </li>");
            dom.append("<div id='menu-"+module.getSystem_id()+"'  style='display:none' >" );

            List<ToolSystemModule> towMenu = layoutService.getSystemMenuTwo(module.getSystem_id());//获取子模块
            int i=0;
            for(ToolSystemModule submodule:towMenu){

                if(!MemCache._userIsAdmin.get(userId)&&!MemCache._userRoleModule.containsKey(userId+"--"+submodule.getSystem_id())){//不是系统管理员，也没有该模块权限则跳过
                    continue;
                }
                dom.append("<li class='treeview"+(i==0?" menu-open":"")+"'>");
                dom.append("<a href='#'   module-id='"+submodule.getSystem_id()+"'>");
                dom.append("<i class='" + StringUtils.emptyToDefault(submodule.getIco_img(),icoArr[n++%10] )+ "'></i>");
                dom.append("<span class='oneMenu'>" + submodule.getSystem_name() + "</span>");
                dom.append("<span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i> </span>");
                dom.append("</a>");
                dom.append("<ul class='treeview-menu' "+(i==0?"style='display:block;'":"")+">");

                List<Map<String,Object>>  functions =layoutService.getFunctions(submodule.getSystem_id());
                for(Map<String,Object> function:functions){

                    if(!MemCache._userIsAdmin.get(userId)&&(!MemCache._userRoles.get(userId).containsKey(function.get("fun_id"))||
                            !"1".equals((String) MemCache._userRoles.get(userId).get(function.get("fun_id")).get("is_access") ))){//不是系统管理员，也没有该功能权限则跳过
                        continue;
                    }
                    StringBuffer fundom=new StringBuffer("");
                    fundom.append("<li class='treeview'>");
                    fundom.append("<a href='#"+(function.get("layout_url")==null?"":((String)function.get("layout_url")).substring(1))+"?funId="+function.get("fun_id")+"'>");
                    fundom.append("<i class='" + StringUtils.emptyToDefault((String)function.get("ico_img"),icoArr[n++%10]) + "'></i>");
                    fundom.append("<span class='funMenu'>" + function.get("fun_name") + "</span>");
                    fundom.append("</a>");
                    fundom.append("</li>");

                    dom.append(fundom);
                    if(majorMp.containsKey(function.get("fun_id"))) majordom.append(fundom);
                }

                dom.append("</ul>");
                dom.append("</li>");
                if(i==0) i++;
            }

            dom.append("</div>");
        }

        majordom.append("</ul>");
        majordom.append("</li>");
        model.addAttribute("mainmenu",mainMenu.toString());
    }


}
