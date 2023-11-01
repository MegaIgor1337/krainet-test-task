package krainet.test.service;

import krainet.test.service.dto.CandidateResponseDto;
import krainet.test.service.dto.CandidateRequestDto;

import java.util.List;

public interface CandidateService {
    CandidateResponseDto saveCandidate(CandidateRequestDto candidateRequestDto);

    List<CandidateResponseDto> getCandidates();

    boolean isCandidateExist(Long id);

    CandidateResponseDto updateCandidate(Long id, CandidateRequestDto candidateRequestDto);
}
