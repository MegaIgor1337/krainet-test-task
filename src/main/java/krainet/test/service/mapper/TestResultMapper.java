package krainet.test.service.mapper;

import krainet.test.persistence.entity.TestResult;
import krainet.test.persistence.repository.CandidateRepository;
import krainet.test.persistence.repository.TestRepository;
import krainet.test.service.dto.TestResultRequestDto;
import krainet.test.service.dto.TestResultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestResultMapper {
    private final TestRepository testRepository;
    private final CandidateRepository candidateRepository;

    public TestResultResponseDto fromEntityToResponseDto(TestResult testResult) {
        return TestResultResponseDto.builder()
                .id(testResult.getId())
                .candidateId(testResult.getCandidate().getId())
                .testId(testResult.getTest().getId())
                .score(testResult.getScore())
                .date(testResult.getDate())
                .build();
    }

    public TestResult fromRequestDtoToEntity(TestResultRequestDto testResultRequestDto) {
        return TestResult.builder()
                .date(testResultRequestDto.date())
                .candidate(candidateRepository.findById(testResultRequestDto.candidateId()).get())
                .test(testRepository.findById(testResultRequestDto.testId()).get())
                .score(testResultRequestDto.score())
                .build();
    }
}
