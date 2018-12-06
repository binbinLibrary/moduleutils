package com.utils.library.box;

import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 编码工具
 */
public class EncodeUtils {
    public final static String CHARSET_UTF8 = "utf-8";
    private static char[] encodes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static byte[] decodes = new byte[256];

    /**
     * String URl 转成bundle。
     *
     * @param url
     * @return
     */
    public static Bundle parseStringUrlToBundle(String url) {
        try {
            URL u = new URL(url);
            Bundle b = decodeStringUrlPartToBundle(u.getQuery());
            b.putAll(decodeStringUrlPartToBundle(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
        }
        return new Bundle();
    }

    /**
     * 将url string 的一部分转为bundle。
     *
     * @param s
     * @return
     */
    public static Bundle decodeStringUrlPartToBundle(String s) {
        Bundle params = new Bundle();
        try {
            if (s != null) {
                String[] array = s.split("&");
                for (String parameter : array) {
                    String[] v = parameter.split("=");
                    params.putString(URLDecoder.decode(v[0], CHARSET_UTF8), URLDecoder.decode(v[1], CHARSET_UTF8));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * base62加密。
     *
     * @param data
     * @return
     */
    public static String encodeBase62(byte[] data) {
        StringBuffer sb = new StringBuffer(data.length * 2);
        int pos = 0;
        int val = 0;
        for (int i = 0; i < data.length; i++) {
            val = val << 8 | data[i] & 0xFF;
            pos += 8;
            while (pos > 5) {
                pos -= 6;
                char c = encodes[(val >> pos)];
                sb.append(c == '/' ? "ic" : c == '+' ? "ib" : c == 'i' ? "ia" : Character.valueOf(c));
                val &= (1 << pos) - 1;
            }
        }
        if (pos > 0) {
            char c = encodes[(val << 6 - pos)];
            sb.append(c == '/' ? "ic" : c == '+' ? "ib" : c == 'i' ? "ia" : Character.valueOf(c));
        }
        return sb.toString();
    }

    /**
     * base62 解密。
     *
     * @param string
     * @return
     */
    public static byte[] decodeBase62(String string) {
        if (string == null) {
            return null;
        }
        char[] data = string.toCharArray();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                string.toCharArray().length);
        int pos = 0;
        int val = 0;
        for (int i = 0; i < data.length; i++) {
            char c = data[i];
            if (c == 'i') {
                c = data[(++i)];
                c = c == 'c' ? '/' : c == 'b' ? '+' : c == 'a' ? 'i' : data[(--i)];
            }
            val = val << 6 | decodes[c];
            pos += 6;
            while (pos > 7) {
                pos -= 8;
                baos.write(val >> pos);
                val &= (1 << pos) - 1;
            }
        }
        return baos.toByteArray();
    }
}
