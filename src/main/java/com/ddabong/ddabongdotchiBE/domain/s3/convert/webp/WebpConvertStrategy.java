package com.ddabong.ddabongdotchiBE.domain.s3.convert.webp;

import java.io.File;

import com.ddabong.ddabongdotchiBE.domain.s3.convert.ImageFormat;

public interface WebpConvertStrategy {
	boolean identify(ImageFormat imageFormat);

	File convert(String fileName, File file, WebpCompressionParam param);
}
