package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.entity.Test;
import org.example.persistence.repository.TestRepository;
import org.example.service.TestService;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestResponseDto;
import org.example.service.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TestMapper testMapper;
    @Override
    public boolean isTestExist(Long id) {
        return testRepository.existsById(id);
    }

    @Override
    @Transactional
    public TestResponseDto saveTest(TestRequestDto testRequestDto) {
        Test test = testMapper.fromRequestDtoToEntity(testRequestDto);
        Test savedTest = testRepository.save(test);
        return testMapper.fromEntityToResponseDto(savedTest);
    }

    @Override
    public List<TestResponseDto> getTests() {
        return testRepository.findAll().stream()
                .map(testMapper::fromEntityToResponseDto).toList();
    }
}
