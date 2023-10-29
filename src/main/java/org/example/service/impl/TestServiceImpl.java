package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.entity.Test;
import org.example.persistence.repository.TestRepository;
import org.example.service.TestService;
import org.example.service.dto.PageRequestDto;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestRequestFilter;
import org.example.service.dto.TestResponseDto;
import org.example.service.mapper.TestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.service.util.PageUtil.getPageable;

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
        log.info("saved test - {}", savedTest);
        return testMapper.fromEntityToResponseDto(savedTest);
    }

    @Override
    public List<TestResponseDto> getTests(TestRequestFilter testRequestFilter, PageRequestDto pageRequestDto) {


        log.info("Get list of tests on service method with filter - {}, page number - {}, page size - {}",
                testRequestFilter, pageRequestDto.pageNumber(), pageRequestDto.pageSize());

        Pageable pageable = getPageable(pageRequestDto, testRepository.getCountOfDirections());

        Page<Test> testPage = getPageOfTests(testRequestFilter, pageable);

        return testPage.map(testMapper::fromEntityToResponseDto).getContent();
    }

    private Page<Test> getPageOfTests(TestRequestFilter testRequestFilter, Pageable pageable) {
        String name = testRequestFilter.testName();
        List<Long> directionsId = testRequestFilter.directionsId();
        if (name != null && !name.isBlank() && directionsId != null && !directionsId.isEmpty()) {
            List<Test> filterTests = testRepository.findTestsByDirectionIdsAndName(directionsId, name);
            return new PageImpl<>(filterTests, pageable, filterTests.size());
        } else if (name != null && !name.isBlank()) {
            return testRepository.findAllByName(name, pageable);
        } else if (directionsId != null && !directionsId.isEmpty()) {
            List<Test> filterTests = testRepository.findTestsByDirectionIds(directionsId);
            return new PageImpl<>(filterTests, pageable, filterTests.size());
        } else {
            return testRepository.findAll(pageable);
        }
    }


    @Override
    @Transactional
    public TestResponseDto updateTest(Long id, TestRequestDto testRequestDto) {
        Test requestTest = testMapper.fromRequestDtoToEntity(testRequestDto);
        requestTest.setId(id);
        Test updatedTest = testRepository.save(requestTest);
        log.info("Updated test - {}", updatedTest);
        return testMapper.fromEntityToResponseDto(updatedTest);
    }
}
