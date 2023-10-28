package org.example.api.controllers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.DirectionService;
import org.example.service.annotation.IsDirectionExist;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
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


    @Operation(summary = "Get list of directions from DB by Name, page number and size if the page")
    @ApiResponse(responseCode = "200", description = "GET", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = DirectionResponseDto.class))))
    @GetMapping
    public ResponseEntity<List<DirectionResponseDto>> get(
            @RequestParam(value = "directionName", required = false) String directionName,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0")
            @Min(value = 0, message = "page number must not be less than 0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false)
            @Min(value = 1, message = "page size must not be less than 0") Integer pageSize
    ) {
        log.info("Getting list of directions");
        List<DirectionResponseDto> directionDtos = directionService.getDirections(directionName, pageNumber, pageSize);
        return ResponseEntity.ok(directionDtos);
    }


    @Operation(summary = "Add direction")
    @ApiResponse(responseCode = "201", description = "CREATE", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = DirectionResponseDto.class))))
    @PostMapping
    public ResponseEntity<DirectionResponseDto> add(@RequestBody
                                                    @Valid
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
    public ResponseEntity<DirectionResponseDto> update(@RequestBody @Valid
                                                       DirectionRequestDto directionRequestDto,
                                                       @PathVariable(name = "id")
                                                       @IsDirectionExist @NotNull Long id) {
        log.debug("Updating Direction with id {} by the data: {} ", id, directionRequestDto);
        DirectionResponseDto responseDirection = directionService.updateDirection(id, directionRequestDto);
        return new ResponseEntity<>(responseDirection, CREATED);
    }


}
