package org.example.persistence.repository;

import org.example.persistence.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT t FROM Test t JOIN FETCH t.directions d WHERE t.id = :testId AND d.name = :directionName")
    Test findTestByTestIdAndDirectionName(@Param("testId") Long testId, @Param("directionName") String directionName);
}
