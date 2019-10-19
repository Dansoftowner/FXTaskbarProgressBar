# JavaFXTaskbarProgressBar
## This library allows you to add taskbar progressbars to your javafx stages.
The library uses another library called [Bridj](https://github.com/nativelibs4java/BridJ). 
With the Bridj library you can add taskbar progressbars only to the old swing JFrames, 
but not for your javafx stages. But this library will be your solution i hope :).

## How to include it to your project
You can download the jar file from the releases.

## How to use it

Create your stage:
```
Stage stage = new Stage();
```

Then, you have to create the TaskbarProgressbar object:
```
TaskbarProgressbar taskProgressbar = new TaskbarProgressbar(stage);
```
Before you start the taskbar progressbar, you must show the stage
```
stage.show();
```
Now you can everything that the taskbar progressbar can do:
For example you can show a progress:
```
taskProgressbar.showOtherProgress(50, 100, TaskbarProgressbar.TaskbarProgressbarType.NORMAL);
```
It will creates this view at the taskbar:
![](https://i.stack.imgur.com/IG7v5.png)
