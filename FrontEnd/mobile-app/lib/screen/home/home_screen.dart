import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

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
        child: SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          child: Row(
            children: [
              SizedBox(width: 20), // 왼쪽 여백
              _HomeLogo(),
              SizedBox(width: 20), // 로고와 버튼 사이의 간격 조정
              _HomeButton(),
              SizedBox(width: 20), // 오른쪽 여백
            ],
          ),
        ),
      ),
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
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        ElevatedButton(
          style: ElevatedButton.styleFrom(
            minimumSize: Size(350, 50),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          onPressed: () {
            print('Login Button is clicked');
          },
          child: Text(
            'Login',
            style: TextStyle(fontFamily: 'GothicA1ExtraBold'),
          ),
        ),
        SizedBox(height: 10), // 버튼 사이의 간격 조정
        OutlinedButton(
          style: OutlinedButton.styleFrom(
            minimumSize: Size(350, 50),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          onPressed: () {
            print('Register Button is clicked');
          },
          child: Text(
            'Register',
            style:
                TextStyle(color: Colors.white, fontFamily: 'GothicA1ExtraBold'),
          ),
        ),
      ],
    );
  }
}
