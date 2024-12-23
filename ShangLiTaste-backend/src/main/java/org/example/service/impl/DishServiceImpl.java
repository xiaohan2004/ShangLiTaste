package org.example.service.impl;

import org.example.mapper.DishMapper;
import org.example.pojo.Dish;
import org.example.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public List<Dish> getAllDishes() {
        return dishMapper.getAllDishes();
    }

    @Override
    public Dish getDishById(Integer id) {
        return dishMapper.getDishById(id);
    }

    @Override
    public void addDish(Dish dish) {
        dishMapper.insertDish(dish);
    }

    @Override
    public void updateDish(Dish dish) {
        dishMapper.updateDish(dish);
    }

    @Override
    public void deleteDish(Integer id) {
        dishMapper.deleteDish(id);
    }
}

