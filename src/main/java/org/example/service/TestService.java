package org.example.service;

import org.example.persistence.entity.Direction;
import org.example.persistence.entity.Test;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestResponseDto;

import java.util.List;

public interface TestService {
    boolean isTestExist(Long id);
    TestResponseDto saveTest(TestRequestDto testRequestDto);
    List<TestResponseDto> getTests();
}
