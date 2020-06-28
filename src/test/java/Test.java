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

import com.nativejavafx.taskbar.strategy.DefaultStageIndexer;
import com.nativejavafx.taskbar.strategy.GlassApiHWNDStrategy;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.bridj.Pointer;
import org.bridj.cpp.com.COMRuntime;
import org.bridj.cpp.com.shell.ITaskbarList3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test extends Application {
    private ITaskbarList3 list;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(new StackPane(new Button("OK")));

        primaryStage.setScene(scene);
        primaryStage.show();

        ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
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

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });



    }
}
