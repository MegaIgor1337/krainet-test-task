package org.example.api.controllers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.DirectionService;
import org.example.service.TestService;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestResponseDto;
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
@RequestMapping("api/v1/tests")
@Tag(name = "Test Controller", description = "API for working with tests")
public class TestRestController {
    private final TestService testService;
    private final DirectionService directionService;

    @Operation(summary = "Add test")
    @ApiResponse(responseCode = "201", description = "CREATE", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = TestRequestDto.class))))
    @PostMapping
    public ResponseEntity<TestResponseDto> add(@RequestBody
                                               @Valid
                                               TestRequestDto testRequestDto) {
        log.info("Adding new test - {}", testRequestDto);
        TestResponseDto addedTest = testService.saveTest(testRequestDto);
        return new ResponseEntity<>(addedTest, CREATED);
    }


    @Operation(summary = "Get list of directions from DB by Name, page number and size if the page")
    @ApiResponse(responseCode = "200", description = "GET", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = TestRequestDto.class))))
    @GetMapping
    public ResponseEntity<List<TestResponseDto>> get() {
        List<TestResponseDto> testResponseDtos = testService.getTests();
        return ResponseEntity.ok(testResponseDtos);
    }

}
