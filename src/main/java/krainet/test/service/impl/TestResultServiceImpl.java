package krainet.test.service.impl;

import krainet.test.persistence.entity.TestResult;
import krainet.test.persistence.repository.TestResultRepository;
import krainet.test.service.TestResultService;
import krainet.test.service.dto.*;
import krainet.test.service.mapper.TestResultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static krainet.test.service.util.PageUtil.getPageable;
import static krainet.test.service.util.SortUtil.getSort;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestResultServiceImpl implements TestResultService {

    private final TestResultRepository testResultRepository;
    private final TestResultMapper testResultMapper;

    @Override
    @Transactional
    public TestResultResponseDto saveTestResult(TestResultRequestDto testResultRequestDto) {
        TestResult testResult = testResultMapper.fromRequestDtoToEntity(testResultRequestDto);
        TestResult savedTestResult = testResultRepository.save(testResult);
        log.info("Saved test result - {}", testResult);
        return testResultMapper.fromEntityToResponseDto(savedTestResult);
    }

    @Override
    @Transactional
    public TestResultResponseDto updateTestResult(Long id, TestResultRequestDto testResultRequestDto) {
        TestResult testResult = testResultMapper.fromRequestDtoToEntity(testResultRequestDto);
        testResult.setId(id);
        TestResult updatedTestResult = testResultRepository.save(testResult);
        log.info("Updated test result - {}", updatedTestResult);
        return testResultMapper.fromEntityToResponseDto(updatedTestResult);
    }

    @Override
    public boolean isTestResultExist(Long id) {
        return testResultRepository.existsById(id);
    }

    @Override
    public List<TestResultResponseDto> getTestResult(List<SortTestResultFields> sortTestResultFields,
                                                     Sort.Direction order,
                                                     TestResultRequestFilter testResultRequestFilter,
                                                     PageRequestDto pageRequestDto) {
        log.info("Get list of tests result in service method with filter - {}, pageable - {}," +
                 " sort - {}, order - {}", testResultRequestFilter, pageRequestDto,
                testResultRequestFilter, order);

        Sort sort = getSort(sortTestResultFields, order);
        Specification<TestResult> specification = getSpecifications(testResultRequestFilter);
        Pageable pageable = getPageable(pageRequestDto, testResultRepository.getCountOfTestResults(), sort);

        Page<TestResult> candidatePage = testResultRepository.findAll(specification, pageable);

        return candidatePage.map(testResultMapper::fromEntityToResponseDto).getContent();
    }

    private Specification<TestResult> getSpecifications(TestResultRequestFilter testResultRequestFilter) {
        Specification<TestResult> specification = Specification.where(null);
        if (testResultRequestFilter == null) {
            return specification;
        }
        if (testResultRequestFilter.id() != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("id"), testResultRequestFilter.id()));
        }
        if (testResultRequestFilter.localDate() != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("date"), testResultRequestFilter.localDate()));
        }
        if (testResultRequestFilter.score() != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("score"), testResultRequestFilter.score()));
        }
        if (testResultRequestFilter.candidateId() != null && !testResultRequestFilter.candidateId().isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    root.join("candidate").get("id").in(testResultRequestFilter.candidateId()));
        }
        if (testResultRequestFilter.testId() != null && !testResultRequestFilter.testId().isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    root.join("test").get("id").in(testResultRequestFilter.testId()));
        }
        return specification;
    }

}
