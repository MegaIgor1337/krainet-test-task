package krainet.test.service.impl;

import krainet.test.persistence.entity.Test;
import krainet.test.persistence.repository.TestRepository;
import krainet.test.service.dto.PageRequestDto;
import krainet.test.service.dto.TestRequestDto;
import krainet.test.service.dto.TestRequestFilter;
import krainet.test.service.dto.TestResponseDto;
import krainet.test.service.mapper.TestMapper;
import krainet.test.util.TestTestData;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static krainet.test.util.TestTestData.*;
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
        TestRequestDto testRequestDto = TestTestData.createRequestTestWithoutDirections();
        Test testWithoutId = TestTestData.createTestWithoutId();
        Test testWithId = TestTestData.createAddedTestWithId();
        TestResponseDto expectedDto = TestTestData.createResponseTestDtoWithoutDirections();

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
        TestRequestDto testRequestDto = TestTestData.createRequestTestWithDirections();
        Test testWithoutId = TestTestData.createTestWithoutIdWithDirections();
        Test testWithId = TestTestData.createTestWithDirections();
        TestResponseDto expectedDto = TestTestData.createResponseTestDtoWithDirections();

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
        TestRequestDto testRequestDto = TestTestData.createRequestTestWithDirections();
        Test testWithoutId = TestTestData.createTestWithoutIdWithDirections();
        Test testWithId = TestTestData.createTestWithDirections();
        TestResponseDto expectedDto = TestTestData.createResponseTestDtoWithDirections();

        when(testMapper.fromRequestDtoToEntity(testRequestDto)).thenReturn(testWithoutId);
        when(testRepository.save(testWithoutId)).thenReturn(testWithId);
        when(testMapper.fromEntityToResponseDto(testWithId)).thenReturn(expectedDto);

        TestResponseDto responseDto = testService.updateTest(TestTestData.TEST_ID, testRequestDto);

        assertNotNull(responseDto);
        assertEquals(testRequestDto.name(), responseDto.name());
        assertEquals(testRequestDto.description(), responseDto.description());
        assertEquals(testRequestDto.directionsId().size(), responseDto.directionsId().size());
    }

    @org.junit.jupiter.api.Test
    public void testGetTestsWithEmptyFilter() {
        Page<Test> tests = TestTestData.createPageOfTests();
        List<TestResponseDto> responseDtos = TestTestData.createListOfTestsResponseDto();
        TestRequestFilter testRequestFilter = TestTestData.createEmptyTestFilter();

        when(testRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(tests);
        when(testMapper.fromEntityToResponseDto(any(Test.class))).thenReturn(
                responseDtos.get(0), responseDtos.get(1));

        List<TestResponseDto> result = testService.getTests(testRequestFilter, new PageRequestDto(0, 10));

        assertEquals(tests.getContent().size(), result.size());
        assertEquals(tests.getContent().get(0).getName(), result.get(0).name());
        assertEquals(tests.getContent().get(1).getDescription(), result.get(1).description());
    }

    @org.junit.jupiter.api.Test
    public void testGetTestsWithNotNullParams() {
        Page<Test> tests = createPageOfTests();
        List<TestResponseDto> responseDtos = createListOfTestsResponseDtoWithDirections();
        TestRequestFilter testRequestFilter = createNotEmptyTestFilter();

        when(testRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(tests);
        when(testMapper.fromEntityToResponseDto(any(Test.class))).thenReturn(
                responseDtos.get(0), responseDtos.get(1));

        List<TestResponseDto> result = testService.getTests(testRequestFilter, new PageRequestDto(0, 10));

        assertEquals(tests.getContent().get(0).getName(), result.get(0).name());
        assertEquals(tests.getContent().get(1).getDescription(), result.get(1).description());
    }
}
