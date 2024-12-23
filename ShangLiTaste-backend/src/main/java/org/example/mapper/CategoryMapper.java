package org.example.mapper;

import org.example.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("SELECT * FROM categories")
    List<Category> getAllCategories();

    @Select("SELECT * FROM categories WHERE category_id = #{categoryId}")
    Category getCategoryById(Integer categoryId);

    @Insert("INSERT INTO categories(name, description) VALUES(#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "categoryId")
    void insertCategory(Category category);

    @Update("UPDATE categories SET name = #{name}, description = #{description} WHERE category_id = #{categoryId}")
    void updateCategory(Category category);

    @Delete("DELETE FROM categories WHERE category_id = #{categoryId}")
    void deleteCategory(Integer categoryId);
}

