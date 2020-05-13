package com.nativejavafx.taskbar;

import com.sun.javafx.stage.StageHelper;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Objects;

/**
 * Provides some utility methods
 */
class Utils {

    /**
     * Returns the index of the given stage in the window-hierarchy
     *
     * @param stage to get the index of
     * @return the index
     */
    static int getIndexOfStage(Stage stage) {
        return StageHelper.getStages().indexOf(stage);
    }


    /**
     * Checks that the OS is Windows 7 or later
     *
     * @return <code>true</code> if the Os is windows 7 or later
     *         <code>false</code> otherwise
     */
    static boolean isWindows7OrLater() {
        double version = Double.parseDouble(System.getProperty("os.version"));
        double win7Version = 6.1; //Windows 7 has this version number

        return isWindows() && version >= win7Version;
    }

    /**
     * Checks that the OS is windows or not.
     *
     * @return <code>true</code> if the OS is windows
     *         <code>false</code> otherwise
     */
    static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    /**
     * Returns the hwnd value of the window that has
     * the given index
     *
     * @param index the index of the window in the window-hierarchy
     * @return the hwnd value
     */
    static long getHWNDValueOf(int index) {
        return com.sun.glass.ui.Window.getWindows().get(index).getNativeWindow();
    }
}
