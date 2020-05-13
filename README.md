# ![JavaFXTaskbarProgressbar](images/Logo.png)
## This library allows you to add native taskbar-progressbar functionality to your JavaFX stages.
The library uses another library called [bridj](https://github.com/nativelibs4java/BridJ) that 
can give access to native operations in java. With the Bridj library you can easily add taskbar progressbars 
to the old swing JFrames but for javafx stages it's too complicated to achieve.<br> 
<b>This library is for you who want to easily use this amazing native functionality with JavaFX!</b>

## Compatibility
This library only works with java 8 but java 11 support is coming soon!

## Background: what are taskbar progressbars?
Since Windows 7 there is a taskbar-progressbar feature in Windows systems.
A good example for this when you copy something using the file explorer:<br>
![Taskbar progressbar in windows 7](images/areo-progressbar.jpg) <br>
This library allows you to do this in java (with javaFX)!

## How to include it to your project
You can download the jar file from the releases.
Every release has two jar files: one is a fat jar that contains the bridj binaries 
as well (so if you use this you don't have to include bridj separately for your project);
and another jar that doesn't contain the external bridj binaries (in this case you have
to download it separately).

## How to use it in practice

### Types of progressbar
Before we jump in we have to know the 4 types of a taskbar-progressbar:<br>
* `NORMAL` - a progressbar with normal green color
* `PAUSED` - a progressbar with a yellow color
* `ERROR`  - a progressbar with a red color 
* `INDETERMINATE` - a progressbar that doesn't show any fix progress
* `NO_PROGRESS` - a progressbar that doesn't show anything 
<br><br>
All these types are represented by an enum called `com.nativejavafx.taskbar.TaskbarProgressbar.Type`.

Now let's see how can we actually use this through code.<br>
There are multiple ways to create taskbar progressbars with this library:

#### Static methods
Firstly you have to import the necessary class:
```java
import com.nativejavafx.taskbar.TaskbarProgressbar; 
```
...and you have to show the javafx Stage before any operation:
```
primaryStage.show();
``` 
Then call the static method:
```java
TaskbarProgressbar.showCustomProgress(primaryStage, 50, 100, TaskbarProgressbar.Type.NORMAL);
```

It looks okay but it's not safe to do that. This functionality isn't supported by every OS.
<b>If you use this way to create taskbar progressbars you always have to check that the current OS 
supports this functionality!</b> 

So let's correct the code:
```java
if (TaskbarProgressbar.isSupported()) {
    TaskbarProgressbar.showCustomProgress(primaryStage, 50, 100, TaskbarProgressbar.Type.NORMAL);
}
```

<b>Result:</b><br>
![Normal Taskbar progressbar](images/normal-progress.jpg)

You have to do a similar thing if you want to show an indeterminate progress:
```java
if (TaskbarProgressbar.isSupported()) {
    TaskbarProgressbar.showIndeterminateProgress(primaryStage);
}
```
<b>Result:</b><br>
![Indeterminate Taskbar progressbar](images/indeterminate.gif)

To stop the progress:
```java
TaskbarProgressbar.stopProgress(primaryStage);
```
#### Through instantiation *(the preferred way)*
Firstly (after you imported the necessary class) create a `TaskbarProgressbar` instance:
```java
TaskbarProgressbar progressbar = TaskbarProgressbar.createInstance(primaryStage);
```
Before any operation you have to show the Stage:
```java
primaryStage.show();
```
After that just use the created instance for the operations:
```
progressbar.showCustomProgress(50, 100, TaskbarProgressbar.Type.NORMAL);
```
<b><i>Note: in this case to check that the OS supports this functionality is unnecessary
because the object checks it automatically!</i></b>

The result is the same:<br>
![Normal Taskbar progressbar](images/normal-progress.jpg)

If you want an indeterminate process:
```java
progressbar.showIndeterminateProgress();
```
To stop the progress:
```java
progressbar.stopProgress();
```

## More screenshots
Some more screenshots about what can you do with this library
* A paused progress example:<br>
Code: `progressbar.showCustomProgress(70, 100, TaskbarProgressbar.Type.PAUSED);`<br>
![Paused progress](images/paused-progress.jpg)
* An error progress example:<br>
Code: `progressbar.showCustomProgress(40, 100, TaskbarProgressbar.Type.ERROR);`<br>
![Paused progress](images/error-progress.jpg)

## Support
If you like this library please give me a star! It's very important for me 
because it keeps me motivated to work on this library. Thank you!