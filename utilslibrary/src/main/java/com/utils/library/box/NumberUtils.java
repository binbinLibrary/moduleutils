package com.utils.library.box;

import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtils {
    public static final String PATTERN1 = "#.0";
    public static final String PATTERN2 = "#.00";
    public static final String PATTERN3 = "###################.###########";

    public static int parseStringToInteger(String valueString, int defaultValue) {
        if (valueString == null)
            return defaultValue;
        int result = defaultValue;
        try {
            result = Integer.parseInt(valueString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static long parseStringToLong(String valueString, long defaultValue) {
        if (valueString == null)
            return defaultValue;
        long result = defaultValue;
        try {
            result = Long.parseLong(valueString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static float parseStringToFloat(String valueString, float defaultValue) {
        if (valueString == null)
            return defaultValue;
        float result = defaultValue;
        try {
            result = Float.parseFloat(valueString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 计算显示数量
     */
    public static String numberUnitConvert(double number, String pattern) {
        if (number < 10000) {
            return String.valueOf((int) number);
        } else {
            double myriad = number / 10000;
            return String.format("%1$s万", "" + numberConvertStringByPattern(myriad, pattern));
        }
    }

    /**
     * 数量格式转换
     */
    public static String numberConvertStringByPattern(double number, String pattern) {
        if (number == 0) {
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(number);
    }

    /**
     * 获取指定范围内的随机数
     */
    public static int getRandomNumber(int maxNumber) {
        Random random = new Random();
        return random.nextInt(maxNumber);
    }
}
