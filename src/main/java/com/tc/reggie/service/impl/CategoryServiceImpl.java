package com.tc.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc.reggie.common.CustomException;
import com.tc.reggie.entity.Category;
import com.tc.reggie.entity.Dish;
import com.tc.reggie.entity.Setmeal;
import com.tc.reggie.mapper.CategoryMapper;
import com.tc.reggie.service.CategoryService;
import com.tc.reggie.service.DishService;
import com.tc.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id){
        //查询是否关联菜品,如果关联，抛业务异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();

        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1=dishService.count(dishLambdaQueryWrapper);
        if(count1>0){
            //已关联菜品，抛出一个业务异常
            throw new CustomException("当前分类已关联菜品，不能删除");
        }

        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件,根据id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2=setmealService.count();
        if (count2>0){
            //已经关联套餐，抛出一个业务异常
            throw new CustomException("当前分类已关联套餐，不能删除");
        }

        //正常删除分类
        super.removeById(id);
    }

//    public void remove(Long id) {

//    }
}
