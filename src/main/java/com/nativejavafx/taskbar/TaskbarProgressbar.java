package com.nativejavafx.taskbar;

import javafx.stage.Stage;
import org.bridj.Pointer;
import org.bridj.cpp.com.shell.ITaskbarList3;

import static com.nativejavafx.taskbar.Utils.*;

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
    public enum TaskbarProgressbarType {
        ERROR(ITaskbarList3.TbpFlag.TBPF_ERROR),
        INDETERMINATE(ITaskbarList3.TbpFlag.TBPF_INDETERMINATE),
        NO_PROGRESS(ITaskbarList3.TbpFlag.TBPF_NOPROGRESS),
        NORMAL(ITaskbarList3.TbpFlag.TBPF_NORMAL),
        PAUSED (ITaskbarList3.TbpFlag.TBPF_PAUSED);

        private final ITaskbarList3.TbpFlag bridjPair;

        TaskbarProgressbarType(ITaskbarList3.TbpFlag bridjPair) {
            this.bridjPair = bridjPair;
        }

        public ITaskbarList3.TbpFlag getBridjPair() {
            return this.bridjPair;
        }
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
     * @param max the max "100%" value
     * @param type the type of the progress; must't be null
     * @throws NullPointerException if the type is null
     */
    public abstract void showCustomProgress(long done, long max, TaskbarProgressbarType type);

    /**
     * Shows a 100% error progress
     */
    public void showFullErrorProgress() {
        this.showCustomProgress(100, 100, TaskbarProgressbarType.ERROR);
    }


    public abstract void closeOperations();

    /**
     * Creates a {@link TaskbarProgressbar} object.
     *
     * <p>
     * At first it checks that the taskbar progressbar functionality
     * is supported on the current system: if it is then it will
     * return a fully featured TaskbarProgressbar; otherwise
     * it will return a taskbar-progressbar object that actually
     * doesn't do anything.
     *
     * @param stage the stage to get the progressbar on the taskbar to
     * @return the taskbar-progressbar object
     */
    public static TaskbarProgressbar createInstance(Stage stage) {
        if (isSupported()) return new TaskbarProgressbarImpl(stage);
        else return new NullTaskbarProgressbar();
    }

    /**
     * Stops the taskbar-progress on the given stage.
     *
     * <p><br>
     * Actually calls the {@link TaskbarProgressbar#stopProgress(int)}
     * method with the index of the stage.
     *
     * @param stage the javaFX stage to stop the progress on; mustn't be null
     * @throws NullPointerException if the stage is null
     * @see TaskbarProgressbar#stopProgress(int) 
     */
    public static void stopProgress(Stage stage) {
        stopProgress(getIndexOfStage(stage));
    }

    /**
     * Shows an indeterminate taskbar-progress on the given stage.
     *
     * <p><br>
     * Actually calls the {@link TaskbarProgressbar#showIndeterminateProgress(int)}
     * method with the index of the stage.
     *
     * @param stage the javaFX stage to show the progress on; mustn't be null
     * @throws NullPointerException if the stage is null
     * @see TaskbarProgressbar#showIndeterminateProgress(int)
     */
    public static void showIndeterminateProgress(Stage stage) {
        showIndeterminateProgress(getIndexOfStage(stage));
    }

    /**
     * Shows a custom progress on the taskbar.
     *
     * <p><br>
     * Actually calls the {@link TaskbarProgressbar#showCustomProgress(int, long, long, TaskbarProgressbarType)}
     * method with the index of the stage.
     *
     * @param stage the javaFX stage to show the progress on; mustn't be null
     * @param done specifies the actual "done" value of the max value
     * @param max specifies the max, 100% value of the loading
     * @param type the type of the progress; mustn't be null
     * @throws NullPointerException if the stage or the type is null
     * @see TaskbarProgressbar#showCustomProgress(int, long, long, TaskbarProgressbarType)
     */
    public static void showCustomProgress(Stage stage, long done, long max, TaskbarProgressbarType type) {
        showCustomProgress(getIndexOfStage(stage), done, max, type);
    }


    @SuppressWarnings({"deprecation", "unchecked"})
    public static void stopProgress(int windowIndex) {
        TaskbarProgressbarImpl progressbar = new TaskbarProgressbarImpl();

        long hwndVal = getHWNDValueOf(windowIndex);
        progressbar.setHwnd(Pointer.pointerToAddress(hwndVal));

        progressbar.getService().execute(() -> progressbar.getList().SetProgressState((Pointer) progressbar.getHwnd(), TaskbarProgressbarType.NO_PROGRESS.getBridjPair()));
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static void showIndeterminateProgress(int windowIndex) {
        TaskbarProgressbarImpl progressbar = new TaskbarProgressbarImpl();

        long hwndVal = getHWNDValueOf(windowIndex);
        progressbar.setHwnd(Pointer.pointerToAddress(hwndVal));

        progressbar.getService().execute(() -> progressbar.getList().SetProgressState((Pointer) progressbar.getHwnd(), TaskbarProgressbarType.INDETERMINATE.getBridjPair()));
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static void showCustomProgress(int windowIndex, long done, long endValue, TaskbarProgressbarType type) {
        TaskbarProgressbarImpl progressbar = new TaskbarProgressbarImpl();

        long hwndVal = getHWNDValueOf(windowIndex);
        progressbar.setHwnd(Pointer.pointerToAddress(hwndVal));

        progressbar.getService().execute(() -> {
            progressbar.getList().SetProgressValue((Pointer) progressbar.getHwnd(), done, endValue);
            progressbar.getList().SetProgressState((Pointer) progressbar.getHwnd(), type.getBridjPair());
        });
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static void showFullErrorProgress(int windowIndex) {
        TaskbarProgressbarImpl progressbar = new TaskbarProgressbarImpl();

        long hwndVal = getHWNDValueOf(windowIndex);
        progressbar.setHwnd(Pointer.pointerToAddress(hwndVal));

        progressbar.getService().execute(() -> {
            progressbar.getList().SetProgressValue((Pointer) progressbar.getHwnd(), 100, 100);
            progressbar.getList().SetProgressState((Pointer) progressbar.getHwnd(), TaskbarProgressbarType.ERROR.getBridjPair());
        });
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
     *         <code>false</code> otherwise.
     */
    public static boolean isSupported() {
        return isWindows7OrLater();
    }

}
