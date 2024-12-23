package org.example.controller;

import org.example.pojo.Category;
import org.example.pojo.Result;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result getAllCategories() {
        return Result.success(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public Result getCategoryById(@PathVariable Integer id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return Result.success(category);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return Result.success(category);
    }

    @PutMapping("/{id}")
    public Result updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        Category existingCategory = categoryService.getCategoryById(id);
        if (existingCategory != null) {
            category.setCategoryId(id);
            categoryService.updateCategory(category);
            return Result.success(category);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable Integer id) {
        Category existingCategory = categoryService.getCategoryById(id);
        if (existingCategory != null) {
            categoryService.deleteCategory(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

