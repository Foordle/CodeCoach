package com.example.CodeCoach.repository;

import org.springframework.stereotype.Repository;
import java.io.*;
import java.nio.file.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Repository
public class CodeRunnerRepository {

    private static final Logger logger = Logger.getLogger(CodeRunnerRepository.class.getName());
    // 코드가 저장될 임시 디렉토리 (로컬 OS 경로)
    private static final String TEMP_REPO_DIR = "temp_repos";
    private static final String TEMP_FILE_DIR = "temp_code_storage";

    // --- A. Docker 실행용 ---
    public Path fetchAndSaveCode(String codeContent, String language) throws IOException {
        Path tempDirPath = Paths.get(TEMP_FILE_DIR).toAbsolutePath();

        if (!Files.exists(tempDirPath)) {
            Files.createDirectories(tempDirPath);
        }

        String fileName = "Main";
        String fileExtension = "";
        if ("JAVA".equalsIgnoreCase(language)) {
            fileExtension = ".java";
        } else if ("CPP".equalsIgnoreCase(language)) {
            fileExtension = ".cpp";
        } else {
            throw new IllegalArgumentException("지원하지 않는 언어입니다: " + language);
        }

        Path sourceFile = tempDirPath.resolve(fileName + fileExtension);

        try {
            Files.writeString(sourceFile, codeContent);
            logger.info("Code saved to: " + sourceFile.toAbsolutePath());
            return tempDirPath;
        } catch (IOException e) {
            logger.severe("File save failed: " + e.getMessage());
            throw new IOException("코드를 임시 디렉토리에 저장하는 데 실패했습니다.", e);
        }
    }


     // 임시 파일을 삭제, 정리
    public void cleanUpCodeFiles(Path path) {
        try {
            Files.deleteIfExists(path.resolve("Main.java"));
            Files.deleteIfExists(path.resolve("Main.class"));
            Files.deleteIfExists(path.resolve("Main.cpp"));
            Files.deleteIfExists(path.resolve("a.out")); // C++ 실행 파일
            logger.info("Temporary files cleaned up in: " + path.toAbsolutePath());
        } catch (IOException e) {
            logger.warning("Temporary file cleanup failed: " + e.getMessage());
        }
    }



    // --- B. Git URL 평가용 저장소 관리 ---

    /**
     * Git Repository를 복제하고, 그 안의 모든 코드를 읽어 하나의 문자열로 결합합니다.
     */
    public String cloneAndExtractCode(String githubUrl, String language) throws IOException, InterruptedException {
        String repoName = githubUrl.substring(githubUrl.lastIndexOf('/') + 1).replace(".git", "");
        Path tempDirPath = Paths.get(TEMP_REPO_DIR).toAbsolutePath().resolve(repoName);

        // 1. 기존 디렉토리 정리 (존재하면 삭제)
        if (Files.exists(tempDirPath)) {
            deleteDirectory(tempDirPath.toFile());
        }
        Files.createDirectories(tempDirPath.getParent());

        // 2. Git Clone 명령 실행
        ProcessBuilder pb = new ProcessBuilder("git", "clone", githubUrl, tempDirPath.toString());
        Process process = pb.start();

        if (process.waitFor() != 0) {
            String errorOutput = readStream(process.getErrorStream());
            logger.severe("Git Clone failed: " + errorOutput);
            // 실패 시 임시 폴더 삭제
            deleteDirectory(tempDirPath.toFile());
            throw new IOException("Git 클론 실패: " + errorOutput);
        }
        logger.info("Repository cloned to: " + tempDirPath);

        // 3. 복제된 디렉토리에서 코드 추출 및 결합
        String extractedCode = extractCodeFromDirectory(tempDirPath, language);

        // 4. 저장소 정리 (복제가 성공했더라도 바로 삭제)
        deleteDirectory(tempDirPath.toFile());

        return extractedCode;
    }

    // --- 헬퍼 메서드 ---

    private String extractCodeFromDirectory(Path repoPath, String language) throws IOException {
        StringBuilder fullCode = new StringBuilder();
        String extension = "." + language.toLowerCase();

        try (Stream<Path> stream = Files.walk(repoPath)) {
            stream.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(extension))
                    .forEach(path -> {
                        try {
                            fullCode.append("\n// FILE: ").append(repoPath.relativize(path)).append("\n");
                            fullCode.append(Files.readString(path));
                            fullCode.append("\n");
                        } catch (IOException e) {
                            logger.warning("Failed to read file: " + path);
                        }
                    });
        }

        if (fullCode.length() == 0) {
            return "ERROR: No " + extension + " files found in the repository.";
        }
        return fullCode.toString();
    }

    private void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        dir.delete();
        logger.info("Directory deleted: " + dir.getAbsolutePath());
    }

    private String readStream(InputStream is) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}


