package org.example.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.service.DirectionService;
import org.example.service.dto.DirectionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "Direction Controller", description = "API for working with directions")
public class DirectionRestController {

    private final DirectionService directionService;

    @Operation(summary = "Get list of directions from DB")
    @ApiResponse(responseCode = "200", description = "GET", content = @Content(mediaType = APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = DirectionDto.class))))
    @GetMapping("/directions")
    public ResponseEntity<List<DirectionDto>>get() {
        List<DirectionDto> directionDtos = directionService.getDirections();
        return ResponseEntity.ok(directionDtos);
    }

}
