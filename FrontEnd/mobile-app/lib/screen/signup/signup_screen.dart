import 'package:flutter/material.dart';
import 'package:mobileapp/component/back_button.dart';

class SignUpScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFF0B0B5A),
      body: SafeArea(
        top: true,
        bottom: true,
        child: SingleChildScrollView(
          child: Column(
            children: [
              BackButtonWidget(),
            ],
          ),
        ),
      ),
    );
  }
}