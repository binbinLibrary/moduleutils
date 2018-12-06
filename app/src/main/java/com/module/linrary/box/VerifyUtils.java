package com.module.linrary.box;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtils {
    public static final String TAG = VerifyUtils.class.getName();

    /**
     * 验证手机格式
     * 手机号码: 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[0, 1, 6, 7, 8], 18[0-9]
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        String telRegex = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$";
        telRegex = "^1(3[0-9]|4[5,7]|5[0-3,5-9]|7[0,1,3,5,6,7,8]|8[0-9])\\d{8}$";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    /**
     * 验证密码是否由字母和26个英文字母组成
     * ^ 匹配一行的开头位置
     * (?![0-9]+$) 预测该位置后面不全是数字
     * (?![a-zA-Z]+$) 预测该位置后面不全是字母
     * [0-9A-Za-z] {6,12} 由6-12位数字或这字母组成
     * $ 匹配行结尾位置
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        String passwordStr = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";
        if (TextUtils.isEmpty(password)) {
            return false;
        } else {
            return password.matches(passwordStr);
        }
    }

    /**
     * 验证邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String emailStr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return email.matches(emailStr);
        }
    }

    /**
     * 验证字符串是否符合数字、英文字母、汉字
     *
     * @param str
     * @return
     */
    public static boolean isStringFilter(String str) {
        String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

    /**
     * 验证是否全是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    private static String cardNumber; // 完整的身份证号码
    private static Date cacheBirthDate = null; // 缓存出生日期，因为出生日期使用频繁且计算复杂
    private static final String BIRTH_DATE_FORMAT = "yyyyMMdd"; // 身份证号码中的出生日期的格式
    private static final Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L); // 身份证的最小出生日期,1900年1月1日
    private static final int NEW_CARD_NUMBER_LENGTH = 18;
    private static final int OLD_CARD_NUMBER_LENGTH = 15;
    private static final char[] VERIFY_CODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'}; // 18位身份证中最后一位校验码
    private static final int[] VERIFY_CODE_WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};// 18位身份证中，各个数字的生成校验码时的权值

    /**
     * 验证身份证号
     * 如果是15位身份证号码，则自动转换为18位
     * 完整身份证号码的省市县区检验规则
     *
     * @param cardNumber
     * @return
     */
    public static Boolean isIDCard(String cardNumber) {
        boolean validateResult;
        LogUtils.e("", TAG, "cardNumber=" + cardNumber);
        if (null != cardNumber) {
            cardNumber = cardNumber.trim();
            if (OLD_CARD_NUMBER_LENGTH == cardNumber.length()) {
                cardNumber = convertToNewCardNumber(cardNumber);
            }
        }
        setCardNumber(cardNumber);
        boolean result = true;
        result = result && (null != cardNumber); // 身份证号不能为空
        LogUtils.e("", TAG, "身份证号不能为空,result=" + result);
        result = result && NEW_CARD_NUMBER_LENGTH == cardNumber.length(); // 身份证号长度是18(新证)
        LogUtils.e("", TAG, "身份证号长度是18(新证),result=" + result);
        // 身份证号的前17位必须是阿拉伯数字
        for (int i = 0; result && i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            result = result && ch >= '0' && ch <= '9';
            LogUtils.e("", TAG, "身份证号的前" + i + "位必须是阿拉伯数字,result=" + result);
        }
        // 身份证号的第18位校验正确
        result = result && (calculateVerifyCode(cardNumber) == cardNumber.charAt(NEW_CARD_NUMBER_LENGTH - 1));
        LogUtils.e("", TAG, "身份证号的第18位校验,result=" + result);
        // 出生日期不能晚于当前时间，并且不能早于1900年
        try {
            Date birthDate = getBirthDate();
            result = result && null != birthDate;
            LogUtils.e("", TAG, "出生日期不能为空" + DateUtils.dateConvertStringByPattern(birthDate, "yyyy-MM-dd") + ",result=" + result);
            result = result && birthDate.before(new Date());
            LogUtils.e("", TAG, "出生日期不能晚于" + DateUtils.dateConvertStringByPattern(new Date(), "yyyy-MM-dd") + ",result=" + result);
            result = result && birthDate.after(MINIMAL_BIRTH_DATE);
            LogUtils.e("", TAG, "出生日期不能早于" + DateUtils.dateConvertStringByPattern(MINIMAL_BIRTH_DATE, "yyyy-MM-dd") + ",result=" + result);
            /**
             * 出生日期中的年、月、日必须正确,比如月份范围是[1,12],日期范围是[1,31]，还需要校验闰年、大月、小月的情况时，
             * 月份和日期相符合
             */
            String birthdayPart = getBirthDayPart();
            String realBirthdayPart = createBirthDateParser().format(birthDate);
            result = result && (birthdayPart.equals(realBirthdayPart));
            LogUtils.e("", TAG, "birthdayPart=" + birthdayPart + "realBirthdayPart=" + realBirthdayPart + ",result=" + result);
        } catch (Exception e) {
            result = false;
        }
        validateResult = result;
        LogUtils.e("", TAG, "validateResult=" + validateResult);
        return validateResult;
    }

    public static String getCardNumber() {
        return cardNumber;
    }

    public static void setCardNumber(String cardNumber) {
        VerifyUtils.cardNumber = cardNumber;
    }

    public static String getAddressCode() {
        return getCardNumber().substring(0, 6);
    }

    public static Date getBirthDate() {
        try {
            cacheBirthDate = createBirthDateParser().parse(getBirthDayPart());
        } catch (Exception e) {
            throw new RuntimeException("身份证的出生日期无效");
        }
        return new Date(cacheBirthDate.getTime());
    }

    private static String getBirthDayPart() {
        LogUtils.e("", TAG, "getBirthDayPart=" + getCardNumber());
        return getCardNumber().substring(6, 14);
    }

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat createBirthDateParser() {
        return new SimpleDateFormat(BIRTH_DATE_FORMAT);
    }

    /**
     * 校验码（第十八位数）：
     * 十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0...16 ，先对前17位数字的权求和；
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
     * 2; 计算模 Y = mod(S, 11)< 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9
     * 8 7 6 5 4 3 2
     *
     * @param cardNumber
     * @return
     */
    private static char calculateVerifyCode(CharSequence cardNumber) {
        int sum = 0;
        for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            sum += ((int) (ch - '0')) * VERIFY_CODE_WEIGHT[i];
        }
        return VERIFY_CODE[sum % 11];
    }

    /**
     * 把15位身份证号码转换到18位身份证号码<br>
     * 15位身份证号码与18位身份证号码的区别为：<br>
     * 1、15位身份证号码中，"出生年份"字段是2位，转换时需要补入"19"，表示20世纪<br>
     * 2、15位身份证无最后一位校验码。18位身份证中，校验码根据根据前17位生成
     *
     * @param oldCardNumber
     * @return
     */
    private static String convertToNewCardNumber(String oldCardNumber) {
        StringBuilder buf = new StringBuilder(NEW_CARD_NUMBER_LENGTH);
        buf.append(oldCardNumber.substring(0, 6));
        buf.append("19");
        buf.append(oldCardNumber.substring(6));
        buf.append(calculateVerifyCode(buf));
        return buf.toString();
    }
}
