package com.module.linrary.box;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class FileSizeUtils {
    public static final String TAG = FileSizeUtils.class.getName();
    public static final int SIZE_TYPE_B = 1; // 获取文件大小单位为B的double值
    public static final int SIZE_TYPE_KB = 2; // 获取文件大小单位为KB的double值
    public static final int SIZE_TYPE_MB = 3; // 获取文件大小单位为MB的double值
    public static final int SIZE_TYPE_GB = 4; // 获取文件大小单位为GB的double值
    public static final int SIZE_B = 1; // 1B文件的字节值
    public static final int SIZE_KB = 1024; // 1KB文件的字节值
    public static final int SIZE_MB = 1024 * 1024; // 1MB文件的字节值
    public static final int SIZE_GB = 1024 * 1024 * 1024; // 1GB文件的字节值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("获取文件大小", TAG, "获取失败!");
        }
        return FormatFileSize(blockSize, sizeType);
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param file     文件
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileSize(File file, int sizeType) {
        if (file == null) {
            return 0;
        }
        long blockSize = 0;
        try {
            blockSize = getFileSize(file);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("获取文件大小", TAG, "获取失败!");
        }
        return FormatFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("获取文件大小", TAG, "获取失败!");
        }
        return FormatFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            LogUtils.e("获取文件大小", TAG, "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File file) throws Exception {
        long size = 0;
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size = size + getFileSizes(files[i]);
            } else {
                size = size + getFileSize(files[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileLength
     * @return
     */
    public static String FormatFileSize(long fileLength) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileLength == 0) {
            return wrongSize;
        }
        if (fileLength < 1024) {
            fileSizeString = df.format((double) fileLength / SIZE_B) + "B";
        } else if (fileLength < 1048576) {
            fileSizeString = df.format((double) fileLength / SIZE_KB) + "KB";
        } else if (fileLength < 1073741824) {
            fileSizeString = df.format((double) fileLength / SIZE_MB) + "MB";
        } else {
            fileSizeString = df.format((double) fileLength / SIZE_GB) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileLength 文件长度
     * @param sizeType   获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return
     */
    public static double FormatFileSize(long fileLength, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZE_TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileLength / SIZE_B));
                break;
            case SIZE_TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileLength / SIZE_KB));
                break;
            case SIZE_TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileLength / SIZE_MB));
                break;
            case SIZE_TYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileLength / SIZE_GB));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }
}
