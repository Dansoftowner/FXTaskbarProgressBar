/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.nativejavafx.taskbar;

import com.nativejavafx.taskbar.util.OS;
import javafx.stage.Stage;
import org.bridj.cpp.com.shell.ITaskbarList3;

/**
 * A TaskbarProgressbar object can add native (Windows 7+) taskbar
 * progressbar functionality to {@link Stage} objects.
 *
 * <p>
 * Can be used by instantiation and by static methods as well.
 */
public abstract class TaskbarProgressbar {

    /**
     * Defines the types of the taskbar progressbars
     * that can be used on a Windows 7+ system.
     */
    public enum Type {
        ERROR(ITaskbarList3.TbpFlag.TBPF_ERROR),
        INDETERMINATE(ITaskbarList3.TbpFlag.TBPF_INDETERMINATE),
        NO_PROGRESS(ITaskbarList3.TbpFlag.TBPF_NOPROGRESS),
        NORMAL(ITaskbarList3.TbpFlag.TBPF_NORMAL),
        PAUSED(ITaskbarList3.TbpFlag.TBPF_PAUSED);

        private final ITaskbarList3.TbpFlag bridjPair;

        Type(ITaskbarList3.TbpFlag bridjPair) {
            this.bridjPair = bridjPair;
        }

        public ITaskbarList3.TbpFlag getBridjPair() {
            return this.bridjPair;
        }
    }

    /**
     * A {@link TaskbarProgressbar} object-cache that used
     * for static operations.
     */
    private static TaskbarProgressbar cache;


    protected TaskbarProgressbar() {
    }

    /**
     * Stops the progress on the taskbar
     */
    public abstract void stopProgress();

    /**
     * Shows an indeterminate progress on the taskbar
     */
    public abstract void showIndeterminateProgress();

    /**
     * Shows a custom progress on the taskbar
     *
     * @param done the done value of the max value
     * @param max  the max "100%" value
     * @param type the type of the progress; must't be null
     * @throws NullPointerException if the type is null
     */
    public abstract void showCustomProgress(long done, long max, Type type);

    /**
     * Shows a 100% error progress
     */
    public void showFullErrorProgress() {
        this.showCustomProgress(100, 100, Type.ERROR);
    }

    /**
     * Shows a 100% paused progress
     */
    public void showFullPausedProgress() {
        this.showCustomProgress(100, 100, Type.PAUSED);
    }

    /**
     * Shows a 100% normal progress
     */
    public void showFullNormalProgress() {
        this.showCustomProgress(100, 100, Type.NORMAL);
    }

    /**
     * Closes all background-thread tasks
     */
    public abstract void closeOperations();

    /**
     * @deprecated use {@link TaskbarProgressbarFactory#getTaskbarProgressbar(Stage)} instead.
     */
    @Deprecated
    public static TaskbarProgressbar createInstance(Stage stage) {
        return TaskbarProgressbarFactory.getTaskbarProgressbar(stage);
    }

    private synchronized static void createCache() {
        if (cache == null) cache = TaskbarProgressbarFactory.getTaskbarProgressbarImpl(null);
    }

    /**
     * Stops the taskbar-progress on the given stage.
     *
     * @param stage the javaFX stage to stop the progress on; mustn't be null
     * @throws NullPointerException if the stage is null
     */
    public static void stopProgress(Stage stage) {
        createCache();

        TaskbarProgressbarImpl impl = (TaskbarProgressbarImpl) cache;
        impl.setStage(stage);
        cache.stopProgress();
    }

    /**
     * Shows an indeterminate taskbar-progress on the given stage.
     *
     * @param stage the javaFX stage to show the progress on; mustn't be null
     * @throws NullPointerException if the stage is null
     */
    public static void showIndeterminateProgress(Stage stage) {
        createCache();

        TaskbarProgressbarImpl impl = (TaskbarProgressbarImpl) cache;
        impl.setStage(stage);
        cache.showIndeterminateProgress();
    }

    /**
     * Shows a custom progress on the taskbar.
     *
     * @param stage the javaFX stage to show the progress on; mustn't be null
     * @param done  specifies the actual "done" value of the max value
     * @param max   specifies the max, 100% value of the loading
     * @param type  the type of the progress; mustn't be null
     * @throws NullPointerException if the stage or the type is null
     */
    public static void showCustomProgress(Stage stage, long done, long max, Type type) {
        createCache();

        TaskbarProgressbarImpl impl = (TaskbarProgressbarImpl) cache;
        impl.setStage(stage);
        impl.showCustomProgress(done, max, type);
    }

    /**
     * Shows a full error progress on the taskbar.
     *
     * @param stage the javaFX stage to show the progress on; mustn't be null
     */
    public static void showFullErrorProgress(Stage stage) {
        showCustomProgress(stage, 100, 100, Type.ERROR);
    }

    /**
     * Shows a full paused progress on the taskbar.
     *
     * @param stage the javaFX stage to show the progress on; mustn't be null
     */
    public static void showFullPausedProgress(Stage stage) {
        showCustomProgress(stage, 100, 100, Type.PAUSED);
    }

    /**
     * Shows a full normal progress on the taskbar.
     *
     * @param stage the javaFX stage to show the progress on; mustn't be null
     */
    public static void showFullNormalProgress(Stage stage) {
        showCustomProgress(stage, 100, 100, Type.NORMAL);
    }

    /**
     * Checks that the current system is supported or not.
     *
     * <p><br/>
     * Actually checks that the OS is Windows and the Windows version
     * is greater than Windows 7:
     * <i>the taskbar progressbar functionality is working since Windows 7</i>
     *
     * @return <code>true</code> if the OS is supported for taskbar progressbar functionality
     * <code>false</code> otherwise.
     */
    public static boolean isSupported() {
        return OS.isWindows7OrLater();
    }


    public static boolean isNotSupported() {
        return !isSupported();
    }
}
