package com.my.baselibrary.utils;

import java.security.MessageDigest;

/**
 * Created by AIJU on 2017-06-06.
 */

public class MD5Utils {
    public static String md5Signature(String string) {
        String result = null;
        if (string == null) return result;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(string.toString().getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }
        return result;
    }

    /***
     * 二进制转字符串
     */
    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs.append("0").append(stmp);
            else hs.append(stmp);
        }
        return hs.toString();
    }
}
