package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.entity.Candidate;
import org.example.persistence.repository.CandidateRepository;
import org.example.service.CandidateService;
import org.example.service.dto.CandidateRequestDto;
import org.example.service.dto.CandidateResponseDto;
import org.example.service.mapper.CandidateMapper;
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

