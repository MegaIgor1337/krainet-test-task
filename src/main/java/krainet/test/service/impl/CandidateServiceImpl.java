package krainet.test.service.impl;

import krainet.test.service.CandidateService;
import krainet.test.service.dto.CandidateRequestDto;
import krainet.test.service.dto.CandidateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.repository.CandidateRepository;
import krainet.test.service.mapper.CandidateMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Override
    public CandidateResponseDto saveCandidate(CandidateRequestDto candidateRequestDto) {
        Candidate candidate = candidateMapper.fromRequestDtoToEntity(candidateRequestDto);
        Candidate savedCandidate = candidateRepository.save(candidate);
        log.info("saved candidate - {}", savedCandidate);
        return candidateMapper.fromEntityToResponseDto(candidate);
    }

    @Override
    public List<CandidateResponseDto> getCandidates() {
        return candidateRepository.findAll().stream()
                .map(candidateMapper::fromEntityToResponseDto).toList();
    }

    @Override
    public boolean isCandidateExist(Long id) {
        return candidateRepository.existsById(id);
    }

    @Override
    public CandidateResponseDto updateCandidate(Long id, CandidateRequestDto candidateRequestDto) {
        Candidate candidate = candidateMapper.fromRequestDtoToEntity(candidateRequestDto);
        candidate.setId(id);
        Candidate updatedCandidate = candidateRepository.save(candidate);
        log.info("updated candidate {} with id - {}", updatedCandidate, id);
        return candidateMapper.fromEntityToResponseDto(updatedCandidate);
    }
}

