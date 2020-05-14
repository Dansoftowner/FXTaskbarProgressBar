# ![JavaFXTaskbarProgressbar](images/Logo.png)
## This library allows you to add native taskbar-progressbar functionality to your JavaFX Stages.
<b align="center">A clean and easy way to implement this native Windows-taskbar functionality in javaFX</b><br>
<i>For the native access this project uses [bridj](https://github.com/nativelibs4java/BridJ) </i><br>

## Compatibility
This library has support for java 8 and java 11 too.

## Source code
This project has two important branches:
* "master" - for java 8 builds
* "11" - for java 11 builds

## Background
Since Windows 7 there is a taskbar-progressbar feature in Windows systems
that basically means that you can see a progress on the program's icon.
A good example for this when you copy something using the file explorer:<br>
![Taskbar progressbar in windows 7](images/areo-progressbar.jpg) <br>
This is very useful because you don't have to open the window to know the progress!
The problem is that javaFX doesn't provide this functionality by default... but now you 
can easily implement this with the power of this library!  
It allows you to do this in pure java!

## How to include it to your project
You can download the right jar file from the [releases](https://github.com/Dansoftowner/FXTaskbarProgressBar/releases). 
<br>
Every release has two jar files: one is a fat jar (it's name ends with '*-full-x.x.jar') that contains the bridj binaries 
as well <i>(so if you use this you don't have to include bridj separately for your project)</i>;
and another jar that doesn't contain the external bridj binaries <i>(in this case you have
to download the bridj binaries separately)</i>.
<br><b>The 'v11.x' versions are for java11 users and the 'v8.x' versions are for java 8 users.</b>

## How to use it Tutorial

### Types of progressbar
Before we jump in, we have to know the 4 types of a taskbar-progressbar:<br>
* `NORMAL` - a progressbar with normal green color
* `PAUSED` - a progressbar with a yellow color
* `ERROR`  - a progressbar with a red color 
* `INDETERMINATE` - a progressbar that doesn't show any fix progress
* `NO_PROGRESS` - a progressbar that doesn't show anything 
<br><br>
All these types are represented by an enum called `com.nativejavafx.taskbar.TaskbarProgressbar.Type`.

Now let's see how can we actually use this through code.<br>
There are multiple ways to create taskbar progressbars with this library:

#### 1.Through static methods:
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

Well, the code above looks okay, but it's not safe. This functionality isn't supported by every OS. 
For example on a Linux Ubuntu system it will definitely throw a `RuntimeException` because it's only available on <b>Windows 7+</b>
systems.<br> 
<b>If you use static methods to create taskbar-progressbars you always have to check that the current OS 
supports this functionality!</b> 

So let's correct the code:
```java
if (TaskbarProgressbar.isSupported()) {
    TaskbarProgressbar.showCustomProgress(primaryStage, 50, 100, TaskbarProgressbar.Type.NORMAL);
}
```
...now it is safe!

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
#### 2. Through instantiation *(the recommended way)*
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

#### Bonus features

A simple method for showing a fully loaded error progressbar
```
progressbar.showFullErrorProgress();
//equivalent to progressbar.showCustomProgress(100, 100, TaskbarProgressbar.Type.ERROR) 
```
<b>Result:</b><br>
![Full errror taskbar progress](images/full-error-progress.jpg)

## More screenshots
Some more screenshots about what can you do with this library
* A paused progress example:<br>
Code: `progressbar.showCustomProgress(70, 100, TaskbarProgressbar.Type.PAUSED);`<br>
![Paused progress](images/paused-progress.jpg)
* An error progress example:<br>
Code: `progressbar.showCustomProgress(40, 100, TaskbarProgressbar.Type.ERROR);`<br>
![Paused progress](images/error-progress.jpg)

## Full demo
A full demo-example class is available [here](src/test/java/Demo.java). 

## Support
If you like this library please give me a star! It's very important for me 
because it keeps me motivated to work on this library. Thank you!