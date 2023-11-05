package krainet.test.service.impl;

import krainet.test.service.TestService;
import krainet.test.service.dto.PageRequestDto;
import krainet.test.service.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import krainet.test.persistence.entity.Test;
import krainet.test.persistence.repository.TestRepository;
import krainet.test.service.dto.TestRequestDto;
import krainet.test.service.dto.TestRequestFilter;
import krainet.test.service.dto.TestResponseDto;
import krainet.test.service.mapper.TestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        log.info("Saved test - {}", savedTest);
        return testMapper.fromEntityToResponseDto(savedTest);
    }

    @Override
    public List<TestResponseDto> getTests(TestRequestFilter testRequestFilter, PageRequestDto pageRequestDto) {
        log.info("Get list of tests on service method with filter - {}, pageRequestDto - {}",
                testRequestFilter, pageRequestDto);

        Pageable pageable = PageUtil.getPageable(pageRequestDto, testRepository.getCountOfDirections(), null);
        Specification<Test> specification = getSpecifications(testRequestFilter);

        Page<Test> testPage = testRepository.findAll(specification, pageable);

        return testPage.map(testMapper::fromEntityToResponseDto).getContent();
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

    private Specification<Test> getSpecifications(TestRequestFilter testRequestFilter) {
        Specification<Test> specification = Specification.where(null);
        if (testRequestFilter.testName() != null && !testRequestFilter.testName().isBlank()) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("name"), testRequestFilter.testName()));
        }
        if (testRequestFilter.directionsId() != null && !testRequestFilter.directionsId().isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    root.join("directions").get("id").in(testRequestFilter.directionsId()));
        }
        return specification;
    }
}
