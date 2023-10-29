package org.example.persistence.repository;

import org.example.persistence.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("SELECT COUNT(*) FROM Test ")
    int getCountOfDirections();

    @Query("SELECT t FROM Test t JOIN t.directions d WHERE d.id IN :directionsId AND t.name = :name")
    List<Test> findTestsByDirectionIdsAndName(@Param("directionsId") List<Long> directionsId,
                                              @Param("name") String name);

    @Query("SELECT t FROM Test t JOIN t.directions d WHERE d.id IN :directionsId")
    List<Test> findTestsByDirectionIds(@Param("directionsId") List<Long> directionsId);

    Page<Test> findAllByName(String name, Pageable pageable);
}
