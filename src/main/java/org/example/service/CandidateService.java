package org.example.service;

import org.example.service.dto.CandidateRequestDto;
import org.example.service.dto.CandidateResponseDto;

import java.util.List;

public interface CandidateService {
    CandidateResponseDto saveCandidate(CandidateRequestDto candidateRequestDto);

    List<CandidateResponseDto> getCandidates();

    boolean isCandidateExist(Long id);

    CandidateResponseDto updateCandidate(Long id, CandidateRequestDto candidateRequestDto);
}
