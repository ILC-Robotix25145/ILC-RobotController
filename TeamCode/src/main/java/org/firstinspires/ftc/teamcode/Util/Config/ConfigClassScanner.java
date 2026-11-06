package org.firstinspires.ftc.teamcode.Util.Config;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

public final class ConfigClassScanner {

    private static final String TAG = "ConfigClassScanner";
    private static final String TEAM_PACKAGE = "org.firstinspires.ftc.teamcode";

    private ConfigClassScanner() {}

    public static List<Class<?>> findConfigClasses(Context context) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            DexFile dex = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = dex.entries();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement();
                if (!name.startsWith(TEAM_PACKAGE)) continue;
                try {
                    Class<?> cls = Class.forName(name, false,
                            ConfigClassScanner.class.getClassLoader());
                    classes.add(cls);
                } catch (Throwable ignored) {
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to scan DEX for @Config classes", e);
        }
        return classes;
    }
}