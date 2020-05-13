package com.nativejavafx.taskbar;

/**
 * A NullTaskbarProgressbar is a {@link TaskbarProgressbar} which
 * actually doesn't do everything
 */
class NullTaskbarProgressbar extends TaskbarProgressbar {
    @Override
    public void stopProgress() {
    }

    @Override
    public void showIndeterminateProgress() {
    }

    @Override
    public void showCustomProgress(long done, long max, Type type) {
    }

    @Override
    public void closeOperations() {
    }
}
