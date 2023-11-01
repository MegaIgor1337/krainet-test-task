package krainet.test.service;

import krainet.test.service.dto.PageRequestDto;
import krainet.test.service.dto.TestRequestDto;
import krainet.test.service.dto.TestRequestFilter;
import krainet.test.service.dto.TestResponseDto;

import java.util.List;

public interface TestService {
    boolean isTestExist(Long id);

    TestResponseDto saveTest(TestRequestDto testRequestDto);

    List<TestResponseDto> getTests(TestRequestFilter testRequestFilter, PageRequestDto pageRequestDto);

    TestResponseDto updateTest(Long id, TestRequestDto testRequestDto);
}
