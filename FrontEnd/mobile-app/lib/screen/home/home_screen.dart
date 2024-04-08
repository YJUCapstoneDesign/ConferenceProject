import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:mobileapp/component/logo.dart';
import 'package:mobileapp/screen/login/login_screen.dart';
import 'package:mobileapp/screen/signup/signup_screen.dart';
import 'package:mobileapp/contentPage/contentsPage.dart';

import '../../component/bottom_navigation_bar.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // StatusBar 배경색 투명하게 색상을 변경하고, StatusBar 아이콘 색상을 변경합니다.
    SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle(
      statusBarColor: Colors.transparent,
      statusBarIconBrightness: Brightness.light,
      statusBarBrightness: Brightness.light,
    ));
    return Scaffold(
      backgroundColor: Color(0xFF0B0B5A),
      body: SafeArea(
        top: true,
        bottom: true,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Expanded(
              child: Center(
                child: _HomeLogo(),
              ),
            ),
            _HomeButton(),
          ],
        ),
      ),
      bottomNavigationBar: MyCustomBottomNavigationBar(),
    );
  }
}

// HomeScreen 클래스의 로고 부분 입니다.
class _HomeLogo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text(
        'UNMUTE',
        style: TextStyle(
            color: Colors.white, fontSize: 40, fontFamily: 'GothicA1ExtraBold'),
      ),
    );
  }
}

// HomeScreen 클래의 버튼 부분 입니다.
class _HomeButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
        child: Column(
      children: [
        Container(
          margin: EdgeInsets.only(bottom: 10.0),
          child: ElevatedButton(
            style: ElevatedButton.styleFrom(
              minimumSize: Size(350, 50),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => LoginScreen()),
              );
            },
            child: Text(
              'Login',
              style: TextStyle(fontFamily: 'GothicA1ExtraBold'),
            ),
          ),
        ),
        Container(
          margin: EdgeInsets.only(bottom: 50.0),
          child: OutlinedButton(
            style: OutlinedButton.styleFrom(
              minimumSize: Size(350, 50),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => SignUpScreen()),
              );
            },
            child: Text(
              'Register',
              style: TextStyle(
                  color: Colors.white, fontFamily: 'GothicA1ExtraBold'),
            ),
          ),
        ),
      ],
    ));
  }
}
