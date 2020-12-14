package com.duo.core.assigner;

import com.baidu.fsg.uid.utils.DockerUtils;
import com.baidu.fsg.uid.utils.NetUtils;
import com.baidu.fsg.uid.worker.WorkerIdAssigner;
import com.baidu.fsg.uid.worker.WorkerNodeType;
import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.utils.RedisUtil;
import com.duo.modules.system.entity.SystemBaiduid;
import com.duo.modules.system.mapper.SystemBaiduidMapper;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import sun.plugin.util.UIUtil;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @author [ Yonsin ] on [2019/8/12]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class DisposableWorkerIdAssigner implements WorkerIdAssigner {
    @Autowired
    private SystemBaiduidMapper systemBaiduidMapper;

    /**
     * Assign worker id base on database.<p>
     * If there is host name & port in the environment, we considered that the node runs in Docker container<br>
     * Otherwise, the node runs on an actual machine.
     *
     * @return assigned worker id
     */
//    @Transactional
    public long assignWorkerId() {
        // build worker node entity
        SystemBaiduid systemBaiduid = buildWorkerNode();

        // add worker node for new (ignore the same IP + PORT)
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        systemBaiduidMapper.insert(systemBaiduid);
        DynamicDataSource.clearDataSource();
        log.info("Add worker node:" + systemBaiduid);

        return systemBaiduid.getId();
    }

    /**
     * Build worker node entity by IP and PORT
     */
    private SystemBaiduid buildWorkerNode() {
        SystemBaiduid systemBaiduidEntity = new SystemBaiduid();
        if (DockerUtils.isDocker()) {
            systemBaiduidEntity.setType(WorkerNodeType.CONTAINER.value());
            systemBaiduidEntity.setHostName(DockerUtils.getDockerHost());
            systemBaiduidEntity.setPort(DockerUtils.getDockerPort());

        } else {
            systemBaiduidEntity.setType(WorkerNodeType.ACTUAL.value());
            systemBaiduidEntity.setHostName(NetUtils.getLocalAddress());
            systemBaiduidEntity.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000));
        }
        systemBaiduidEntity.setLaunchDate(new Date());
        systemBaiduidEntity.setCreated(new Timestamp(new Date().getTime()));
        Example example=new Example(SystemBaiduid.class);
        example.setOrderByClause("id desc");
        RowBounds rowBounds=new RowBounds(0,1);
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
       List<SystemBaiduid> lsdatas= systemBaiduidMapper.selectByExampleAndRowBounds(example,rowBounds);
        DynamicDataSource.clearDataSource();
        Long id=1l;
        if(lsdatas!=null&&!lsdatas.isEmpty()){
            SystemBaiduid max= lsdatas.get(0);
            id=max.getId()+1;
        }
        systemBaiduidEntity.setId(id);
        return systemBaiduidEntity;
    }

}
