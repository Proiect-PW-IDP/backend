package com.pweb.controller;

import com.pweb.dao.Category;
import com.pweb.dao.User;
import com.pweb.dto.LoginForm;
import com.pweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    @GetMapping
    public ResponseEntity getById(@RequestParam(name = "categoryId") int categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getById(categoryId));
    }

    @GetMapping("/byName")
    public ResponseEntity getByName(@RequestParam(name = "categoryName") String categoryName) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getByName(categoryName));
    }

    @PostMapping
    public ResponseEntity createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(category));
    }

    @DeleteMapping
    public ResponseEntity deleteCategory(@RequestParam(name = "categoryId") int categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.delete(categoryId));
    }
}
