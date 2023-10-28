package org.example.service.impl;

import org.example.persistence.entity.Test;
import org.example.persistence.repository.TestRepository;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestResponseDto;
import org.example.service.mapper.TestMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.util.TestTestData.*;
import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(testRequestDto.directionsId().size(), responseDto.directions().size());
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
        assertEquals(testRequestDto.directionsId().size(), responseDto.directions().size());
    }
}
