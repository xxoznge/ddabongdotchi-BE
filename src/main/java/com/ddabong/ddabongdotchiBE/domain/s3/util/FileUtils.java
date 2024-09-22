package com.ddabong.ddabongdotchiBE.domain.s3.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FileUtils {

	private static final String TEMP_DIR_PATH = "tmp/";

	public static String getExtension(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName cannot be null");
		}
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException("fileName does not have an extension");
		}
		return fileName.substring(index + 1);
	}

	public static String removeExtension(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName cannot be null");
		}
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException("fileName does not have an extension");
		}
		return fileName.substring(0, index);
	}

	public static File convertToFile(MultipartFile multipartFile) {
		String tempDir = System.getProperty("java.io.tmpdir");
		String originalFilename = multipartFile.getOriginalFilename();
		String safeFilename = originalFilename.replaceAll("[^a-zA-Z0-9.\\-]", "_");
		String uniqueFilename = UUID.randomUUID() + "_" + safeFilename;
		File file = new File(tempDir, uniqueFilename);

		if (!Files.isWritable(Paths.get(tempDir))) {
			throw new IllegalStateException("No write permission to the temporary directory: " + tempDir);
		}

		long usableSpace = new File(tempDir).getUsableSpace();
		long requiredSpace = multipartFile.getSize();

		if (usableSpace < requiredSpace) {
			throw new IllegalStateException("Not enough disk space to store the file in the temporary directory");
		}

		try {
			multipartFile.transferTo(file);
		} catch (IOException e) {
			log.error("Failed to convert multipart file to file", e);
			throw new IllegalArgumentException("Failed to convert multipart file to file", e);
		}

		return file;
	}
	// public static File convertToFile(MultipartFile multipartFile) {
	// 	String tempDir = System.getProperty("java.io.tmpdir");
	// 	File file = new File(tempDir + multipartFile.getOriginalFilename());
	// 	log.info("File path: {}", file.getAbsolutePath());
	// 	try {
	// 		multipartFile.transferTo(file);
	// 	} catch (Exception e) {
	// 		throw new IllegalArgumentException("Failed to convert multipart file to file", e);
	// 	}
	// 	return file;
	// }
}
