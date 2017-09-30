package ir.mastani.godzilla.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class Utilities {
    public static final int AppVersion = 1;
    public static boolean newVersionAvailable = false;

    public static String encodeString(String s) {
        byte[] data = new byte[0];
        try {
            data = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);
            return base64Encoded;
        }
    }

    public static String decodeString(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {
            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return decodedString;
        }
    }

    private static final String ALLOWED_CHARACTERS = "0123456789qweryuipsdfghjklzxbnm";

    public static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String getTextFromHtml(String html) {
        return Html.fromHtml(html).toString().trim();
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static boolean getTutorialState(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("tutorial", MODE_PRIVATE);
        return prefs.getBoolean("value", false);
    }

    public static void setTutorialState(Context context, boolean b) {
        SharedPreferences.Editor editor = context.getSharedPreferences("tutorial", MODE_PRIVATE).edit();
        editor.putBoolean("value", b);
        editor.apply();
    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getPhoneString() {
        return getDeviceName() + " " + getAndroidVersion();
    }
}
