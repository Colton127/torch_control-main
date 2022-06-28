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
                    TorchControl.turn(true, torchLevel: 0.5);
                  },
                  child: const Text('On half brightness')),
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
