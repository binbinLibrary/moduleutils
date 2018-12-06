package com.utils.library;

public class KitSettings {
	public static final String FILE_PATH = "/nutritechinese";
	public static boolean debugMode = false;
	public static String filePath;

	public static void setDebugMode(boolean debugMode) {
		KitSettings.debugMode = debugMode;
	}

	public static void setFilePath(String filePath) {
		KitSettings.filePath = filePath;
	}
}
