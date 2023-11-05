package krainet.test.persistence.repository;

import krainet.test.persistence.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long>,
        JpaSpecificationExecutor<TestResult> {
    @Query("SELECT COUNT(*) FROM TestResult ")
    int getCountOfTestResults();
}
