package com.github.aleffalves.youtube_downloader.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadResponse {

    @Schema(description = "The name of the downloaded file", example = "[video_123.mp3]")
    private List<String> fileName;

    @Schema(description = "The path to the downloaded file", example = "/downloads/video_123.mp3")
    private String filePath;

    @Schema(description = "The status of the download", example = "success")
    private String status;

    @Schema(description = "A message describing the result of the download", example = "Download realizado com sucesso")
    private String message;

}
