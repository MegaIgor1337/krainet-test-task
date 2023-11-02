package krainet.test.persistence.repository;

import krainet.test.persistence.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>,
        JpaSpecificationExecutor<Candidate> {
    @Query("SELECT COUNT(*) FROM Candidate ")
    int getCountOfDirections();
}
