package krainet.test.service;

import krainet.test.service.dto.*;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TestResultService {
    TestResultResponseDto saveTestResult(TestResultRequestDto testResultRequestDto);

    TestResultResponseDto updateTestResult(Long id, TestResultRequestDto testResultRequestDto);

    boolean isTestResultExist(Long id);

    List<TestResultResponseDto> getTestResult(List<SortTestResultFields> sortTestResultFields,
                                              Sort.Direction order,
                                              TestResultRequestFilter testResultRequestFilter,
                                              PageRequestDto pageRequestDto);
}
