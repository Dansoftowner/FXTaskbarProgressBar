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
import org.bridj.cpp.com.shell.ITaskbarList2;
import org.bridj.cpp.com.shell.ITaskbarList3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A TaskbarProgressbarImpl is a concrete TaskbarProgressbar
 * implementation.
 *
 * @author Daniel Gyorffy
 */
class TaskbarProgressbarImpl extends TaskbarProgressbar {

    private static final Logger logger = LoggerFactory.getLogger(TaskbarProgressbarImpl.class);

    private final ExecutorService executor = Executors.newSingleThreadExecutor(DaemonThread::new);
    private final AtomicReference<ITaskbarList3> iTaskbarList3 = new AtomicReference<>();
    private final AtomicReference<Stage> stage = new AtomicReference<>();
    private final HWNDStrategy hwndStrategy;

    TaskbarProgressbarImpl(HWNDStrategy hwndStrategy) {
        this.hwndStrategy = Objects.requireNonNull(hwndStrategy, "The HWNDStrategy mustn't be null");
        this.instantiateITaskbarList();
    }

    TaskbarProgressbarImpl(Stage stage, HWNDStrategy hwndStrategy) {
        this(hwndStrategy);
        this.setStage(stage);
    }

    synchronized void setStage(@NotNull Stage stage) {
        this.stage.set(stage);
    }

    private void instantiateITaskbarList() {
        executor.execute(() -> {
            try {
                iTaskbarList3.set(COMRuntime.newInstance(ITaskbarList3.class));
            } catch (ClassNotFoundException | RuntimeException e) {
                logger.error("Couldn't instantiate ITaskbarList3", e);
            }
        });
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    private Pointer<Integer> getPointer(Stage stage) {
        long windowHandle = hwndStrategy.getHWND(stage);
        return (Pointer<Integer>) Pointer.pointerToAddress(windowHandle);
    }

    private void setProgressState(Stage stage, Type type) {
        if (stage != null) {
            final Pointer<Integer> pointer = getPointer(stage);
            executor.execute(() -> iTaskbarList3.get().SetProgressState(pointer, type.getBridjPair()));
        }
    }

    @Override
    public void stopProgress() {
        if (this.stage.get() != null) {
            setProgressState(stage.get(), Type.NO_PROGRESS);
        }
    }

    @Override
    public void showIndeterminateProgress() {
        if (this.stage.get() != null) {
            setProgressState(stage.get(), Type.INDETERMINATE);
        }
    }

    @Override
    public void showCustomProgress(long startValue, long endValue, @NotNull Type type) {
        if (this.stage.get() != null) {
            final Pointer<Integer> pointer = getPointer(stage.get());
            executor.execute(() -> {
                iTaskbarList3.get().SetProgressValue(pointer, startValue, endValue);
                iTaskbarList3.get().SetProgressState(pointer, type.getBridjPair());
            });
        }
    }

    @Override
    public void setProgressType(@NotNull Type type) {
        setProgressState(stage.get(), type);
    }

    @Override
    public void closeOperations() {
        executor.submit(() -> iTaskbarList3.get().Release());
    }

    private static final class DaemonThread extends Thread {
        DaemonThread(Runnable runnable) {
            super(runnable);
            setDaemon(true);
        }
    }
}
