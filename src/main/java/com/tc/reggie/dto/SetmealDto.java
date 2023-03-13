package com.tc.reggie.dto;

import com.tc.reggie.entity.Setmeal;
import com.tc.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
