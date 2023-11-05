package krainet.test.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import krainet.test.service.TestResultService;
import krainet.test.service.annotation.IsTestResultExist;
import krainet.test.service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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
@RequestMapping("api/v1/tests/results")
@Tag(name = "Test Result Controller", description = "API for working with results of the tests of the candidates")
public class TestResultRestController {
    private final TestResultService testResultService;

    @Operation(summary = "Get list of the test results from DB")
    @ApiResponse(responseCode = "200", description = "GET",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = TestResultResponseDto.class))))
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TestResultResponseDto>> get(
            @RequestParam(name = "sort", required = false)
            List<SortTestResultFields> sortFields,
            @RequestParam(name = "order", required = false, defaultValue = "ASC")
            Sort.Direction order,
            @Valid TestResultRequestFilter filter,
            @Valid PageRequestDto page
    ) {
        log.info("Getting list of the test results");
        List<TestResultResponseDto> testResultResponseDtos = testResultService.getTestResult(sortFields, order,
                filter, page);
        return ResponseEntity.ok(testResultResponseDtos);
    }


    @Operation(summary = "Add result")
    @ApiResponse(responseCode = "201", description = "CREATE",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = TestResultResponseDto.class))))
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TestResultResponseDto> add(
            @Valid
            @RequestBody
            TestResultRequestDto testResultRequestDto) {
        log.info("Adding new test result with - {}", testResultRequestDto);
        TestResultResponseDto addedTestResult = testResultService.saveTestResult(testResultRequestDto);
        return new ResponseEntity<>(addedTestResult, CREATED);
    }

    @Operation(summary = "Update test result", description = "Update test result by id")
    @ApiResponse(responseCode = "201", description = "UPDATE", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = TestResultResponseDto.class))))
    @PutMapping("/{id}")
    public ResponseEntity<TestResultResponseDto> update(
            @RequestBody @Valid
            TestResultRequestDto testResultRequestDto,
            @PathVariable(name = "id")
            @IsTestResultExist @NotNull Long id) {
        log.debug("Updating Test result with id {} by the data: {} ", id, testResultRequestDto);
        TestResultResponseDto testResultResponseDto = testResultService.updateTestResult(id, testResultRequestDto);
        return new ResponseEntity<>(testResultResponseDto, CREATED);
    }
}
