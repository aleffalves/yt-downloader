package com.github.aleffalves.youtube_downloader.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DownloadRequest {

    @NotBlank(message = "Youtube URL is obrigatory")
    @Schema(description = "The URL of the YouTube video or playlist", example = "https://www.youtube.com/watch?v=dQw4w9WgXcQ")
    private String youtubeUrl;

    @Schema(description = "The desired format for the download (mp3 or mp4)", example = "mp3", defaultValue = "mp3")
    private String format = "mp3";

    @Schema(description = "Indicates if the URL is a playlist", example = "false", defaultValue = "false")
    private boolean isPlaylist = false;

    @Schema(description = "Path to the download directory", example = "C/Users/User/Downloads", defaultValue = "false")
    private String downloadDirectory;


}
