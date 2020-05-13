import com.nativejavafx.taskbar.TaskbarProgressbar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Demo extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    
    private TaskbarProgressbar.TaskbarProgressbarType actualSelectedType;

    @Override
    public void start(Stage primaryStage) {
        
        TaskbarProgressbar taskProgressbar = TaskbarProgressbar.createInstance(primaryStage);
        
        Button btn = new Button("Stop");
        btn.setOnAction((event) -> {
            taskProgressbar.stopProgress();
        });
        
        primaryStage.setScene(new Scene(new StackPane(btn)));
        primaryStage.show();
        
        taskProgressbar.showIndeterminateProgress();
        
        
        Stage stage = new Stage();
        
        TaskbarProgressbar taskbarProgressbar2 = TaskbarProgressbar.createInstance(stage);
        
        Slider slider = new Slider(0, 100, 0);
        
        RadioButton paused = new RadioButton("Paused");
        RadioButton normal = new RadioButton("Normal");
        RadioButton error = new RadioButton("Error");
        
        paused.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                actualSelectedType = TaskbarProgressbar.TaskbarProgressbarType.PAUSED;
        });
        
        normal.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                actualSelectedType = TaskbarProgressbar.TaskbarProgressbarType.NORMAL;
        });
        
        error.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                actualSelectedType = TaskbarProgressbar.TaskbarProgressbarType.ERROR;
        });
        
        normal.setSelected(true);
        
        ToggleGroup tg = new ToggleGroup();
        tg.getToggles().addAll(paused, error, normal);
        
        VBox vBox = new VBox(slider, paused, normal, error);
        
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            taskbarProgressbar2.showCustomProgress((long)slider.getValue(), (long) slider.getMax(), actualSelectedType);
        });
        
        stage.setScene(new Scene(vBox));
        stage.show();
        
        
    }
}
