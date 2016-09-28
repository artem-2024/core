package com.android.core.util;

import android.util.Base64;

/**
 * Created by Administrator on 2016/7/20.
 */
public class CodeUtils {
    // encode和decode

    static public String encodeToString(String code) {
        if (code == null || code.length() == 0) {
            return "";
        }
        return Base64.encodeToString(code.getBytes(), Base64.NO_WRAP);
    }

    static public String decodeToSgtring(String code) {
        if (code == null || code.length() == 0) {
            return "";
        }
        try {
            return new String(Base64.decode(code.getBytes(), Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
            return code;
        }


    }

    static public String decodeToSgtring(byte[] code) {
        if (code == null || code.length == 0) {
            return "";
        }
        return new String(Base64.decode(code, Base64.NO_WRAP));
    }

    static public byte[] decodeToByte(String code) {
        if (code == null || code.length() == 0) {
            return null;
        }
        return Base64.decode(code.getBytes(), Base64.NO_WRAP);
    }

    static public byte[] decodeToByte(byte[] code) {
        if (code == null || code.length == 0) {
            return null;
        }
        return Base64.decode(code, Base64.NO_WRAP);
    }
}
