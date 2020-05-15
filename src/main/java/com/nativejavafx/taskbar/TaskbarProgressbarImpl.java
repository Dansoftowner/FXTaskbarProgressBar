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

import com.nativejavafx.taskbar.strategy.HWNDStrategy;
import javafx.stage.Stage;
import org.bridj.Pointer;
import org.bridj.cpp.com.COMRuntime;
import org.bridj.cpp.com.shell.ITaskbarList3;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A TaskbarProgressbarImpl is a concrete TaskbarProgressbar
 * implementation.
 *
 * <p>
 * Communicates with the OS through bridj natively.
 */
class TaskbarProgressbarImpl extends TaskbarProgressbar {

    private Stage stage;

    private final HWNDStrategy hwndStrategy;

    private ExecutorService executorService;
    private ITaskbarList3 list;
    private Pointer<?> hwnd;

    TaskbarProgressbarImpl(HWNDStrategy hwndStrategy) {
        this.hwndStrategy = Objects.requireNonNull(hwndStrategy, "The HWNDStrategy mustn't be null");

        //creating the executor service that will execute the
        //native operations on a background thread
        executorService = Executors.newSingleThreadExecutor(r -> {
            Thread backGroundThread = new Thread(r);
            backGroundThread.setDaemon(true);

            return backGroundThread;
        });

        executorService.execute(() -> {
            try {
                list = COMRuntime.newInstance(ITaskbarList3.class);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    TaskbarProgressbarImpl(Stage stage, HWNDStrategy hwndStrategy) {
        this(hwndStrategy);
        this.stage = stage;
    }

    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public void stopProgress() {
        if (this.stage == null) return;

        long hwndVal = hwndStrategy.getHWND(this.stage);
        hwnd = Pointer.pointerToAddress(hwndVal);

        executorService.execute(() -> list.SetProgressState((Pointer) hwnd, Type.NO_PROGRESS.getBridjPair()));
    }

    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public void showIndeterminateProgress() {
        if (this.stage == null) return;

        long hwndVal = hwndStrategy.getHWND(this.stage);
        hwnd = Pointer.pointerToAddress(hwndVal);

        executorService.execute(() -> list.SetProgressState((Pointer) hwnd, Type.INDETERMINATE.getBridjPair()));
    }

    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public void showCustomProgress(long startValue, long endValue, Type type) {
        if (this.stage == null) return;

        long hwndVal = hwndStrategy.getHWND(this.stage);
        hwnd = Pointer.pointerToAddress(hwndVal);

        executorService.execute(() -> {
            list.SetProgressValue((Pointer) hwnd, startValue, endValue);
            list.SetProgressState((Pointer) hwnd, type.getBridjPair());
        });
    }

    @Override
    public void closeOperations() {
        executorService.submit(() -> list.Release());
        executorService.shutdown();
        executorService = null;
    }

    synchronized void setStage(Stage stage) {
        this.stage = stage;
    }
}
