package org.danielsa.proiect_ps.utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class LanguageManager {
    private static JSONObject currentLanguage;

    public static CustomLocale fromStringToLocale(String language) {
        return switch (language) {
            case "ENGLISH" -> CustomLocale.ENGLISH;
            case "ROMANIAN" -> CustomLocale.ROMANIAN;
            case "DEUTSCH" -> CustomLocale.DEUTSCH;
            default -> null;
        };
    }

    public static void loadLanguage(CustomLocale locale) {
        try {
            InputStream is = LanguageManager.class.getResourceAsStream("/languages/" + locale.language() + ".json");
            if (is == null) {
                is = LanguageManager.class.getResourceAsStream("/languages/en.json");
            }
            JSONTokener token;
            if (is != null) {
                token = new JSONTokener(is);
                currentLanguage = new JSONObject(token);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            currentLanguage = new JSONObject();
        }
    }

    public static String getString(String key) {
        return currentLanguage.optString(key, "Key not found: " + key);
    }
}