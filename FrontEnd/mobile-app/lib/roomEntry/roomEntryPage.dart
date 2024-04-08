import 'package:flutter/material.dart';
import 'package:mobileapp/component/logo.dart';
import 'package:mobileapp/roomEntry/inputRoomId.dart';

import '../component/bottom_navigation_bar.dart';
class roomEntryPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Color(0xFF0B0B5A),
        body: SafeArea(
            top: true,
            bottom: true,
            child: SingleChildScrollView(
                child: Container(
                    child: Column(children: [
                      logo(),
                      inputRoomId(),

                    ])))),
      bottomNavigationBar: MyCustomBottomNavigationBar(),
    );
  }
}
