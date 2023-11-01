package org.example.service.impl;

import org.example.persistence.entity.Test;
import org.example.persistence.repository.TestRepository;
import org.example.service.dto.PageRequestDto;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestRequestFilter;
import org.example.service.dto.TestResponseDto;
import org.example.service.mapper.TestMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.example.util.TestTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {
    @InjectMocks
    private TestServiceImpl testService;
    @Mock
    private TestMapper testMapper;
    @Mock
    private TestRepository testRepository;

    @org.junit.jupiter.api.Test
    public void shouldReturnTrueIfTestExist() {
        when(testRepository.existsById(1L)).thenReturn(true);

        boolean result = testService.isTestExist(1L);

        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    public void testAddTestSuccessWithoutDirections() {
        TestRequestDto testRequestDto = createRequestTestWithoutDirections();
        Test testWithoutId = createTestWithoutId();
        Test testWithId = createAddedTestWithId();
        TestResponseDto expectedDto = createResponseTestDtoWithoutDirections();

        when(testMapper.fromRequestDtoToEntity(testRequestDto)).thenReturn(testWithoutId);
        when(testRepository.save(testWithoutId)).thenReturn(testWithId);
        when(testMapper.fromEntityToResponseDto(testWithId)).thenReturn(expectedDto);

        TestResponseDto responseDto = testService.saveTest(testRequestDto);

        assertNotNull(responseDto);
        assertEquals(testRequestDto.name(), responseDto.name());
        assertEquals(testRequestDto.description(), responseDto.description());
    }

    @org.junit.jupiter.api.Test
    public void testAddTestSuccessWithDirections() {
        TestRequestDto testRequestDto = createRequestTestWithDirections();
        Test testWithoutId = createTestWithoutIdWithDirections();
        Test testWithId = createTestWithDirections();
        TestResponseDto expectedDto = createResponseTestDtoWithDirections();

        when(testMapper.fromRequestDtoToEntity(testRequestDto)).thenReturn(testWithoutId);
        when(testRepository.save(testWithoutId)).thenReturn(testWithId);
        when(testMapper.fromEntityToResponseDto(testWithId)).thenReturn(expectedDto);

        TestResponseDto responseDto = testService.saveTest(testRequestDto);

        assertNotNull(responseDto);
        assertEquals(testRequestDto.name(), responseDto.name());
        assertEquals(testRequestDto.description(), responseDto.description());
        assertEquals(testRequestDto.directionsId().size(), responseDto.directionsId().size());
    }

    @org.junit.jupiter.api.Test
    public void testUpdateTestSuccess() {
        TestRequestDto testRequestDto = createRequestTestWithDirections();
        Test testWithoutId = createTestWithoutIdWithDirections();
        Test testWithId = createTestWithDirections();
        TestResponseDto expectedDto = createResponseTestDtoWithDirections();

        when(testMapper.fromRequestDtoToEntity(testRequestDto)).thenReturn(testWithoutId);
        when(testRepository.save(testWithoutId)).thenReturn(testWithId);
        when(testMapper.fromEntityToResponseDto(testWithId)).thenReturn(expectedDto);

        TestResponseDto responseDto = testService.updateTest(TEST_ID, testRequestDto);

        assertNotNull(responseDto);
        assertEquals(testRequestDto.name(), responseDto.name());
        assertEquals(testRequestDto.description(), responseDto.description());
        assertEquals(testRequestDto.directionsId().size(), responseDto.directionsId().size());
    }

    @org.junit.jupiter.api.Test
    public void testGetTestsWithEmptyFilter() {
        Page<Test> tests = createPageOfTests();
        List<TestResponseDto> responseDtos = createListOfTestsResponseDto();
        TestRequestFilter testRequestFilter = createEmptyTestFilter();

        when(testRepository.findAll(PageRequest.of(0, 10))).thenReturn(tests);
        when(testMapper.fromEntityToResponseDto(any(Test.class))).thenReturn(
                responseDtos.get(0), responseDtos.get(1));

        List<TestResponseDto> result = testService.getTests(testRequestFilter, new PageRequestDto(0, 10));

        assertEquals(tests.getContent().size(), result.size());
        assertEquals(tests.getContent().get(0).getName(), result.get(0).name());
        assertEquals(tests.getContent().get(1).getDescription(), result.get(1).description());
    }

    @org.junit.jupiter.api.Test
    public void testGetTestsWithNotNullParams() {
        List<Test> tests = createListOfTestsWithDirection();
        List<TestResponseDto> responseDtos = createListOfTestsResponseDtoWithDirections();
        TestRequestFilter testRequestFilter = createNotEmptyTestFilter();

        when(testRepository.findTestsByDirectionIdsAndName(testRequestFilter.directionsId(),
                testRequestFilter.testName())).thenReturn(tests);
        when(testMapper.fromEntityToResponseDto(any(Test.class))).thenReturn(
                responseDtos.get(0), responseDtos.get(1));

        List<TestResponseDto> result = testService.getTests(testRequestFilter, new PageRequestDto(0, 10));

        assertEquals(tests.size(), result.size());
        assertEquals(tests.get(0).getName(), result.get(0).name());
        assertEquals(tests.get(1).getDescription(), result.get(1).description());
        assertEquals(tests.get(0).getDirections().size(), result.get(0).directionsId().size());
    }
}
