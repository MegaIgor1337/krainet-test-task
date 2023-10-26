package org.example.api.controllers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.service.DirectionService;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/directions")
@Tag(name = "Direction Controller", description = "API for working with directions")
public class DirectionRestController {

    private final DirectionService directionService;

    @Operation(summary = "Get list of directions from DB")
    @ApiResponse(responseCode = "200", description = "GET", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = DirectionResponseDto.class))))
    @GetMapping()
    public ResponseEntity<List<DirectionResponseDto>> get() {
        List<DirectionResponseDto> directionDtos = directionService.getDirections();
        return ResponseEntity.ok(directionDtos);
    }

    @Operation(summary = "Add direction")
    @ApiResponse(responseCode = "201", description = "CREATE", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = DirectionResponseDto.class))))
    @PostMapping()
    public ResponseEntity<DirectionResponseDto> add(@RequestBody
                                                    @Valid
                                                    DirectionRequestDto directionRequestDto) {
        DirectionResponseDto addedDirection = directionService.addDirection(directionRequestDto);
        return new ResponseEntity<>(addedDirection, CREATED);
    }
}
