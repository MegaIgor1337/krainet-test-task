package org.example.persistence.repository;

import org.example.persistence.entity.Direction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {
    @Query("SELECT d.test.name FROM Direction d JOIN FETCH Test t WHERE d.test.id = t.id AND d.id = :id")
    String findTestNameByDirectionId(@Param("id") Long Id);

    Page<Direction> findAllByName(String name, Pageable pageable);
    List<Direction> findAllByName(String name);
    @Query("SELECT COUNT(*) FROM Direction ")
    int getCountOfDirections();
}
