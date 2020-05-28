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

import com.nativejavafx.taskbar.strategy.GlassApiHWNDStrategy;
import com.nativejavafx.taskbar.strategy.DefaultStageIndexer;
import javafx.stage.Stage;

import static com.nativejavafx.taskbar.TaskbarProgressbar.isNotSupported;
import static com.nativejavafx.taskbar.TaskbarProgressbar.isSupported;

/**
 * The TaskbarProgressbarFactory class is responsible for
 * creating the right {@link TaskbarProgressbar} object.
 */
public class TaskbarProgressbarFactory {

    static TaskbarProgressbar getTaskbarProgressbarImpl(Stage stage) {
        return new TaskbarProgressbarImpl(stage, new GlassApiHWNDStrategy(new DefaultStageIndexer()));
    }

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
    public static TaskbarProgressbar getTaskbarProgressbar(Stage stage) {
        if (TaskbarProgressbar.isNotSupported())
            return new NullTaskbarProgressbar();
        return getTaskbarProgressbarImpl(stage);
    }

}
