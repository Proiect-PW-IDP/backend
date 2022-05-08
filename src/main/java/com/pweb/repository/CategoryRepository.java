package com.pweb.repository;

import com.pweb.dao.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "Select * FROM category c WHERE c.name = ?1", nativeQuery = true)
    public Category findByCategoryName(String name);
}
