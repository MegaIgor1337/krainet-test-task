package krainet.test.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import krainet.test.service.TestService;
import krainet.test.service.annotation.IsTestExist;
import krainet.test.service.dto.PageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import krainet.test.service.dto.TestRequestDto;
import krainet.test.service.dto.TestRequestFilter;
import krainet.test.service.dto.TestResponseDto;
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


    @Operation(summary = "Get list of tests from DB by Name, directions page number and size if the page")
    @ApiResponse(responseCode = "200", description = "GET", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = TestResponseDto.class))))
    @GetMapping
    public ResponseEntity<List<TestResponseDto>> get(
            @Valid TestRequestFilter testRequestFilter,
            @Valid PageRequestDto pageRequestDto
    ) {
        log.info("Getting list of directions");
        List<TestResponseDto> testsDtos = testService.getTests(testRequestFilter, pageRequestDto);
        return ResponseEntity.ok(testsDtos);
    }

    @Operation(summary = "Update Test", description = "Update Test by id")
    @ApiResponse(responseCode = "201", description = "UPDATE", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = TestResponseDto.class))))
    @PutMapping("/{id}")
    public ResponseEntity<TestResponseDto> update(@RequestBody @Valid
                                                  TestRequestDto testRequestDto,
                                                  @PathVariable(name = "id")
                                                  @IsTestExist @NotNull Long id) {
        log.debug("Updating Test with id {} by the data: {} ", id, testRequestDto);
        TestResponseDto responseTest = testService.updateTest(id, testRequestDto);
        return new ResponseEntity<>(responseTest, CREATED);
    }
}
