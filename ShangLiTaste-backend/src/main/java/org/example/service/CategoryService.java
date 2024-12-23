package org.example.service;

import org.example.pojo.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Integer id);
}

