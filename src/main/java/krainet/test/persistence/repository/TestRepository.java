package krainet.test.persistence.repository;

import krainet.test.persistence.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>,
        JpaSpecificationExecutor<Test> {
    @Query("SELECT COUNT(*) FROM Test ")
    int getCountOfDirections();
}
