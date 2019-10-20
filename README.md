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
Now you can do everything that the taskbar progressbar can do, for example you can show a progress:
```
taskProgressbar.showOtherProgress(50, 100, TaskbarProgressbar.TaskbarProgressbarType.NORMAL);
```
It will creates this view at the taskbar:
![](https://i.stack.imgur.com/IG7v5.png).

There are 5 types of the taskbar progressbar: 

`TaskbarProgressbar.TaskbarProgressbarType.NORMAL` - It is the normal progressbar with the green color
`TaskbarProgressbar.TaskbarProgressbarType.ERROR` - It is the error progressbar with the red color.
`TaskbarProgressbar.TaskbarProgressbarType.PAUSED` - It is the paused progressbar with the yellow color.

`TaskbarProgressbar.TaskbarProgressbarType.NOPROGRESS` - It is the empty progressbar
`TaskbarProgressbar.TaskbarProgressbarType.INDETERMINATE` - It is the indeterminate progressbar. It doesn't show any fix progress.

If you want to show an indeterminate progress:
```
taskbarProgressbar.showIndeterminateProgress();
```
If you want to stop the progress:
```
taskbarProgressbar.stopProgress();
```
