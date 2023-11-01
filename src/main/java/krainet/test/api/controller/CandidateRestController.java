package krainet.test.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import krainet.test.service.CandidateService;
import krainet.test.service.annotation.IsCandidateExist;
import krainet.test.service.dto.CandidateRequestDto;
import krainet.test.service.dto.CandidateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/candidates")
@Tag(name = "Candidate Controller", description = "API for working with candidates")
public class CandidateRestController {
    private final CandidateService candidateService;

    @Operation(summary = "Add candidate")
    @ApiResponse(responseCode = "201", description = "CREATE",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = CandidateResponseDto.class))))
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CandidateResponseDto> add(@Valid
                                                    @RequestBody
                                                    CandidateRequestDto candidateRequestDto) {
        log.info("Adding new candidate with - {}", candidateRequestDto);

        CandidateResponseDto addedCandidate = candidateService.saveCandidate(candidateRequestDto);
        return new ResponseEntity<>(addedCandidate, CREATED);

    }

    @Operation(summary = "Get list of candidates from DB")
    @ApiResponse(responseCode = "200", description = "GET",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = CandidateResponseDto.class))))
    @GetMapping
    public ResponseEntity<List<CandidateResponseDto>> get() {
        log.info("Getting list of directions");
        List<CandidateResponseDto> directionDtos = candidateService.getCandidates();
        return ResponseEntity.ok(directionDtos);
    }

    @Operation(summary = "Update candidate")
    @ApiResponse(responseCode = "201", description = "CREATE",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = CandidateResponseDto.class))))
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CandidateResponseDto> update(
            @PathVariable("id") @IsCandidateExist Long id,
            @Valid
            @RequestBody
            CandidateRequestDto candidateRequestDto) {
        log.info("Updating candidate with - {}", candidateRequestDto);
        CandidateResponseDto updatedCandidate = candidateService.updateCandidate(id, candidateRequestDto);
        return new ResponseEntity<>(updatedCandidate, CREATED);
    }
}
