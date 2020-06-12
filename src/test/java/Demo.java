import com.nativejavafx.taskbar.TaskbarProgressbar;
import com.nativejavafx.taskbar.TaskbarProgressbarFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bridj.cpp.com.shell.ITaskbarList3;

public class Demo extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    private TaskbarProgressbar taskbarProgressbar;
    private TaskbarProgressbar.Type actualSelectedType =
            TaskbarProgressbar.Type.NORMAL;

    @Override
    public void start(Stage primaryStage) {
        taskbarProgressbar =
                TaskbarProgressbarFactory.getTaskbarProgressbar(primaryStage); // the deprecated way: TaskbarProgressbar.createInstance(primaryStage);

        Slider slider = getSlider();

        Button stopperBtn = new Button("Stop");
        stopperBtn.setOnAction((event) -> {
            slider.setValue(0);
            taskbarProgressbar.stopProgress();
        });

        Button indeterminateBtn = new Button("Indeterminate");
        indeterminateBtn.setOnAction(event -> {
            slider.setValue(0);
            taskbarProgressbar.showIndeterminateProgress();
        });

        VBox bottom = new VBox(10, stopperBtn, indeterminateBtn);
        VBox vBox = new VBox(10, slider, getToggleGroup(), bottom,
                new Label("java version " + System.getProperty("java.version")));

        primaryStage.setTitle("Test FXTaskbarProgressbar");
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();

        taskbarProgressbar.showIndeterminateProgress();
    }

    private Slider getSlider() {
        Slider slider = new Slider(0, 100, 0);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            taskbarProgressbar.showCustomProgress((long) slider.getValue(), (long) slider.getMax(), actualSelectedType);
        });

        return slider;
    }

    private VBox getToggleGroup() {
        RadioButton paused = new RadioButton("Paused");
        RadioButton normal = new RadioButton("Normal");
        RadioButton error = new RadioButton("Error");

        paused.setOnAction(event -> {
            if (paused.isSelected()) this.actualSelectedType = TaskbarProgressbar.Type.PAUSED;
        });

        normal.setOnAction(event -> {
            if (normal.isSelected()) this.actualSelectedType = TaskbarProgressbar.Type.NORMAL;
        });

        error.setOnAction(event -> {
            if (error.isSelected()) this.actualSelectedType = TaskbarProgressbar.Type.ERROR;
        });

        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(paused, error, normal);

        normal.setSelected(true);
        
        ToggleGroup tg = new ToggleGroup();
        tg.getToggles().addAll(paused, error, normal);

        return new VBox(paused, normal, error);
    }
}
