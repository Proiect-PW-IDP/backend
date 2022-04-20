package com.pweb.repository;

import com.pweb.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "Select * FROM user u WHERE u.username = ?1 AND u.password = ?2", nativeQuery = true)
    public User findByUsernameAndPassword(String username, String password);
}
