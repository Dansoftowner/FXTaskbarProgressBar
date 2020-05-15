package com.nativejavafx.taskbar.util;

/**
 * Utility class is for checking the
 * type of the current platform that the
 * application runs on.
 */
public class OS {
    private OS() {
    }

    /**
     * Checks that the OS is Windows 7 or later
     *
     * @return <code>true</code> if the Os is windows 7 or later;
     *         <code>false</code> otherwise
     */
    public static boolean isWindows7OrLater() {
        double version = Double.parseDouble(System.getProperty("os.version"));
        double win7Version = 6.1; //Windows 7 has this version number

        return isWindows() && version >= win7Version;
    }

    /**
     * Checks that the OS is windows or not.
     *
     * @return <code>true</code> if the OS is windows;
     *         <code>false</code> otherwise
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }
}