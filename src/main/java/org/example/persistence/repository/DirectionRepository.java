package org.example.persistence.repository;

import org.example.persistence.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {
    @Query("SELECT d.test.name FROM Direction d JOIN FETCH Test t WHERE d.test.id = t.id ")
    String findTestNameByDirectionId(Long Id);
}
