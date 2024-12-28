package org.example.controller;

import org.example.pojo.Dish;
import org.example.pojo.Result;
import org.example.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping
    public Result getAllDishes() {
        return Result.success(dishService.getAllDishes());
    }

    @GetMapping("/{id}")
    public Result getDishById(@PathVariable Integer id) {
        Dish dish = dishService.getDishById(id);
        if (dish != null) {
            return Result.success(dish);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createDish(@RequestBody Dish dish) {
        dishService.addDish(dish);
        return Result.success(dish);
    }

    @PutMapping("/{id}")
    public Result updateDish(@PathVariable Integer id, @RequestBody Dish dish) {
        Dish existingDish = dishService.getDishById(id);
        if (existingDish != null) {
            dish.setDishId(id);
            dishService.updateDish(dish);
            return Result.success(dish);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteDish(@PathVariable Integer id) {
        Dish existingDish = dishService.getDishById(id);
        if (existingDish != null) {
            dishService.deleteDish(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/force/{id}")
    public Result forceDeleteDish(@PathVariable Integer id) {
        Dish existingDish = dishService.getDishById(id);
        if (existingDish != null) {
            dishService.forceDeleteDish(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

