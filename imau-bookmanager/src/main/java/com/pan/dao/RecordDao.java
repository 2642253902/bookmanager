package com.pan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.pojo.RecordPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordDao extends BaseMapper<RecordPojo> {
}
