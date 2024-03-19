import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:mobileapp/screen/home_screen.dart';
import 'package:mobileapp/component/bottom_navigation_bar.dart';

void main() {
  // 플러터 프레임워크가 앱을 실행할 준비가 될 떄까지 기다림
  WidgetsFlutterBinding.ensureInitialized();
  runApp(
    MaterialApp(
      home: HomeScreen(),
    ),
  );
}