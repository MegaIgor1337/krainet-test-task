package krainet.test.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import krainet.test.api.exceptions.BadRequestException;
import krainet.test.api.exceptions.CvStorageException;
import krainet.test.service.CvService;
import krainet.test.service.annotation.IsCandidateExist;
import krainet.test.service.annotation.ValidationCv;
import krainet.test.service.dto.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/candidates/{id}/cv")
@Tag(name = "Document cv Controller", description = "API for working with cv of candidates")
public class DocumentCvRestController {
    private final CvService cvService;

    @Operation(summary = "Put document cv of the candidate")
    @ApiResponse(responseCode = "201", description = "CREATE",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = FileDto.class))))
    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileDto> add(
            @IsCandidateExist @NotNull
            @PathVariable("id") Long id,
            @ValidationCv @NotNull
            @RequestPart("cv")
            MultipartFile multipartFile) {
        log.info("Adding new cv of the candidate with id - {}", id);
        try {
            FileDto fileDto = cvService.uploadCv(multipartFile, id);
            return new ResponseEntity<>(fileDto, CREATED);
        } catch (CvStorageException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Get cv document of the candidate")
    @ApiResponse(responseCode = "200", description = "GET",
            content = @Content(mediaType = "application/pdf"))
    @GetMapping(produces = APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> get(
            @IsCandidateExist @NotNull
            @PathVariable("id") Long id) {
        log.info("Get cv of candidate with id - {}", id);
        byte[] cv = cvService.getCv(id);
        return new ResponseEntity<>(cv, createHeadersWithContent(id), OK);
    }

    private HttpHeaders createHeadersWithContent(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                                                     + "Id of the candidate - " + id + ".pdf");
        return headers;
    }
}
