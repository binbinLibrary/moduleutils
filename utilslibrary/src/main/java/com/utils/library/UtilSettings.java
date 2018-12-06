package com.utils.library;

public class UtilSettings {
	public static final String FILE_PATH = "/nutritechinese";
	public static boolean debugMode = false;
	public static String filePath;

	public static void setDebugMode(boolean debugMode) {
		UtilSettings.debugMode = debugMode;
	}

	public static void setFilePath(String filePath) {
		UtilSettings.filePath = filePath;
	}
}
