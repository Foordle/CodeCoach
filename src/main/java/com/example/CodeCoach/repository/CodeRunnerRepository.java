package com.example.CodeCoach.repository;

import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Repository
public class CodeRunnerRepository {

    private static final Logger logger = Logger.getLogger(CodeRunnerRepository.class.getName());
    // 코드가 저장될 임시 디렉토리 (로컬 OS 경로)
    private static final String TEMP_DIR = "temp_code_storage";

    /**
     * 사용자로부터 받은 코드 텍스트를 로컬 디렉토리에 파일로 저장합니다.
     * @param codeContent 평가할 소스 코드 내용
     * @param language 선택된 언어 (JAVA 또는 CPP)
     * @return 코드가 저장된 임시 디렉토리 Path 객체
     */
    public Path fetchAndSaveCode(String codeContent, String language) throws IOException {
        Path tempDirPath = Paths.get(TEMP_DIR).toAbsolutePath();

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
    public void cleanUp(Path path) {
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
}