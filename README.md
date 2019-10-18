# JavaFXTaskbarProgressBar
An open-source library to javaFX for add taskbar progressbar to your javafx stages


How to use it?

//create a stage
Stage stage = new Stage();

//create the taskbar progressbar for it
TaskbarProgressbar taskProgressbar = new TaskbarProgressbar(stage);

//show the stage
stage.show();

//and now, you can do everything 
//if you want to show indeterminate process in your taskbar, just do this:
taskProgressbar.showIndeterminateProgress();

//if you want to stop the progressbar, just do this:
taskProgressbar.stopProgress();
