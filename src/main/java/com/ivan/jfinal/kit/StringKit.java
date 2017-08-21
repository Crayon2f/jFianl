package com.ivan.jfinal.kit;

import com.jfinal.kit.StrKit;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class StringKit extends StrKit {

    private static NumberFormat FORMAT_CHINA = NumberFormat.getCurrencyInstance(Locale.CHINA);
    // 随机字符串
    private static final String _INT = "0123456789";
    private static final String _STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String _ALL = _INT + _STR;

    private static final Random RANDOM = new Random();

    /**
     * 生成的随机数类型
     */
    public static enum RandomType {
        INT, STRING, ALL;
    }

    /**
     * 随机数生成
     *
     * @param count
     * @return
     */
    public static String random(int count, RandomType randomType) {
        if (count == 0) return "";
        if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            if (randomType.equals(RandomType.INT)) {
                buffer[i] = _INT.charAt(RANDOM.nextInt(_INT.length()));
            } else if (randomType.equals(RandomType.STRING)) {
                buffer[i] = _STR.charAt(RANDOM.nextInt(_STR.length()));
            } else {
                buffer[i] = _ALL.charAt(RANDOM.nextInt(_ALL.length()));
            }
        }
        return new String(buffer);
    }


    /**
     * 将字符串列表转换成有序的字符串
     *
     * @param array     字符串列表
     * @param separator 分隔符
     * @return 转换后的字符串
     */
    public static String formatListToOrderedStr(String[] array, String separator) {

        if (null == array || array.length <= 0) {
            return "";
        }

        separator = null == separator ? "" : separator;
        StringBuffer sb = new StringBuffer();

        Arrays.sort(array);

        for (String str : array) {
            if (StringKit.notBlank(str)) {
                sb.append(separator).append(str);
            }
        }

        return sb.substring(separator.length());
    }

    /**
     * 货币中国显示方式
     *
     * @param money
     * @return
     */
    public static String formatChinaMoney(double money) {

        return FORMAT_CHINA.format(money).substring(1);
    }

    /**
     * 获取随机数字
     *
     * @param intLength 长度
     * @return 指定长度的数字串
     * @deprecated 建议使用random方法
     */
    public static String getRandomNumStr(int intLength) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < intLength; ++i) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    /**
     * map 转换成按字母顺序排序后的http参数字符串
     *
     * @param paramMap
     * @return
     */
    public static String mapToHttpParams(Map<String, String> paramMap) {

        return mapToHttpParams(paramMap, true);
    }

    /**
     * map 转换成按字母顺序排序后的http参数字符串
     *
     * @param paramMap
     * @param encode   是否urlEncode
     * @return
     */
    public static String mapToHttpParams(Map<String, String> paramMap, boolean encode) {

        if (null == paramMap || paramMap.keySet().isEmpty()) {
            return "";
        }

        List<String> keyList = new ArrayList<>(paramMap.keySet());
        Collections.sort(keyList);

        StringBuilder paramSB = new StringBuilder();
        for (String name : keyList) {
            if (StringKit.isBlank(paramMap.get(name))) {
                continue;
            }
            String value = paramMap.get(name);
            if (encode) {
                value = EncryptKit.URLEncode(value);
            }
            paramSB.append("&").append(name).append("=").append(value);
        }

        return paramSB.substring(1);
    }


    public static String mapToHttpArrayParams(Map<String, String[]> httpParam) {

        return mapToHttpArrayParams(httpParam, true);
    }

    /**
     * map 转换成按字母顺序排序后的http参数字符串
     *
     * @param httpParam http参数
     * @param encode    是否urlEncode
     * @return
     */
    public static String mapToHttpArrayParams(Map<String, String[]> httpParam, boolean encode) {

        if (null == httpParam || httpParam.keySet().isEmpty()) {
            return "";
        }

        List<String> keyList = new ArrayList<>(httpParam.keySet());
        Collections.sort(keyList);

        StringBuilder paramSB = new StringBuilder();
        for (String name : keyList) {
            String[] values = httpParam.get(name);

            if (null == values || values.length < 1) {
                continue;
            }
            for (String value : values) {
                if (encode) {
                    value = EncryptKit.URLEncode(value);
                }
                paramSB.append("&").append(name).append("=").append(value);
            }
        }

        return paramSB.substring(1);
    }

    /**
     * 判断字符串是否是中文汉字和符号
     *
     * @param strName
     * @return 是否是中文和符号
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据Unicode编码判断中文汉字和符号
     *
     * @param c
     * @return 是否为中文和符号
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是CJK
     *
     * @param str
     * @return 是否是CJK文字
     */
    public static boolean isCJK(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 生成 18 位订单号
     *
     * @return 订单号
     */
    public static String genOrderCode() {

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");

        return dateFormat.format(now) + random(3, RandomType.INT);
    }

    /**
     * 生成带有前缀的订单号（前缀 + 18位）
     *
     * @param prefix 前缀
     * @return 订单号
     */
    public static String genOrderCode(String prefix) {

        return prefix + genOrderCode();
    }

    /**
     * 对象转换字符串
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {

        if (null == obj || "null".equals(obj)) {
            return "";
        } else {
            return obj.toString().trim();
        }
    }

    public static String trim(String str) {

        return toString(str);
    }
}