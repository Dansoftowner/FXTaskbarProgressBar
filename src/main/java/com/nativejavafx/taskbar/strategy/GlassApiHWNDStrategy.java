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

import java.util.Objects;

/**
 * A GlassApiHWNDStrategy calculates the HWND value of
 * a {@link Stage} through the {@link com.sun.glass.ui.Window#getWindows()}
 * API.
 *
 * <p>
 * This kind of process needs the index of the window in the window-
 * hierarchy to get the HWND value.
 * For the indexing, this strategy-object needs a {@link StageIndexer}
 */
public class GlassApiHWNDStrategy implements HWNDStrategy {

    private final StageIndexer indexer;

    /**
     * Creates a basic {@link GlassApiHWNDStrategy} that
     * needs a {@link StageIndexer}.
     *
     * @param indexer the stage-indexer; mustn't be null
     */
    public GlassApiHWNDStrategy(StageIndexer indexer) {
        this.indexer = Objects.requireNonNull(indexer, "The indexer mustn't be null");
    }

    @Override
    public long getHWND(Stage stage) {
        return com.sun.glass.ui.Window.getWindows()
                .get(indexer.indexOf(stage))
                .getNativeWindow();
    }
}
