package org.example.service;

import org.example.service.dto.PageRequestDto;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestRequestFilter;
import org.example.service.dto.TestResponseDto;

import java.util.List;

public interface TestService {
    boolean isTestExist(Long id);

    TestResponseDto saveTest(TestRequestDto testRequestDto);

    List<TestResponseDto> getTests(TestRequestFilter testRequestFilter, PageRequestDto pageRequestDto);

    TestResponseDto updateTest(Long id, TestRequestDto testRequestDto);
}
