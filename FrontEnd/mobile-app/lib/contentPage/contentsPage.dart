import 'package:flutter/material.dart';
import 'package:mobileapp/component/logo.dart';
import 'package:mobileapp/contentPage/borderBox.dart';
import 'package:mobileapp/contentPage/developerNotes.dart';
import 'package:mobileapp/contentPage/ourServices.dart';
import 'package:mobileapp/contentPage/aboutUs.dart';

import '../component/bottom_navigation_bar.dart';

class contentsPage extends StatelessWidget {
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
              developerNotes(),
              borderBox(),
              ourServices(),
              aboutUs(),
            ],
          ),
        ),
      ),
      bottomNavigationBar: MyCustomBottomNavigationBar(),
    );
  }
}
