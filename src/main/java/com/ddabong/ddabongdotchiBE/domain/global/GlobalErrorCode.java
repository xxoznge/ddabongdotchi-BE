package com.ddabong.ddabongdotchiBE.domain.global;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {

	// 가장 일반적인 응답
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	// 사용자 관련 에러
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USR4001", "사용자가 없습니다."),
	NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USR4002", "닉네임은 필수 입니다."),

	// 예시,,,
	ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

	// Page Error
	PAGE_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "PAGE_4001", "페이지 범위를 벗어났습니다."),

	// For test
	TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트"),

	VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "ERROR4000", "입력값에 대한 검증에 실패했습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR5000", "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ApiResponse<Void> getErrorResponse() {
		return ApiResponse.onFailure(code, message);
	}

}
