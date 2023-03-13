package com.tc.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tc.reggie.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
}
