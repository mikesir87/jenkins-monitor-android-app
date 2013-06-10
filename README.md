# Jenkins Monitor Android App

This is a demo of a full Android app that builds off of what the BuildWatch app provided.  

## Setting up the project

### Pre-requisites

1. Install the Android SDK. You will need API Version 17.
2. Download the latest Android support package (r13) ([how-to here](http://developer.android.com/tools/extras/support-library.html#Downloading)).

3. Install that support package into your Maven repo (it doesn't seem to be in any public repos quite yet).
    
    <code>mvn install:install-file -Dfile=android-support-v13.jar -DgroupId=com.google.android -DartifactId=support-v4 -Dversion=r13 -Dpackaging=jar</code>

4. Add the GCM repo to your Maven's settings so you can get the GCM library. ([info here](https://github.com/slorber/gcm-server-repository))

### Eclipse setup

1. Clone this project.

2. Import the project into your workspace using **New** -> **Project** -> **Android** -> **Android Project from Existing Code**.

3. Eclipse doesn't work very well with the **apklib** package format yet (patches to the plugin are coming soon). In order to have all the resources be happy, you will need to add the ActionBarSherlock library to your workspace.
  1. Clone the project on [GitHub](https://github.com/JakeWharton/ActionBarSherlock/tree/master/actionbarsherlock).
  2. Import the existing project using **New** -> **Project** -> **Android** -> **Android Project from Existing Code**.
  3. Select the project from where you cloned it.
  4. Right-click the Android project (jenkins-notifier) and hit **Properties**.
  5. In the **Android** section, add the actionbarsherlock project as a library.

Everything should be good to go then.  Eclipse probably still won't be able to install the app until the plugin gets fixed.

### Installing the app

1. Run <code>mvn clean install android:deploy</code>

