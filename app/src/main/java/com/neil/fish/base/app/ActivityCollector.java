package com.neil.fish.base.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2016/5/19.
 *
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void finishActivity(Class tClass) {
        for (Activity activity : activities) {
            if (activity.getClass().equals(tClass)) {
                activity.finish();
            }
        }
    }

}
