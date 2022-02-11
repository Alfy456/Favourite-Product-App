package com.dev.lab_1_2_alfygeorge_c0836170_android2.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private static final String PREF_NAME = "SharedPref";
    private static SharedPrefHelper ourInstance;
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private final boolean IS_SAVED = false;

    private SharedPrefHelper(Context context) {

        prefs = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = prefs.edit();
        editor.apply();
    }

    public static SharedPrefHelper getInstance(Context context) {

        if (ourInstance == null) {
            ourInstance = new SharedPrefHelper(context);
        }
        return ourInstance;
    }
    public boolean getBolIsSaved() {
        return prefs.getBoolean(String.valueOf(IS_SAVED), false);
    }

    public void setBolIsSaved(boolean isAutoStart) {
        editor.putBoolean(String.valueOf(IS_SAVED), isAutoStart);
        editor.commit();
    }

}
