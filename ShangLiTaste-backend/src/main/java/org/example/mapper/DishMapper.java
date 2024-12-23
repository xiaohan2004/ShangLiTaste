package org.example.mapper;

import org.example.pojo.Dish;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {
    @Select("SELECT * FROM dishes")
    List<Dish> getAllDishes();

    @Select("SELECT * FROM dishes WHERE dish_id = #{dishId}")
    Dish getDishById(Integer dishId);

    @Insert("INSERT INTO dishes(name, category_id, price, description, is_active) VALUES(#{name}, #{categoryId}, #{price}, #{description}, #{isActive})")
    @Options(useGeneratedKeys = true, keyProperty = "dishId")
    void insertDish(Dish dish);

    @Update("UPDATE dishes SET name = #{name}, category_id = #{categoryId}, price = #{price}, description = #{description}, is_active = #{isActive} WHERE dish_id = #{dishId}")
    void updateDish(Dish dish);

    @Delete("DELETE FROM dishes WHERE dish_id = #{dishId}")
    void deleteDish(Integer dishId);
}

