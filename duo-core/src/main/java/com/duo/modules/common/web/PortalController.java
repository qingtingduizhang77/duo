package com.duo.modules.common.web;

import com.duo.core.BaseController;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.Result;
import com.duo.modules.tool.entity.ToolPortal;
import com.duo.modules.tool.entity.ToolWorkflow;
import com.duo.modules.tool.mapper.ToolPortalMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@Slf4j
@RequestMapping("/portal")
public class PortalController extends BaseController {

    @Autowired
    ToolPortalMapper toolPortalMapper;

    @GetMapping
    @RequestMapping("/page-tool-portal")
    public String page_tool_portal(Model model, HttpServletRequest request) {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        String keyId= (String) requestMap.get("foreignKeyId");
        ToolPortal record=toolPortalMapper.selectByPrimaryKey(keyId);
        String jsondata=record.getPortal_map();
        model.addAttribute("jsondata", jsondata);
        model.addAttribute("portal_id", keyId);
        return "tool/page-tool-portal";
    }

    //保存portal设计图
    @ResponseBody
    @RequestMapping("/designsave")
    public Result<?> portalupdate(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        String data=StringEscapeUtils.unescapeHtml(request.getParameter("data"));
        String keyId=StringEscapeUtils.unescapeHtml(request.getParameter("foreignKeyId"));

        ToolPortal record=toolPortalMapper.selectByPrimaryKey(keyId);
        record.setPortal_map(data);
        toolPortalMapper.updateByPrimaryKeySelective(record);
        return Result.success();
    }


}
