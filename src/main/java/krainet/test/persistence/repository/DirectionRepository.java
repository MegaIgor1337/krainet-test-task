package krainet.test.persistence.repository;

import krainet.test.persistence.entity.Direction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long>,
        JpaSpecificationExecutor<Direction> {
    @Query("SELECT d.test.name FROM Direction d JOIN FETCH Test t WHERE d.test.id = t.id AND d.id = :id")
    String findTestNameByDirectionId(@Param("id") Long Id);

    Page<Direction> findAllByName(String name, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Direction ")
    int getCountOfDirections();
}
