package com.github.aleffalves.youtube_downloader.controller;

import com.github.aleffalves.youtube_downloader.dto.DownloadRequest;
import com.github.aleffalves.youtube_downloader.dto.DownloadResponse;
import com.github.aleffalves.youtube_downloader.service.DownloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/download")
public class DownloadController {
    @Autowired
    private DownloadService downloadService;

    @Operation(summary = "Download a file", description = "Downloads a file based on the provided file name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful download", content = {
                    @Content(mediaType = "application/json", schema = @Schema(oneOf = {DownloadResponse.class}))
            }),
            @ApiResponse(responseCode = "404", description = "File not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DownloadResponse> downloadYoutube(@Valid @RequestBody DownloadRequest request) {
        return ResponseEntity.ok().body(downloadService.downloadYoutube(request));
    }

}
