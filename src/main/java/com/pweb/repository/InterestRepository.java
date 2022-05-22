package com.pweb.repository;

import com.pweb.dao.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Integer> {
    @Query(value = "Select * FROM interest i WHERE i.user_id = ?1 AND i.offer_id =?2", nativeQuery = true)
    public Optional<Interest> getInterest(int userId, int offerId);
}
