package com.ivan.jfinal.kit;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class EncryptKit {

    private static final int MAX_INDEX = 32;

    public static String URLEncode(String value) {

        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    public static String URLDecode(String value) {

        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    public static String encode(Map<String, String> params) {

        String paramsStr = StringKit.mapToHttpParams(params);
        String sign = APIAuthKit.sign(paramsStr, "", "UTF-8").toUpperCase();
        int maxIndex = paramsStr.length() > MAX_INDEX ? paramsStr.length() : MAX_INDEX;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < maxIndex * 2; i++) {
            char c;
            if (i % 2 == 0) {
                c = charAt(paramsStr, i / 2);
            } else {
                c = charAt(sign, i / 2);
            }
            sb.append(encode(c, i));
        }

        return sb.toString().toUpperCase();
    }

    private static char charAt(String str, int index) {

        char c = ' ';
        if (str.length() > index) {
            c = str.charAt(index);
        }
        return c;
    }

    private static String encode(char c, int index) {

        if (0 == index % 2) {
            String str = Integer.toHexString(c * 10);
            return str.replaceAll("0", "Z");
        } else {
            return String.valueOf((char) (c + 17));
        }
    }

    public static Map<String, String> decode(String string) {

        Map<String, String> map = new HashMap<>();
        StringBuffer signSB = new StringBuffer();
        StringBuffer paramsSB = new StringBuffer();

        int i = 0, j = 0;
        while (i < string.length()) {
            if (j % 2 == 0) {
                String re16 = string.substring(i, i + 3);
                int ascii = Integer.parseInt(re16.replaceAll("Z", "0"), 16) / 10;
                paramsSB.append((char) ascii);
                i += 3;
            } else {
                String str = string.substring(i, i + 1);
                signSB.append((char) (str.charAt(0) - 17));
                i++;
            }
            j++;
        }
        String sign = signSB.toString().trim();
        String paramsStr = paramsSB.toString().trim();
        if (!APIAuthKit.verify(paramsStr, sign.toLowerCase(), "", "UTF-8")) {
            throw new RuntimeException("auth error");
        }
        for (String mapStr : paramsStr.split("&")) {
            map.put(mapStr.split("=")[0], URLDecode(mapStr.split("=")[1]));
        }

        return map;
    }

    /**
     * MD5 加密
     *
     * @param string 字符串
     * @return 加密后字符串
     */
    public static String md5(String string) {

        if (StringKit.isBlank(string)) {
            return string;
        }

        return DigestUtils.md5Hex(string.getBytes());
    }

    public static String sha1(String string) {

        if (StringKit.isBlank(string)) {
            return string;
        }
        return DigestUtils.sha1Hex(string.getBytes());
    }
}