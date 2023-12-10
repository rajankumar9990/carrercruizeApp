package com.example.carrercruize;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.UUID;

public class uniqueidGenerator {

    private static final String PREFS_NAME = "MyAppPreferences";
    private static final String UNIQUE_ID_KEY = "unique_id";

    public static String getUniqueId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String uniqueId = prefs.getString(UNIQUE_ID_KEY, null);

        if (uniqueId == null) {
            uniqueId = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(UNIQUE_ID_KEY, uniqueId);
            editor.apply();
        }

        return uniqueId;
    }
}