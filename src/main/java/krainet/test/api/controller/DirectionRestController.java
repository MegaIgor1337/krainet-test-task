package krainet.test.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import krainet.test.service.DirectionService;
import krainet.test.service.annotation.IsDirectionExist;
import krainet.test.service.dto.DirectionRequestDto;
import krainet.test.service.dto.DirectionResponseDto;
import krainet.test.service.dto.PageRequestDto;
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
@RequestMapping("api/v1/directions")
@Tag(name = "Direction Controller", description = "API for working with directions")
public class DirectionRestController {
    private final DirectionService directionService;

    @Operation(summary = "Get list of directions from DB by Name, page number and size of the page")
    @ApiResponse(responseCode = "200", description = "GET", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = DirectionResponseDto.class))))
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DirectionResponseDto>> get(
            @RequestParam(value = "name", required = false)
            String name,
            @Valid PageRequestDto pageRequestDto
    ) {
        log.info("Getting list of directions");
        List<DirectionResponseDto> directionDtos = directionService
                .getDirections(name, pageRequestDto);
        return ResponseEntity.ok(directionDtos);
    }


    @Operation(summary = "Add direction")
    @ApiResponse(responseCode = "201", description = "CREATE", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = DirectionResponseDto.class))))
    @PostMapping
    public ResponseEntity<DirectionResponseDto> add(
            @RequestBody
            @Valid @NotNull
            DirectionRequestDto directionRequestDto) {
        log.info("Adding new direction with the name - {} and description - {}",
                directionRequestDto.name(), directionRequestDto.description());
        DirectionResponseDto addedDirection = directionService.saveDirection(directionRequestDto);
        return new ResponseEntity<>(addedDirection, CREATED);
    }

    @Operation(summary = "Update Direction", description = "Update Direction by id")
    @ApiResponse(responseCode = "201", description = "UPDATE", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = DirectionResponseDto.class))))
    @PutMapping("/{id}")
    public ResponseEntity<DirectionResponseDto> update(
            @RequestBody @Valid @NotNull
            DirectionRequestDto directionRequestDto,
            @PathVariable(name = "id")
            @IsDirectionExist @NotNull Long id) {
        log.debug("Updating Direction with id of the candidate - {} by the data: {} ", id, directionRequestDto);
        DirectionResponseDto responseDirection = directionService.updateDirection(id, directionRequestDto);
        return new ResponseEntity<>(responseDirection, CREATED);
    }
}
