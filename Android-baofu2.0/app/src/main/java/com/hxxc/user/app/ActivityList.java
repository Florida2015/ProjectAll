package com.hxxc.user.app;

import android.app.Activity;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ActivityList {
    //MainActivity
    private static HashMap<String, Activity> mainActivitys = new LinkedHashMap<>();

    public static void addMainActivity(Activity activity) {
        mainActivitys.put(activity.getClass().toString(), activity);
    }

    public static void removeMainActivity() {
        mainActivitys.clear();
    }

    public static Activity getMainActivity(String classs) {
        return mainActivitys.get(classs);
    }
}
