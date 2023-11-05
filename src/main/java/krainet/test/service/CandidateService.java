package krainet.test.service;

import krainet.test.service.dto.*;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CandidateService {
    CandidateResponseDto saveCandidate(CandidateRequestDto candidateRequestDto);

    List<CandidateResponseDto> getCandidates(List<SortCandidateFields> sortCandidateFields,
                                             Sort.Direction order, CandidateRequestFilter candidateFilter,
                                             PageRequestDto pageRequestDto);

    boolean isCandidateExist(Long id);

    CandidateResponseDto updateCandidate(Long id, CandidateRequestDto candidateRequestDto);
}
