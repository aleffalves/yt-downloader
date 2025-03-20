# YouTube Downloader API

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-green)](https://spring.io/projects/spring-boot)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203.0-orange)](https://swagger.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This project is a RESTful API built with Spring Boot that allows you to download videos and playlists from YouTube in various formats (mp3, mp4). It leverages the `yt-dlp` command-line tool for the actual downloading process and utilizes multithreading to download playlists concurrently.

## Table of Contents

-   Features
-   Prerequisites
-   Installation
-   Configuration
-   Running the Application
-   API Endpoints
    -   Download Video/Playlist
    -   Download File
    -   Check System Status
-   Swagger Documentation
-   Error Handling
-   Contributing
-   License

## Features

-   **Download Videos:** Download individual YouTube videos in mp3 or mp4 format.
-   **Download Playlists:** Download entire YouTube playlists in mp3 or mp4 format concurrently using multithreading.
-   **Format Selection:** Choose between mp3 (audio) and mp4 (video) formats.
-   **RESTful API:** Interact with the application through a well-defined REST API.
-   **Swagger Documentation:** Comprehensive API documentation generated using Swagger/OpenAPI.
-   **System Status Check:** Verify if `yt-dlp` is installed and the system is ready for use.
-   **Parallel Downloads:** Download multiple songs from a playlist concurrently, significantly speeding up the process.

## Prerequisites

-   **Java 17:** Make sure you have Java 17 installed on your system.
-   **Maven:** You'll need Maven to build and run the project.
-   **yt-dlp:** This project relies on the `yt-dlp` command-line tool. You need to install it separately.
    -   **Windows:** `choco install yt-dlp`
    -   **Linux:** `sudo apt-get install yt-dlp`
    -   **macOS:** `brew install yt-dlp`
    -   **All:** `pip install --upgrade yt-dlp`

## Installation

1.  **Clone the repository:**
2.  **Navigate to the project directory:**
3.  **Build the project using Maven:**

## Configuration

The project uses Spring Boot, so most of the configuration is done through the `application.yml` file.

**`application.yml` Example:**
-   `download.directory`: The default directory where the downloaded files will be saved.
-   `download.threads`: The number of threads to use for parallel downloads (default is 5).

## Running the Application

1.  **Run the application using Maven:**
2.  **Alternatively, run the packaged JAR file:**
3. The application will start on port `8080` by default.

## API Endpoints

### Download Video/Playlist

-   **Endpoint:** `POST /api/download`
-   **Description:** Downloads a video or playlist from YouTube.
-   **Request Body:**
-   `youtubeUrl`: The URL of the YouTube video or playlist (required).
    -   `format`: The desired format for the download (`mp3` or `mp4`, default is `mp3`).
    -   `isPlaylist`: Indicates if the URL is a playlist (`true` or `false`, default is `false`).
    -   `downloadDirectory`: The directory where the files will be downloaded. If not provided, the default directory from `application.yml` will be used.
-   **Response (Video):**
-   **Response (Playlist):**

### Download File

-   **Endpoint:** `GET /api/download/file/{fileName}`
-   **Description:** Downloads a previously downloaded file.
-   **Path Parameter:**
    -   `fileName`: The name of the file to download.
-   **Response:** The file content (mp3 or mp4).

### Check System Status

-   **Endpoint:** `GET /api/system/check`
-   **Description:** Checks if `yt-dlp` is installed and the system is ready for use.
-   **Response:**

## Swagger Documentation

This project uses Swagger/OpenAPI to generate interactive API documentation.

-   **Access:** `http://localhost:8080/swagger-ui/index.html`

## Error Handling

The API returns standard HTTP status codes to indicate the result of each request:

-   **200 OK:** The request was successful.
-   **400 Bad Request:** The request was invalid (e.g., missing parameters, invalid format).
-   **404 Not Found:** The requested file was not found.
-   **500 Internal Server Error:** An unexpected error occurred on the server.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes and commit them.
4.  Push your changes to your fork.
5.  Submit a pull request.