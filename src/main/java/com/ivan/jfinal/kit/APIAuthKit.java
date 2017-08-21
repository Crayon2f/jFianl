package com.ivan.jfinal.kit;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * MD5签名处理类
 */
public class APIAuthKit {

    /**
     * 参数签名
     *
     * @param paramMap 需要签名的参数map
     * @param key      密钥
     * @return
     */
    public static String sign(Map<String, String> paramMap, String key) {

        return sign(paramMap, key, "UTF-8");
    }

    /**
     * 参数签名
     *
     * @param paramMap 需要签名的参数map
     * @param key      密钥
     * @param encode   是否编码
     * @return
     */
    public static String sign(Map<String, String> paramMap, String key, boolean encode) {

        return sign(paramMap, key, "UTF-8", encode);
    }

    /**
     * 参数签名
     *
     * @param paramMap 需要签名的参数map
     * @param key      密钥
     * @param charset  编码格式
     * @return
     */
    public static String sign(Map<String, String> paramMap, String key, String charset) {

        return sign(paramMap, key, charset, true);
    }

    /**
     * 参数签名
     *
     * @param paramMap 需要签名的参数map
     * @param key      密钥
     * @param charset  编码格式
     * @param encode   是否编码
     * @return
     */
    public static String sign(Map<String, String> paramMap, String key, String charset, boolean encode) {

        String text = StringKit.mapToHttpParams(paramMap, encode);

        return sign(text, key, charset);
    }

    /**
     * 签名字符串
     *
     * @param text 需要签名的字符串
     * @param key  密钥
     * @return 签名结果
     */
    public static String sign(String text, String key) {

        return sign(text, key, "UTF-8");
    }

    /**
     * 签名字符串
     *
     * @param text    需要签名的字符串
     * @param key     密钥
     * @param charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String charset) {

        text = text + key;

        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }

    /**
     * 校验签名是否正确
     *
     * @param paramMap 需要签名的参数map
     * @param sign     签名结果
     * @param key      密钥
     * @return 签名是否正确
     */
    public static boolean verifyHttpParam(Map<String, String[]> paramMap, String sign, String key) {

        return verifyHttpParam(paramMap, sign, key, "UTF-8");
    }

    /**
     * 校验签名是否正确
     *
     * @param paramMap 需要签名的参数map
     * @param sign     签名结果
     * @param key      密钥
     * @param charset  编码格式
     * @return 签名是否正确
     */
    public static boolean verifyHttpParam(Map<String, String[]> paramMap, String sign, String key, String charset) {

        String paramStr = StringKit.mapToHttpArrayParams(paramMap);
        String mySign = sign(paramStr, key, charset);

        if (mySign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验签名是否正确
     *
     * @param paramMap 需要签名的参数map
     * @param sign     签名结果
     * @param key      密钥
     * @return 签名是否正确
     */
    public static boolean verify(Map<String, String> paramMap, String sign, String key) {

        return verify(paramMap, sign, key, "UTF-8");
    }

    /**
     * 校验签名是否正确
     *
     * @param paramMap 需要签名的参数map
     * @param sign     签名结果
     * @param key      密钥
     * @param charset  编码格式
     * @return 签名是否正确
     */
    public static boolean verify(Map<String, String> paramMap, String sign, String key, String charset) {

        String mySign = sign(paramMap, key, charset);

        if (mySign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 签名字符串
     *
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key  密钥
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key) {

        return verify(text, sign, key, "UTF-8");
    }

    /**
     * 签名字符串
     *
     * @param text    需要签名的字符串
     * @param sign    签名结果
     * @param key     密钥
     * @param charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String charset) {

        String mySign = sign(text, key, charset);

        if (mySign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws java.security.SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}