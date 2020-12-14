package com.duo.core;

import com.duo.Constants;
import com.duo.MemCache;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.StringUtils;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.system.entity.SystemMajorfunc;
import com.duo.modules.system.mapper.SystemMajorfuncMapper;
import com.duo.modules.tool.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class BaseController {

	@Autowired
	public RedisUtil redisUtil;
	@Resource
	public I18nMessage localeMessage;
	@Resource
	public LogCenter logdb;

	@Autowired
	public LayoutService layoutService;

	@Autowired
	public SystemMajorfuncMapper systemMajorfuncMapper;


}
