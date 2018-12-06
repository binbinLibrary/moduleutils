package com.module.linrary.box;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

public class FileUtils {
	public static boolean deleteDependOn(File file, int maxRetryCount) {
		int retryCount = 1;
		maxRetryCount = maxRetryCount < 1 ? 5 : maxRetryCount;
		boolean isDeleted = false;
		if (file != null) {
			do {
				if (!(isDeleted = file.delete())) {
					retryCount++;
				}
			} while ((!isDeleted) && (retryCount <= maxRetryCount) && (file.isFile()) && (file.exists()));
		}
		return isDeleted;
	}

	public static void mkdirs(File file) {
		if (file == null) {
			return;
		}
		if ((!file.exists()) && (!file.mkdirs())) {
			throw new RuntimeException("fail to make " + file.getAbsolutePath());
		}
	}

	public static void createNewFile(File file) {
		if (file == null) {
			return;
		}
		if (!isCreateNewFile(file)) {
			throw new RuntimeException(file.getAbsolutePath() + " doesn't be created!");
		}
	}

	public static void delete(File f) {
		if ((f != null) && (f.exists()) && (!f.delete())) {
			throw new RuntimeException(f.getAbsolutePath() + " doesn't be deleted!");
		}
	}

	public static boolean isCreateNewFile(File file) {
		if (file == null) {
			return false;
		}
		makeSureParentExist(file);
		if (file.exists()) {
			delete(file);
		}
		try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteDependOn(String filepath, int maxRetryCount) {
		if (TextUtils.isEmpty(filepath)) {
			return false;
		}
		return deleteDependOn(new File(filepath), maxRetryCount);
	}

	public static boolean deleteDependOn(String filepath) {
		return deleteDependOn(filepath, 0);
	}

	public static boolean doesExisted(File file) {
		return (file != null) && (file.exists());
	}

	public static boolean doesExisted(String filepath) {
		if (TextUtils.isEmpty(filepath)) {
			return false;
		}
		return doesExisted(new File(filepath));
	}

	public static void makeSureParentExist(File file) {
		if (file == null) {
			return;
		}
		File parent = file.getParentFile();
		if ((parent != null) && (!parent.exists())) {
			mkdirs(parent);
		}
	}

	public static void makeSureFileExist(File file) {
		if (file == null) {
			return;
		}
		if (!file.exists()) {
			makeSureParentExist(file);
			createNewFile(file);
		}
	}

	public static void makeSureFileExist(String filePath) {
		if (filePath == null) {
			return;
		}
		makeSureFileExist(new File(filePath));
	}
}
