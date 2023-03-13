package com.tc.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tc.reggie.dto.DishDto;
import com.tc.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要插入两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新dish表的基本信息
    public void updateWithFlavor(DishDto dishDto);
}
