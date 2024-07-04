package com.ddabong.ddabongdotchiBE.domain.s3;

import static com.ddabong.ddabongdotchiBE.domain.global.enums.ApiPath.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ddabong.ddabongdotchiBE.domain.global.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(S3_URI)
public class S3TestController {

	private final S3Service s3Service;

	@PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
	public ApiResponse<String> uploadFile(@RequestPart(value = "file", required = false) MultipartFile file) {
		return ApiResponse.onSuccess(s3Service.uploadFile(file));
	}

	@PostMapping(value = "/uploadImage", consumes = "multipart/form-data")
	public ApiResponse<String> uploadImage(@RequestPart(value = "file", required = false) MultipartFile file) {
		return ApiResponse.onSuccess(s3Service.uploadImage(file));
	}

	@DeleteMapping(value = "/deleteImage")
	public ApiResponse<String> deleteImage(@RequestBody String path) {
		String image = path.substring(path.lastIndexOf('/') + 1);
		return ApiResponse.onSuccess(s3Service.deleteFile(image));
	}

	@PostMapping(value = "/uploadImages", consumes = "multipart/form-data")
	public ApiResponse<List<String>> uploadImages(
		@RequestPart(value = "files", required = false) List<MultipartFile> files) {
		return ApiResponse.onSuccess(s3Service.uploadFiles(files));
	}

}
