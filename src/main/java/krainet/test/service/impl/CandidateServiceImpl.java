package krainet.test.service.impl;

import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.repository.CandidateRepository;
import krainet.test.service.CandidateService;
import krainet.test.service.dto.*;
import krainet.test.service.mapper.CandidateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static krainet.test.service.util.PageUtil.getPageable;
import static krainet.test.service.util.SortUtil.getSort;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Override
    @Transactional
    public CandidateResponseDto saveCandidate(CandidateRequestDto candidateRequestDto) {
        Candidate candidate = candidateMapper.fromRequestDtoToEntity(candidateRequestDto);
        Candidate savedCandidate = candidateRepository.save(candidate);
        log.info("saved candidate - {}", savedCandidate);
        return candidateMapper.fromEntityToResponseDto(candidate);
    }

    @Override
    public List<CandidateResponseDto> getCandidates(List<SortCandidateFields> sortCandidateFields,
                                                    Sort.Direction direction, CandidateRequestFilter candidateFilter,
                                                    PageRequestDto pageRequestDto) {
        log.info("get list of tests on service method with filter - {}, pageable - {}",
                candidateFilter, pageRequestDto);

        Sort sort = getSort(sortCandidateFields, direction);
        Specification<Candidate> specification = getSpecifications(candidateFilter);
        Pageable pageable = getPageable(pageRequestDto, candidateRepository.getCountOfDirections(), sort);

        Page<Candidate> candidatePage = candidateRepository.findAll(specification, pageable);

        return candidatePage.map(candidateMapper::fromEntityToResponseDto).getContent();
    }

    @Override
    public boolean isCandidateExist(Long id) {
        return candidateRepository.existsById(id);
    }

    @Override
    @Transactional
    public CandidateResponseDto updateCandidate(Long id, CandidateRequestDto candidateRequestDto) {
        Candidate candidate = candidateMapper.fromRequestDtoToEntity(candidateRequestDto);
        candidate.setId(id);
        Candidate updatedCandidate = candidateRepository.save(candidate);
        log.info("updated candidate {} with id - {}", updatedCandidate, id);
        return candidateMapper.fromEntityToResponseDto(updatedCandidate);
    }

    private Specification<Candidate> getSpecifications(CandidateRequestFilter candidateFilter) {
        Specification<Candidate> specification = Specification.where(null);
        if (candidateFilter == null) {
            return specification;
        }
        if (candidateFilter.firstName() != null && !candidateFilter.firstName().isBlank()) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("firstName"), candidateFilter.firstName()));
        }
        if (candidateFilter.lastName() != null && !candidateFilter.lastName().isBlank()) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("lastName"), candidateFilter.lastName()));
        }
        if (candidateFilter.directionsId() != null && !candidateFilter.directionsId().isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    root.join("directions").get("id").in(candidateFilter.directionsId()));
        }
        return specification;
    }


}

