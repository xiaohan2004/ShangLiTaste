package org.example.service.impl;

import org.example.mapper.CategoryMapper;
import org.example.pojo.Category;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryMapper.getCategoryById(id);
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
    }
}

