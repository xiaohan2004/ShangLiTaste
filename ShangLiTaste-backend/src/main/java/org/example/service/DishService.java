package org.example.service;

import org.example.pojo.Dish;

import java.util.List;

public interface DishService {
    List<Dish> getAllDishes();
    Dish getDishById(Integer id);
    void addDish(Dish dish);
    void updateDish(Dish dish);
    void deleteDish(Integer id);
}

