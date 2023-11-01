package krainet.test.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import krainet.test.service.annotation.IsCandidateExist;
import krainet.test.service.annotation.ValidationImage;
import krainet.test.service.dto.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import krainet.test.api.exceptions.BadRequestException;
import krainet.test.api.exceptions.ImageStorageException;
import krainet.test.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/candidates/{id}/image")
@Tag(name = "Image Controller", description = "API for working with images of candidates")
public class ImageRestController {
    private final ImageService fileService;

    @Operation(summary = "Put image of candidate")
    @ApiResponse(responseCode = "201", description = "CREATE",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = FileDto.class))))
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileDto> upload(
            @NotNull
            @ValidationImage
            @RequestPart("image")
            MultipartFile image,
            @IsCandidateExist
            @PathVariable("id") Long id) {
        log.info("Adding new image of the candidate with id - {}", id);
        try {
            FileDto fileDto = fileService.uploadImage(image, id);
            return new ResponseEntity<>(fileDto, CREATED);
        } catch (ImageStorageException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Ger image of candidate")
    @ApiResponse(responseCode = "200", description = "GET",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = byte[].class))))
    @GetMapping(produces = IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> download(
            @IsCandidateExist
            @PathVariable("id") Long id) {
        log.info("Get image of candidate with id - {}", id);
        byte[] image = fileService.getImage(id);
        return new ResponseEntity<>(image, OK);
    }

}
