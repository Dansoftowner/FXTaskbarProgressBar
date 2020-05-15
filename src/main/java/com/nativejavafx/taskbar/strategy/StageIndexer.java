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
 * A StageIndexer is used for get the index of a javaFX Stage
 * in the WindowHierarchy
 */
public interface StageIndexer {

    /**
     * Gets the index of a javaFX Stage in the Window-hierarchy.
     *
     * @param stage the stage to get the index of; mustn't be null
     * @return the index of the Stage
     * @throws NullPointerException if the stage is null
     */
    int indexOf(Stage stage);
}
