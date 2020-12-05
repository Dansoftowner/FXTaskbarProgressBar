package com.nativejavafx.taskbar.util;

/**
 * Utility class is for checking the
 * type of the current platform that the
 * application runs on.
 */
public class OS {

    private static final String OS = System.getProperty("os.name");
    private static final String VERSION = System.getProperty("os.version");

    private static final boolean WINDOWS = OS.startsWith("Windows");
    private static final boolean WINDOWS_7_OR_LATER = WINDOWS && versionGreaterThanOrEqualTo(6.1F);

    private OS() {
    }

    /**
     * Checks that the OS is Windows 7 or later
     *
     * @return <code>true</code> if the Os is windows 7 or later;
     *         <code>false</code> otherwise
     */
    public static boolean isWindows7OrLater() {
        return WINDOWS_7_OR_LATER;
    }

    public static boolean versionGreaterThanOrEqualTo(float value) {
        try {
            return Float.parseFloat(VERSION) >= value;
        } catch (Exception e) {
            return false;
        }
    }
}