package com.github.aleffalves.youtube_downloader.controller;

import com.github.aleffalves.youtube_downloader.service.DownloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private DownloadService youtubeDLService;

    @Operation(summary = "Check system status", description = "Checks if youtube-dl is installed and the system is ready for use")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "System status", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))
            })
    })
    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkSystem() {
        Map<String, Object> status = new HashMap<>();

        boolean youtubeDLInstalled = youtubeDLService.isYoutubeDLPInstalled();
        status.put("youtubeDLP", youtubeDLInstalled ? "installed" : "not installed");

        if (!youtubeDLInstalled) {
            status.put("message", "O yt-dlp não está instalado. Por favor, instale-o usando:\n" +
                    "- Windows: choco install yt-dlp\n" +
                    "- Linux: sudo apt-get install yt-dlp\n" +
                    "- macOS: brew install yt-dlp");
        } else {
            status.put("message", "Sistema pronto para uso!");
        }

        return ResponseEntity.ok(status);
    }
}