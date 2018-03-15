package com.me.o2ocampus.util;

public class PathUtil {
	
	private static final String separator = System.getProperty("file.separator");
	
	public static String getImageBasePath() {
		String osName = System.getProperty("os.name");
		String basePath = "";
		if(osName.toLowerCase().startsWith("win")) {
			basePath = "D:/projectdev/o2o/images/";
		}else {
			basePath = "/home/o2o/images";
		}
		basePath = basePath.replace("/", separator);
		return basePath;
	}
	
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/"+shopId+"/";
		return imagePath.replace("/", separator);
	}
	
}
