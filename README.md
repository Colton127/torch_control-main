# torch_control

A Flutter plugin for torch/flashlight/lamp control. Offers API for:
* checking if torch is available
* turning the torch on and off
* toggling the torch
* flashing the torch for a given duration

Platforms supported by the plugin:
* Android old API levels 16 to 19 (4.1 to 4.4) via android.hardware.Camera
* Android API levels 21 to 31 (5.+) via android.hardware.camera2
* iOS (currently untested)

## Install

Add  `torch_control: 0.2.0` to your pubspec.yaml file

Set permissions for Android

    <uses-permission android:name="android.permission.CAMERA"/>
    <uses-permission android:name="android.permission.FLASHLIGHT"/>

    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" />

## Usage

To check if a torch is available and ready to use:

    await TorchControl.ready();

To turn on and off the torch:

    TorchControl.turnOn();
    TorchControl.turnOff();
    TorchControl.turn(true);
    TorchControl.turn(false);

To toggle the torch:

    TorchControl.toggle();        // if is on it is turned off an vice versa

To check the torch state:

    TorchControl.isOn();
    TorchControl.isOff();

* In the beginning the torch state is initialized to off. To ensure correctness of the on/off status
    you should call `TorchControl.turnOff()` (or `TorchControl.turnOn()`) somewhere at the beginning.

To flash the torch for a given duration:

    TorchControl.flash(const Duration(seconds: 1));

## Examples

See the companion example for demonstration on how to use the plugin.

There are two of my apps in PlayStore that use this plugin:
* [Flashlight Torcher](https://play.google.com/store/apps/details?id=com.topappfield.torcher) - Yet another torch/flashlight/lamp app with [source code available](https://github.com/topappfield/torcher).
* [Count On Me](https://play.google.com/store/apps/details?id=com.topappfield.CountOnMe) - A tally counter app with many options, advanced features, statistics, and various methods of counting.

## Trivia

Why yet another torch plugin? I needed a simple plugin for controlling the torch with the support for null safety, Dart 2, and a bit more flexible interface which was not readily available at the time. Moreover, I've never written a Flutter plugin as well as any Kotlin code before.
