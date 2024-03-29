import 'package:flutter/material.dart';
import 'package:torch_control/torch_control.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool torchReady = false;

  @override
  void initState() {
    super.initState();
    checkTorch();
  }

  void checkTorch() async {
    torchReady = await TorchControl.ready();
  }

  void checkState() {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              Text('ready: $torchReady\n'),
              Text('isOn: ${TorchControl.isOn}\n'),
              Text('isOff: ${TorchControl.isOff}\n'),
              TextButton(
                  onPressed: () {
                    TorchControl.deviceLock(true);
                  },
                  child: const Text('READY')),
              TextButton(
                  onPressed: () async {
                    final maxBrightness = await TorchControl.maxBrightness();
                    print('maxBrightness: $maxBrightness');
                  },
                  child: const Text('Get Max Brightness')),
              TextButton(
                  onPressed: () async {
                    final maxBrightness = await TorchControl.setBrightness(1);
                    print('maxBrightness: $maxBrightness');
                  },
                  child: const Text('Brightness 1')),
              TextButton(
                  onPressed: () async {
                    final maxBrightness = await TorchControl.setBrightness(2);
                    print('maxBrightness: $maxBrightness');
                  },
                  child: const Text('Brightness 2')),
              TextButton(
                  onPressed: () async {
                    final maxBrightness = await TorchControl.setBrightness(3);
                    print('maxBrightness: $maxBrightness');
                  },
                  child: const Text('Brightness 3')),
              TextButton(
                  onPressed: () async {
                    final maxBrightness = await TorchControl.setBrightness(4);
                    print('maxBrightness: $maxBrightness');
                  },
                  child: const Text('Brightness 4')),
              TextButton(
                  onPressed: () async {
                    final maxBrightness = await TorchControl.setBrightness(5);
                    print('maxBrightness: $maxBrightness');
                  },
                  child: const Text('Brightness 5')),
              TextButton(
                  onPressed: () {
                    TorchControl.deviceLock(false);
                  },
                  child: const Text('UNREADY')),
              TextButton(
                  onPressed: () {
                    TorchControl.turnOn().whenComplete(checkState);
                  },
                  child: const Text('On')),
              TextButton(
                  onPressed: () {
                    TorchControl.loop(.25, torchLevel: 100);
                  },
                  child: const Text('On in a while')),
              TextButton(
                  onPressed: () {
                    TorchControl.loop(1, torchLevel: 100);
                  },
                  child: const Text('L00P 100')),
              TextButton(
                  onPressed: () {
                    TorchControl.loop(20, torchLevel: 25);
                  },
                  child: const Text('L00P 25')),
              TextButton(
                  onPressed: () {
                    TorchControl.loop(20, torchLevel: 50);
                  },
                  child: const Text('L00P 50')),
              TextButton(
                  onPressed: () {
                    TorchControl.loop(20, torchLevel: 100);
                  },
                  child: const Text('L00P 100')),
              TextButton(
                  onPressed: () {
                    TorchControl.cancelLoop();
                  },
                  child: const Text('NO L00P')),
              TextButton(
                  onPressed: () {
                    TorchControl.turnOff().whenComplete(checkState);
                  },
                  child: const Text('Off')),
              TextButton(
                  onPressed: () {
                    TorchControl.toggle().whenComplete(checkState);
                  },
                  child: const Text('Toggle')),
              TextButton(
                  onPressed: () {
                    TorchControl.flash(const Duration(seconds: 1)).whenComplete(checkState);
                    checkState();
                  },
                  child: const Text('Flash for 1 s')),
              TextButton(onPressed: checkState, child: const Text('Refresh UI')),
            ],
          ),
        ),
      ),
    );
  }
}
