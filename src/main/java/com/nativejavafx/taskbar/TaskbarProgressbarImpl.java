package com.nativejavafx.taskbar;

import javafx.stage.Stage;
import org.bridj.Pointer;
import org.bridj.cpp.com.COMRuntime;
import org.bridj.cpp.com.shell.ITaskbarList3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.nativejavafx.taskbar.Utils.getIndexOfStage;

/**
 * A TaskbarProgressbarImpl is a TaskbarProgressbar that actually
 * do the native work through bridj.
 */
class TaskbarProgressbarImpl extends TaskbarProgressbar {
    private final Stage stage;

    private ExecutorService es;
    private ITaskbarList3 list;
    private Pointer<?> hwnd;

    TaskbarProgressbarImpl() {
        stage = null;

        es = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);

            return t;
        });

        es.execute(() -> {
            try {
                list = COMRuntime.newInstance(ITaskbarList3.class);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public TaskbarProgressbarImpl(Stage stage) {
        this.stage = stage;

        es = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);

            t.setDaemon(true);

            return t;
        });

        es.execute(() -> {
            try {
                list = COMRuntime.newInstance(ITaskbarList3.class);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public void stopProgress() {
        long hwndVal = com.sun.glass.ui.Window.getWindows().get(getIndexOfStage(stage)).getNativeWindow();
        hwnd = Pointer.pointerToAddress(hwndVal);

        es.execute(() -> list.SetProgressState((Pointer) hwnd, TaskbarProgressbar.TaskbarProgressbarType.NO_PROGRESS.getBridjPair()));
    }

    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public void showIndeterminateProgress() {
        long hwndVal = com.sun.glass.ui.Window.getWindows().get(getIndexOfStage(stage)).getNativeWindow();
        hwnd = Pointer.pointerToAddress(hwndVal);

        es.execute(() -> list.SetProgressState((Pointer) hwnd, TaskbarProgressbar.TaskbarProgressbarType.INDETERMINATE.getBridjPair()));
    }

    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public void showCustomProgress(long startValue, long endValue, TaskbarProgressbar.TaskbarProgressbarType type) {
        long hwndVal = com.sun.glass.ui.Window.getWindows().get(getIndexOfStage(stage)).getNativeWindow();
        hwnd = Pointer.pointerToAddress(hwndVal);

        es.execute(() -> {
            list.SetProgressValue((Pointer) hwnd, startValue, endValue);
            list.SetProgressState((Pointer) hwnd, type.getBridjPair());
        });
    }

    @Override
    public void closeOperations() {
        es.submit(() -> list.Release());
        es.shutdown();
        es = null;
    }

    ExecutorService getService() {
        return es;
    }

    ITaskbarList3 getList() {
        return list;
    }

    void setHwnd(Pointer<?> hwnd) {
        this.hwnd = hwnd;
    }

    Pointer<?> getHwnd() {
        return hwnd;
    }
}
