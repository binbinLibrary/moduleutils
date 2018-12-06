package com.module.linrary.box;

import android.util.Log;

import com.depend.kit.KitSettings;

public class LogUtils {
    public static final boolean IS_DEBUG = KitSettings.IS_DEBUG_MODE;

    public static String getObjectName(Object object) {
        String temp = object.getClass().getName();
        return temp.substring(temp.lastIndexOf(".") + 1, temp.length());
    }

    public static void v(Object obj, String tag, String msg) {
        if (IS_DEBUG) {
            Log.v(getObjectName(obj), tag + msg);
        }
    }

    public static void e(Object obj,String tag, String msg) {
        if (IS_DEBUG) {
            Log.e(getObjectName(obj), tag + msg);
        }
    }

    public static void i(Object obj, String tag, String msg) {
        if (IS_DEBUG) {
            Log.i(getObjectName(obj), tag + msg);
        }
    }

    public static void d(Object obj, String tag, String msg) {
        if (IS_DEBUG) {
            Log.d(getObjectName(obj), tag + msg);
        }
    }
}
