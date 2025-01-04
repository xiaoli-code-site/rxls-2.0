package cn.rx.db.mapper;

import cn.rx.db.entity.SysConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 系统配置(SysConfig)表数据库访问层
 *
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {

}

