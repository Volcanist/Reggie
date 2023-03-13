package com.tc.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tc.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
