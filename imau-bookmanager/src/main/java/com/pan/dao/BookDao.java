package com.pan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.pojo.BookPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookDao extends BaseMapper<BookPojo> {



}
