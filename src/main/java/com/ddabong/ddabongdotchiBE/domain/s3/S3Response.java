package com.ddabong.ddabongdotchiBE.domain.s3;

import lombok.Builder;

@Builder
public record S3Response(
	String preSignedUrl,
	String imageUrl
) {
	public static S3Response from(String preSignedUrl, String imageUrl) {
		return S3Response.builder()
			.preSignedUrl(preSignedUrl)
			.imageUrl(imageUrl)
			.build();
	}
}
