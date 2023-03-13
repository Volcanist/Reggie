package com.tc.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tc.reggie.entity.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
