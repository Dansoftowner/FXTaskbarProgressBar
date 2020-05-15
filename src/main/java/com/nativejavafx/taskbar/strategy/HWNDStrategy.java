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

package com.nativejavafx.taskbar.strategy;

import javafx.stage.Stage;

/**
 * A HWNDStrategy used for getting the HWND
 * ("lowest-level" native window handle on Windows systems)
 */
public interface HWNDStrategy {

    /**
     * Returns the native handle (on Windows: HWND)
     * of the javaFX Stage
     *
     * @param stage the stage to get the HWND of; mustn't be null
     * @return the HWND value
     * @throws NullPointerException if the stage is null
     */
    long getHWND(Stage stage);
}
