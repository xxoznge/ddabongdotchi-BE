package com.ddabong.ddabongdotchiBE.domain.card.dto.response;

import lombok.Builder;

@Builder
public record CardImageUploadResponse(
	String imageUrl
) {
}
