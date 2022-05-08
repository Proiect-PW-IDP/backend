package com.pweb.service;

import com.pweb.dao.Category;
import com.pweb.dao.User;
import com.pweb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category getById(int id) {
        return categoryRepository.findById(id).get();
    }

    public Category getByName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public Category delete(int categoryId){
        Category category = getById(categoryId);
        categoryRepository.delete(category);
        return category;
    }
}
