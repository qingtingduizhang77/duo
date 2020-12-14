package com.duo.modules.common.web;

import com.duo.core.BaseController;
import com.duo.core.BaseEntity;
import com.duo.core.utils.E2Map;
import com.duo.core.utils.E2MapUtil;
import com.duo.core.utils.JsonUtil;
import com.duo.core.utils.MinIoUtils;
import com.duo.modules.common.bean.*;
import com.duo.modules.common.service.BIService;
import com.duo.modules.tool.entity.BiVisual;
import com.duo.modules.tool.entity.BiVisualCategory;
import com.duo.modules.tool.entity.BiVisualMap;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */


@RestController
@Slf4j
@RequestMapping("/bi")
public class BIController  {

    @Autowired
    private  BIService biService;
    /**
     * 详情
     */
    @GetMapping("/category/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入visualCategory")
    public BIResult<BaseEntity> detail(BiVisualCategory visualCategory) {
        log.info("进入。。。。。。"+"/category/detail");
        BaseEntity detail = biService.getEntity(visualCategory,"BiVisualCategory");
        return BIResult.data(detail);
    }

    /**
     * 列表 可视化分类表
     */
    @GetMapping("/category/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入visualCategory")
    public BIResult<List<BaseEntity>> list(BiVisualCategory visualCategory) {
        log.info("进入。。。。。。"+"/category/list");
        List<BaseEntity> list =  biService.getList("BiVisualCategory");
        return BIResult.data(list);
    }

    /**
     * 分页 可视化分类表
     */
    @GetMapping("/category/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入visualCategory")
    public BIResult<Pages<BaseEntity>> page(BiVisualCategory visualCategory) {
        log.info("进入。。。。。。"+"/category/page");
        Pages<BaseEntity> pages = new Pages();
        pages.setRecords(biService.getList("BiVisualCategory"));
        return BIResult.data(pages);
    }

    /**
     * 新增 可视化分类表
     */
    @PostMapping("/category/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入visualCategory")
    public BIResult saveCat(@RequestBody Map<String,Object> visualCategory) {
        log.info("进入。。。。。。"+"/category/save");
        return BIResult.status(biService.create(visualCategory,"BiVisualCategory"));
    }

    /**
     * 修改 可视化分类表
     */
    @PostMapping("/category/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入visualCategory")
    public BIResult update(@RequestBody Map<String,Object> visualCategory) {
        log.info("进入。。。。。。"+"/category/update");
        return BIResult.status(biService.save(visualCategory,"BiVisualCategory"));
    }

    /**
     * 删除 可视化分类表
     */
    @PostMapping("/category/remove")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "删除", notes = "传入ids")
    public BIResult removeCaregory(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        log.info("进入。。。。。。"+"/category/remove");
        return BIResult.status(biService.delete(ids.split(","),"BiVisualCategory"));
    }


    /**
     * 详情
     */
    @GetMapping("/visual/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入visual")
    public BIResult<VisualDTO> detail(@RequestParam Long id) {
        log.info("进入。。。。。。"+"/visual/detail");
        VisualDTO detail = biService.detail(String.valueOf(id));
        return BIResult.data(detail);
    }

    /**
     * 分页 可视化表
     * http://127.0.0.1:8080/bi/visual/list?current=1&size=10
     */
    @GetMapping("/visual/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入visual")
    public BIResult<Pages<BiVisual>> list(BiVisual visual) {
        log.info("进入。。。。。。"+"/visual/list");

        Pages<BiVisual> pages = new Pages();
        pages.setRecords(biService.getVisualList(visual));
        return BIResult.data(pages);
    }

    /**
     * 新增 可视化表
     */
    @PostMapping("/visual/save")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增", notes = "传入visual")
    public BIResult save(@RequestBody VisualDTO visual) {
        log.info("进入。。。。。。"+"/visual/save");
        boolean temp = biService.create(visual);
        if (temp) {
            String id = visual.getVisual().getId();
            return BIResult.data(Kv.create().set("id",  id));
        } else {
            return BIResult.status(false);
        }
    }

    /**
     * 修改 可视化表
     */
    @PostMapping("/visual/update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "修改", notes = "传入visual")
    public BIResult updateVisual(@RequestBody  Map<String,Object>  visual) {
        log.info("进入。。。。。。"+"/visual/update");
        return BIResult.status(biService.save(visual,"BiVisual"));
    }


    /**
     * 删除 可视化表
     */
    @PostMapping("/visual/remove")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public BIResult removeVisual(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        log.info("进入。。。。。。"+"/visual/remove");
        return BIResult.status(biService.delete(ids.split(","),"BiVisual"));
    }

    /**
     * 复制 可视化表
     */
    @PostMapping("/visual/copy")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "复制", notes = "传入id")
    public BIResult<String> copy(@ApiParam(value = "主键集合", required = true) @RequestParam String id) {
        log.info("进入。。。。。。"+"/visual/copy");
        return BIResult.data(biService.copyVisual(id));
    }

    /**
     * 获取分类
     */
    @GetMapping("/visual/category")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "获取类型")
    public BIResult category() {
        log.info("进入。。。。。。"+"/visual/category");
        List<BaseEntity> list = biService.getList("BiVisualCategory");
        return BIResult.data(list);
    }

    /**
     * 上传文件
     */
    @SneakyThrows
    @PostMapping("/visual/put-file")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "上传", notes = "传入文件")
    public BIResult putFile(@ApiParam(value = "上传文件", required = true) @RequestParam MultipartFile file) {
        log.info("进入。。。。。。"+"/visual/put-file");
//        BladeFile bladeFile = ossTemplate.putFile(file);
        MinIoUtils.uploadFile("bi",file, UUID.randomUUID().toString());
        return BIResult.data(null);
    }


    /**
     * 详情
     */
    @GetMapping("/map/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入visualMap")
    public BIResult<BiVisualMap> detail(BiVisualMap visualMap) {
        log.info("进入。。。。。。"+"/map/detail");
        BiVisualMap detail = (BiVisualMap)biService.getEntity(visualMap,"BiVisualMap");
        return BIResult.data(detail);
    }

    /**
     * 数据详情
     */
    @GetMapping("/map/data")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "数据详情", notes = "传入id")
    public Map<String, Object> data(String id) {
        log.info("进入。。。。。。"+"/map/data");
        BiVisualMap detail =(BiVisualMap) biService.getEntityById(id,"BiVisualMap");
        return JsonUtil.toMap(detail.getData());
    }

    /**
     * 自定义分页 可视化地图配置表
     */
    @GetMapping("/map/list")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入visualMap")
    public BIResult<Pages<BaseEntity>> list(BiVisualMap visualMap) {
        log.info("进入。。。。。。"+"/map/list");
        Pages<BaseEntity> pages = new Pages();
        pages.setRecords(biService.getList("BiVisualMap"));
        return BIResult.data(pages);
    }

    /**
     * 新增 可视化地图配置表
     */
    @PostMapping("/map/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入visualMap")
    public BIResult saveMap(@RequestBody  Map<String,Object> visualMap) {
        log.info("进入。。。。。。"+"/map/save");
        return BIResult.status(biService.save(visualMap,"BiVisualMap"));
    }

    /**
     * 修改 可视化地图配置表
     */
    @PostMapping("/map/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入visualMap")
    public BIResult updateMap(@RequestBody  Map<String,Object> visualMap) {
        log.info("进入。。。。。。"+"/map/update");
        return BIResult.status(biService.save(visualMap,"BiVisualMap"));
    }


    /**
     * 删除 可视化地图配置表
     */
    @PostMapping("/map/remove")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "删除", notes = "传入ids")
    public BIResult removeMap(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        log.info("进入。。。。。。"+"/map/remove");
        return BIResult.status(biService.delete(ids.split(","),"BiVisualMap"));
    }
}
