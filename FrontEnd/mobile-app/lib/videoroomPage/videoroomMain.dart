import 'package:flutter/material.dart';
import 'package:mobileapp/videoroomPage/createRoom.dart';
import 'package:mobileapp/videoroomPage/createRoomLogo.dart';
import 'package:mobileapp/component/logo.dart';

import '../component/bottom_navigation_bar.dart';
class videoroomMain extends StatelessWidget {
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
              logo(),
              createRoomLogo(),
              createRoom(),
            ],
          ),
        ),
      ),
      bottomNavigationBar: MyCustomBottomNavigationBar(),
    );
  }
}
