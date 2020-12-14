package com.duo.modules.common.feign;

import com.alibaba.fastjson.JSONObject;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.vo.PageResultSet;
import com.duo.core.vo.Result;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
* 
* <p>Title: TestFeign.java</p>
* @author Carlton
* @date 2019年4月29日 上午9:26:36
*/
public interface CommonFeign  extends  BaseFeign{

	/**
	 * 基于SQL查询结果集
	 *      ——支持分页
	 *      ——支持统计行
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/common/listmp",consumes = MediaType.APPLICATION_JSON_VALUE)
	public PageResultSet<Map<String,Object>> listmp(HttpServletRequest request) ;

	/**
	 * 基于SQL查询结果集
	 *      ——支持分页
	 *      ——支持统计行
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/common/list",consumes = MediaType.APPLICATION_JSON_VALUE)
	public PageResultSet<Map<String,Object>> list(@RequestParam("funId") String funId, HttpServletRequest request) throws Exception ;


	/**
	 * 复制记录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/common/copy")
	public Result<?> copy(HttpServletRequest request)  throws Exception ;

	/**
	 * 用于更新页面指定行传过来的值
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/common/update")
	public Result<?> update(HttpServletRequest request)  throws Exception ;

	/**
	 * 删除记录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/common/deletes")
	public Result<?> deletes(HttpServletRequest request)  throws Exception;

	/**
	 * copy
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/common/gridsave")
	public Result<?> gridsave(HttpServletRequest request) throws Exception ;

	/**
	 * copy
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/common/savefile")
	public Result<?> saveFile( HttpServletRequest request) throws Exception ;
	/**
	 * copy
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/common/save")
	public Result<?> save(HttpServletRequest request) throws Exception ;


	/**
	 * 数据导入
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/common/dataimport")
	public Result<?> dataimport(HttpServletRequest request)  throws Exception  ;


	/**
	 * 批量修改
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/common/batchUpdate")
	public Result<?> batchUpdate(HttpServletRequest request)  throws Exception  ;
}
