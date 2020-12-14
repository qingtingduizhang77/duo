package  com.duo.modules.health.mapper;

import com.duo.core.MyMapper;
import com.duo.modules.health.entity.DeviceTypeCargo;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.common.special.InsertListMapper;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.util.List;

public interface DeviceTypeCargoMapper extends MyMapper< DeviceTypeCargo > {

}