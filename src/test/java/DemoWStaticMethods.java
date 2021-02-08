import com.nativejavafx.taskbar.TaskbarProgressbar;
import com.nativejavafx.taskbar.TaskbarProgressbarFactory;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DemoWStaticMethods extends Application {
    private Stage stage;
    private final ObjectProperty<TaskbarProgressbar.Type> actualSelectedType =
            new SimpleObjectProperty<TaskbarProgressbar.Type>(TaskbarProgressbar.Type.NORMAL) {
                @Override
                protected void invalidated() {
                    final TaskbarProgressbar.Type type = get();
                    if (type != null)
                        TaskbarProgressbar.setProgressType(stage, type);
                }
            };

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        primaryStage.setTitle("FXTaskbarProgressbar Demo");
        primaryStage.setScene(new Scene(buildRoot()));
        primaryStage.show();

        if (TaskbarProgressbar.isSupported())
            TaskbarProgressbar.showIndeterminateProgress(stage);
    }

    private VBox buildRoot() {
        Slider slider = buildSlider();
        VBox vBox = new VBox(10, slider, getToggleGroup(), buildBottom(slider));
        vBox.setPadding(new Insets(10));
        return vBox;
    }

    private Slider buildSlider() {
        Slider slider = new Slider(0, 100, 0);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            TaskbarProgressbar.showCustomProgress(stage, (long) slider.getValue(), (long) slider.getMax(), actualSelectedType.get());
        });

        return slider;
    }

    private VBox getToggleGroup() {
        RadioButton normal = buildProgressTypeRadio(TaskbarProgressbar.Type.NORMAL);
        RadioButton error = buildProgressTypeRadio(TaskbarProgressbar.Type.ERROR);
        RadioButton paused = buildProgressTypeRadio(TaskbarProgressbar.Type.PAUSED);

        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(paused, error, normal);

        normal.setSelected(true);

        return new VBox(paused, normal, error);
    }

    private VBox buildBottom(Slider slider) {
        Button stopperBtn = new Button("Stop");
        stopperBtn.setOnAction((event) -> {
            slider.setValue(0);
            TaskbarProgressbar.stopProgress(stage);
        });

        Button indeterminateBtn = new Button("Indeterminate");
        indeterminateBtn.setOnAction(event -> {
            slider.setValue(0);
            TaskbarProgressbar.showIndeterminateProgress(stage);
        });

        return new VBox(10, stopperBtn, indeterminateBtn);
    }

    private RadioButton buildProgressTypeRadio(TaskbarProgressbar.Type type) {
        RadioButton btn = new RadioButton(type.name());
        btn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) actualSelectedType.set(type);
        });
        return btn;
    }
}
