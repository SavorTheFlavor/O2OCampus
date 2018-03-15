package com.me.o2ocampus.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	
	private static String basePath = System.getProperty("user.dir");
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final Random r = new Random();
	/**
	 *  generate compressed thumbnail with watermark 
	 * @param thumbnailFile
	 * @return
	 */
	public static File generateThumbnail(CommonsMultipartFile thumbnailFile, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnailFile);
		mkdirIfNotExist(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File destFile = new File(PathUtil.getImageBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnailFile.getInputStream())
				.size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/images/watermark.png")), 0.3f)
				.outputQuality(0.8f)
				.toFile(destFile);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return destFile;
	}

	private static void mkdirIfNotExist(String targetAddr) {
		File dest = new File(PathUtil.getImageBasePath() + targetAddr);
		if(!dest.exists()) {
			dest.mkdirs();
		}
	}

	private static String getFileExtension(CommonsMultipartFile thumbnailFile) {
		String originName = thumbnailFile.getOriginalFilename();
		return originName.substring(originName.lastIndexOf("."));
	}

	private static String getRandomFileName() {
		String nowTime = SIMPLE_DATE_FORMAT.format(new Date());
		int rNum = r.nextInt(89999) + 10000;
		return nowTime + rNum;
	}
	
//	public static void main(String[] args) throws Exception{
//		System.out.println(basePath);
//		System.out.println(getRandomFileName());
////		Thumbnails.of(new File("D:\\@.@\\07.jpg"))
////		.size(200, 200)
////		.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("E:\\workplace\\O2OCampus\\images\\watermark.png")), 0.35f)
////		.outputQuality(0.8f)
////		.toFile(new File("D:\\0777.jpg"));
//	}
	
}

