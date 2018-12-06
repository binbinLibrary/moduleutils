package com.module.linrary.box;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    public final static String APPLICATION_PREFERENCE_NAME = "dependkit";

    private static SharedPreferences getApplicationPreference(Context context) {
        return context.getSharedPreferences(APPLICATION_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean getSharedPreferenceAsBoolean(Context context, String name, boolean defaultValue) {
        return getApplicationPreference(context).getBoolean(name, defaultValue);
    }

    public static void setSharedPreferenceAsBoolean(Context context, String name, boolean value) {
        SharedPreferences.Editor editor = getApplicationPreference(context).edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static float getSharedPreferenceAsFloat(Context context, String name, float defaultValue) {
        return getApplicationPreference(context).getFloat(name, defaultValue);
    }

    public static void setSharedPreferenceAsFloat(Context context, String name, float value) {
        SharedPreferences.Editor editor = getApplicationPreference(context).edit();
        editor.putFloat(name, value);
        editor.commit();
    }

    public static int getSharedPreferenceAsInt(Context context, String name, int defaultValue) {
        return getApplicationPreference(context).getInt(name, defaultValue);
    }

    public static void setSharedPreferenceAsInt(Context context, String name, int value) {
        SharedPreferences.Editor editor = getApplicationPreference(context).edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static long getSharedPreferenceAsLong(Context context, String name, long defaultValue) {
        return getApplicationPreference(context).getLong(name, defaultValue);
    }

    public static void setSharedPreferenceAsLong(Context context, String name, long value) {
        SharedPreferences.Editor editor = getApplicationPreference(context).edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public static String getSharedPreference(Context context, String name, String defaultValue) {
        return getApplicationPreference(context).getString(name, defaultValue);
    }

    public static void setSharedPreference(Context context, String name, String value) {
        SharedPreferences.Editor editor = getApplicationPreference(context).edit();
        editor.putString(name, value);
        editor.commit();
    }
}
