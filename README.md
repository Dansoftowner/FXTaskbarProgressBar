# ![JavaFXTaskbarProgressbar](images/Logo.png)
<p align="center"><b>A clean and easy way to implement this amazing native Windows taskbar-progressbar functionality in javaFX</b></p>

## Background
Since Windows 7 there is a taskbar-progressbar feature in Windows systems
that basically means that you can see progress on the program's taskbar icon.
A good example for this when you copy something using the file explorer:<br>
![Taskbar progressbar in windows 7](images/areo-progressbar.jpg) <br>
This is very useful because you don't have to open the window to see the progress!
The problem is that javaFX doesn't provide this functionality by default... however you 
can easily implement it with this library!

## Compatibility
This library has support for java 8 and java 11 too.<br>
<b>The 'v11.x' versions are for java11 users and the 'v8.x' versions are for java 8 users.</b>

### Using with java 11
If you use java 11 you have to pass this VM argument: `--add-exports javafx.graphics/com.sun.glass.ui=nativejavafx.taskbar`.
Also, you have to mention the `nativejavafx.taskbar` module in your `module-info.java` file:
```java
module YourModule {
    ...
    requires nativejavafx.taskbar;
}
```

## How to include it to your project

### Maven example:

Add JitPack.io to your repositories :
```xml
<repositories>
   <repository>
     <id>jitpack.io</id>
     <url>https://jitpack.io</url>
   </repository>
</repositories>
```
Add the dependency:
```xml
<dependency>
	<groupId>com.github.Dansoftowner</groupId>
	<artifactId>FXTaskbarProgressBar</artifactId>
	<version>v11.3</version>
</dependency>
```

### Gradle example

Add the repository:
```groovy
repositories {
    //...
    maven { url 'https://jitpack.io' }
}
```

Add the dependency:
```groovy
dependencies {
    //...
    implementation 'com.github.Dansoftowner:FXTaskbarProgressBar:v11.3'
}
```

## How to use it Tutorial

### 0. Types of progressbar
Before we jump in, we have to know the 4 types of a taskbar-progressbar:<br>
* `NORMAL` - a progressbar with normal green color
* `PAUSED` - a progressbar with a yellow color
* `ERROR`  - a progressbar with a red color 
* `INDETERMINATE` - a progressbar that doesn't show any fix progress
* `NO_PROGRESS` - a progressbar that doesn't show anything 
<br><br>
All these types are represented by the enum called `com.nativejavafx.taskbar.TaskbarProgressbar.Type`.

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
TaskbarProgressbar.showCustomProgress(primaryStage, 0.5, TaskbarProgressbar.Type.NORMAL);
```

Well, the code above looks okay, but it's not safe. This functionality isn't supported by every OS. 
For example on a Linux system it will definitely throw an `UnsupportedSystemException` because it's only available on <b>Windows 7+</b>
systems.<br> 
<b>If you use static methods to create taskbar-progressbars you always have to check that the current OS 
supports this functionality!</b> 

So let's correct the code:
```java
if (TaskbarProgressbar.isSupported()) {
    TaskbarProgressbar.showCustomProgress(primaryStage, 0.5, TaskbarProgressbar.Type.NORMAL);
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
Firstly (after you imported the necessary class) create a `TaskbarProgressbar` instance with the help of
`TaskbarProgressbarFactory`:
```java
TaskbarProgressbar progressbar = TaskbarProgressbarFactory.getTaskbarProgressbar(primaryStage);
```
Before any operation you have to show the Stage:
```java
primaryStage.show();
```
After that just use the created instance for the operations:
```
progressbar.showCustomProgress(0.5, TaskbarProgressbar.Type.NORMAL);
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
```java
progressbar.showFullErrorProgress();
//equivalent to progressbar.showCustomProgress(100, 100, TaskbarProgressbar.Type.ERROR) 
```
<b>Result:</b><br>
![Full errror taskbar progress](images/full-error-progress.jpg)

<i>Also:</i>
```java
progressbar.showFullNormalProgress();
```

```java
progressbar.showFullPausedProgress();
```

## More screenshots
Some more screenshots about what can you do with this library
* A paused progress example:<br>
Code: `progressbar.showCustomProgress(0.7, TaskbarProgressbar.Type.PAUSED);`<br>
![Paused progress](images/paused-progress.jpg)
* An error progress example:<br>
Code: `progressbar.showCustomProgress(0.4, TaskbarProgressbar.Type.ERROR);`<br>
![Paused progress](images/error-progress.jpg)

## Full demo
A full demo-example class is available [here](src/test/java/Demo.java). 

## Projects using `FXTaskbarProgressBar`
If this library is used by your project, let me know in the `Discussions` and I will mention that in this section.

## Source code
This project has two important branches:
* "master" - for java 8 builds
* "11" - for java 11 builds

## Used libraries
 * [bridj](https://github.com/nativelibs4java/BridJ) - blazing fast Java / C / C++ interop
 * [SLF4J](http://www.slf4j.org/) - Simple Logging Facade for Java
 * [Jetbrains Annotations](https://github.com/JetBrains/java-annotations) - Annotations for JVM-based languages

## Support
If you like this library give it a star!