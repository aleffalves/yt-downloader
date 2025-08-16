package com.github.aleffalves.youtube_downloader.service;

import com.github.aleffalves.youtube_downloader.dto.DownloadRequest;
import com.github.aleffalves.youtube_downloader.dto.DownloadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService {

    private static final Logger logger = LoggerFactory.getLogger(DownloadService.class);

    private static final String STATUS_ERROR = "error";
    private static final String STATUS_SUCCESS = "success";
    private static final String FORMAT_MP3 = "mp3";

    @Value("${download.directory}")
    private String downloadDirectory;

    public DownloadResponse downloadYoutube(DownloadRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("DownloadRequest não pode ser nulo");
        }
        return request.isPlaylist() ? downloadPlaylist(request) : downloadVideo(request);
    }

    public DownloadResponse downloadVideo(DownloadRequest request) {
        return download(request, false);
    }

    public DownloadResponse downloadPlaylist(DownloadRequest request) {
        return download(request, true);
    }

    private DownloadResponse download(DownloadRequest request, boolean isPlaylist) {
        if (!isYoutubeDLPInstalled()) {
            return DownloadResponse.builder()
                    .status(STATUS_ERROR)
                    .message("yt-dlp não está instalado ou não está no PATH")
                    .build();
        }

        // Usa o diretório da requisição, se fornecido, ou o valor padrão
        String outputDirectory = request.getDownloadDirectory() != null
                ? request.getDownloadDirectory()
                : downloadDirectory;

        try {
            String extension = FORMAT_MP3.equals(request.getFormat()) ? FORMAT_MP3 : "mp4";
            List<String> command = buildCommand(request.getYoutubeUrl(), extension, outputDirectory, isPlaylist);

            logger.info("Executando comando: {}", String.join(" ", command));
            Process process = new ProcessBuilder(command).redirectErrorStream(true).start();

            List<String> fileName = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info("yt-dlp: {}", line);
                    if (line.contains("[download] Destination:")) {
                        fileName.add(line.replace("[download] Destination: ".concat(outputDirectory), "")
                                         .replace("/", "")
                                         .replace("\\", "")
                                         .replace("webm", extension).trim());
                    }
                }
                while ((line = errorReader.readLine()) != null) {
                    logger.error("yt-dlp error: {}", line);
                }
            }

            int exitCode = process.waitFor();
            logger.info("Comando finalizado com código de saída: {}", exitCode);

            if (exitCode != 0) {
                return DownloadResponse.builder()
                        .filePath(outputDirectory)
                        .status(STATUS_ERROR)
                        .message("Erro ao baixar do YouTube. Código de saída: " + exitCode)
                        .build();
            }

            return DownloadResponse.builder()
                    .fileName(fileName)
                    .filePath(outputDirectory)
                    .status(STATUS_SUCCESS)
                    .message("Download realizado com sucesso")
                    .build();

        } catch (IOException e) {
            logger.error("Erro ao baixar do YouTube", e);
            return DownloadResponse.builder()
                    .status(STATUS_ERROR)
                    .message("Erro ao baixar do YouTube: " + e.getMessage())
                    .build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrompida durante o download", e);
            throw new RuntimeException("Thread interrompida", e);
        }
    }

    private List<String> buildCommand(String url, String format, String outputTemplate, boolean isPlaylist) {
        List<String> command = new ArrayList<>();
        command.add("yt-dlp");

        if (FORMAT_MP3.equals(format)) {
            command.add("-f");
            command.add("ba");
            command.add("-x");
            command.add("--audio-format");
            command.add("mp3");
            command.add("--audio-quality");
            command.add("320");
        } else {
            command.add("--format");
            command.add("bv*[height<=1080]+ba/b[height<=1080] --merge-output-format mp4");
        }

        command.add("-P");
        command.add(outputTemplate);

        if (isPlaylist) {
            command.add("--yes-playlist");
        }

        command.add(url);

        return command;
    }

    public boolean isYoutubeDLPInstalled() {
        try {
            Process process = new ProcessBuilder("yt-dlp", "--version").start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Erro ao verificar a instalação do yt-dlp", e);
            return false;
        }
    }

}
