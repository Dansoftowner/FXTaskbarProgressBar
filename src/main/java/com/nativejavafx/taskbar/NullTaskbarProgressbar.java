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

import org.jetbrains.annotations.NotNull;

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
    public void showCustomProgress(long done, long max, @NotNull Type type) {
    }

    @Override
    public void setProgressType(@NotNull Type type) {
    }

    @Override
    public void closeOperations() {
    }
}
