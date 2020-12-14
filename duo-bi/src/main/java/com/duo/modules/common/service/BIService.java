package com.duo.modules.common.service;

import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.utils.E2MapUtil;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.StringUtils;
import com.duo.modules.common.bean.VisualDTO;
import com.duo.modules.tool.entity.BiVisual;
import com.duo.modules.tool.entity.BiVisualCategory;
import com.duo.modules.tool.entity.BiVisualConfig;
import com.duo.modules.tool.entity.BiVisualMap;
import com.duo.modules.tool.mapper.BiVisualCategoryMapper;
import com.duo.modules.tool.mapper.BiVisualConfigMapper;
import com.duo.modules.tool.mapper.BiVisualMapMapper;
import com.duo.modules.tool.mapper.BiVisualMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2020/7/17]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Service
public class BIService extends BaseService {
    @Autowired
    private BiVisualMapper biVisualMapper;
    @Autowired
    private BiVisualMapMapper biVisualMapMapper;
    @Autowired
    private BiVisualCategoryMapper biVisualCategoryMapper;
    @Autowired
    private BiVisualConfigMapper biVisualConfigMapper;


    public void findPage(BaseEntity entity,String tabeName){

    }

    //获取visual对象
    public List<BiVisual> getVisualList( BiVisual visual){
        DynamicDataSource.setDataSource("default");
        List<BiVisual> records=   biVisualMapper.select(visual);
        DynamicDataSource.clearDataSource();
        return records;
    }

    //获取全部对象
    public List<BaseEntity> getList( String tableName){
        DynamicDataSource.setDataSource("default");
        List<BaseEntity> records=   getMapper(tableName).selectAll();
        DynamicDataSource.clearDataSource();
        return records;
    }

    //获取单个对象
    public BaseEntity getEntity(BaseEntity entity,String tableName){
        DynamicDataSource.setDataSource("default");
        BaseEntity record= (BaseEntity) getMapper(tableName).selectOne(entity);
        DynamicDataSource.clearDataSource();
        return record;
    }


    //获取单个对象
    public BaseEntity getEntityById(String id,String tableName){
        DynamicDataSource.setDataSource("default");
        BaseEntity record= (BaseEntity) getMapper(tableName).selectByPrimaryKey(id);
        DynamicDataSource.clearDataSource();
        return record;
    }

    public boolean create(Map<String,Object> mp, String tableName)  {
        try {
            DynamicDataSource.setDataSource("default");
            Class clazz;
            clazz = MemCache._entitys.get(tableName);
            //获取newid
            String keycolumn = getTableKeyIdColumn(tableName);//主键列
            log.info("keycolumn==========" + keycolumn);
            String keyid = String.valueOf(mp.get(keycolumn));
            String newid = mp.containsKey("newId") ? (String) mp.get("newId") : getKeyUID();
            if (StringUtils.isEmpty(keyid)||keyid.equals("null")) {
                mp.put(keycolumn, newid);
            }
            BaseEntity record = (BaseEntity) Map2EntityUtil.createModel(clazz, mp);
            getMapper(tableName).insert(record);
        }catch (Exception e){
            log.error("新建记录失败！",e);
            return false;
        }finally {

            DynamicDataSource.clearDataSource();
        }
        return true;
    }


    public boolean create(VisualDTO visual)  {
        try {
            DynamicDataSource.setDataSource("default");
            BiVisual bv=visual.getVisual();
            BiVisualConfig bvc=visual.getConfig();
            String vid=this.getKeyUID();
            bv.setId(vid);
            biVisualMapper.insert(bv);
            bvc.setId(vid);
            bvc.setVisualId(vid);
            biVisualConfigMapper.insert(bvc);
        }catch (Exception e){
            log.error("新建记录失败！",e);
            return false;
        }finally {

            DynamicDataSource.clearDataSource();
        }
        return true;
    }

    public boolean save(Map<String,Object> mp,String tableName){

        try {
            DynamicDataSource.setDataSource("default");
            Class clazz;
            clazz = MemCache._entitys.get(tableName);
            //获取newid
            String keycolumn = getTableKeyIdColumn(tableName);//主键列
            log.info("keycolumn==========" + keycolumn);
            String keyid = String.valueOf(mp.get(keycolumn));
            if (StringUtils.isEmpty(keyid)||keyid.equals("null"))  return false;
            BaseEntity record = (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyid);;
            getMapper(tableName).updateByPrimaryKey(record);
        }catch (Exception e){
            log.error("保存记录失败！",e);
            return false;
        }finally {

            DynamicDataSource.clearDataSource();
        }
        return true;
    }


    public boolean delete(String[] ids,String tableName){


        return true;
    }


    public String copyVisual(String id){
        String newid=this.getKeyUID();
        try {
            DynamicDataSource.setDataSource("default");

            BiVisual record = biVisualMapper.selectByPrimaryKey(id);
            BiVisualConfig bvc=biVisualConfigMapper.selectByPrimaryKey(id);
            record.setId(newid);
            bvc.setId(newid);
            bvc.setVisualId(newid);
            biVisualMapper.insert(record);
            biVisualConfigMapper.insert(bvc);
        }catch (Exception e){
            log.error("新建记录失败！",e);
            return null;
        }finally {

            DynamicDataSource.clearDataSource();
        }
        return newid;
    }

    public void List(BaseEntity entity,String tableName){

    }

    public VisualDTO detail(String id){
        VisualDTO dto = new VisualDTO();
        BiVisual bv=biVisualMapper.selectByPrimaryKey(id);
        BiVisualConfig query=new BiVisualConfig();
        query.setVisualId(id);
        BiVisualConfig bvc=biVisualConfigMapper.selectByPrimaryKey(id);
        dto.setConfig(bvc);
        dto.setVisual(bv);
        return dto;
    }
}
