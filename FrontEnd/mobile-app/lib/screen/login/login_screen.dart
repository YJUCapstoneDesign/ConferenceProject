import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:mobileapp/screen/home/home_screen.dart';

class LoginScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFF0B0B5A),
      body: SafeArea(
        top: true,
        bottom: true,
        child: SingleChildScrollView(
          child: Container(
            width: MediaQuery.of(context).size.width,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                _BackButtonWidget(),
                _TitleWidget(),
                _InputBoxWidget(),
                _LoginButtonWidget(),
                _LineWidget(),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    _KakaoButtonWidget(),
                    _NaverButtonWidget(),
                    _GoogleButtonWidget(),
                  ],
                ),
                _RegisterTextButtonWidget(),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

// 뒤로 가기 버튼
class _BackButtonWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Container(
          margin: EdgeInsets.only(top: 20.0, left: 30.0, bottom: 30.0),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(8.0),
            border: Border.all(
              color: Colors.white,
              width: 2.0,
              style: BorderStyle.solid,
            ),
          ),
          width: 45.0,
          height: 45.0,
          alignment: Alignment.center, // Center align the content of the container
          child: IconButton(
            iconSize: 24.0,
            icon: Icon(
              Icons.chevron_left,
              color: Colors.white,
            ),
            onPressed: () {
              Navigator.pop(context);
            },
          ),
        ),
      ],
    );
  }
}

// 타이틀 위젯
class _TitleWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.centerLeft,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            margin: EdgeInsets.only(left: 30.0),
            child: Text(
              'Welcome back!',
              style: TextStyle(
                color: Colors.white,
                fontFamily: 'GothicA1ExtraBold',
                fontSize: 24.0,
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(bottom: 20.0, left: 30.0),
            child: Text(
              'Everyone is waiting for you!',
              style: TextStyle(
                color: Colors.white,
                fontFamily: 'GothicA1ExtraBold',
                fontSize: 24.0,
              ),
            ),
          )
        ],
      ),
    );
  }
}

// 인풋박스 위젯
class _InputBoxWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: [
          Container(
            margin: EdgeInsets.only(bottom: 30.0),
            padding: EdgeInsets.only(left: 30.0, right: 35.0),
            child: TextField(
              style: TextStyle(
                color: Color(0xFF0B0B5A), // Change text color to black
              ),
              decoration: InputDecoration(
                filled: true,
                fillColor: Colors.white,
                hintText: 'Enter your email',
                hintStyle: TextStyle(
                  color: Color(0xFF0B0B5A),
                  fontFamily: 'GothicA1ExtraBold',
                  fontSize: 16.0,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide.none,
                ),
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(bottom: 10.0),
            padding: EdgeInsets.only(left: 30.0, right: 35.0),
            child: TextField(
              style: TextStyle(
                color: Color(0xFF0B0B5A), // Change text color to black
              ),
              decoration: InputDecoration(
                filled: true,
                fillColor: Colors.white,
                hintText: 'Enter your password',
                hintStyle: TextStyle(
                  color: Color(0xFF0B0B5A),
                  fontFamily: 'GothicA1ExtraBold',
                  fontSize: 16.0,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide.none,
                ),
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(left: 200.0),
            child: Text(
              'Forgot your password?',
              style: TextStyle(
                color: Colors.white,
                fontFamily: 'GothicA1ExtraBold',
                fontSize: 12.0,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

// 로그인버튼 위젯
class _LoginButtonWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 30.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10.0),
        color: Color(0xFF0B0B5A),
        border: Border.all(
          color: Colors.white,
          width: 2.0,
          style: BorderStyle.solid,
        ),
      ),
      child: OutlinedButton(
        style: OutlinedButton.styleFrom(
          minimumSize: Size(300, 50),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10),
          ),
        ),
        onPressed: () {},
        child: Text(
          'Login',
          style: TextStyle(
            color: Colors.white,
            fontFamily: 'GothicA1ExtraBold',
            fontSize: 16.0,
          ),
        ),
      ),
    );
  }
}

// 로그인 버튼과 구글, 네이버, 카카오 버튼 사이의 선
class _LineWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 30.0),
      child: Row(
        children: [
          Expanded(
            child: Container(
              padding: EdgeInsets.only(left: 30.0, right: 10.0),
              child: Divider(
                color: Colors.white,
                thickness: 1.0,
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 3.0),
            child: Text(
              'Or Login with',
              style: TextStyle(
                color: Colors.white,
                fontFamily: 'GothicA1ExtraBold',
                fontSize: 10.0,
              ),
            ),
          ),
          Expanded(
            child: Container(
              padding: EdgeInsets.only(left: 10.0, right: 30.0),
              child: Divider(
                color: Colors.white,
                thickness: 1.0,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

// 구글, 네이버, 카카오 버튼 위젯
// 카카오 버튼
class _KakaoButtonWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 25.0, right: 25.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10.0),
        color: Color(0xFF0B0B5A),
        border: Border.all(
          color: Colors.white,
          width: 1.5,
          style: BorderStyle.solid,
        ),
      ),
      child: OutlinedButton(
          style: OutlinedButton.styleFrom(
            minimumSize: Size(100, 50),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          onPressed: () {},
          child: Icon(
            Icons.abc,
          )),
    );
  }
}

// 네이버 버튼
class _NaverButtonWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 25.0, right: 25.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10.0),
        color: Color(0xFF0B0B5A),
        border: Border.all(
          color: Colors.white,
          width: 1.5,
          style: BorderStyle.solid,
        ),
      ),
      child: OutlinedButton(
          style: OutlinedButton.styleFrom(
            minimumSize: Size(100, 50),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          onPressed: () {},
          child: Icon(
            Icons.abc,
          )),
    );
  }
}

// 구글 버튼
class _GoogleButtonWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 25.0),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10.0),
        color: Color(0xFF0B0B5A),
        border: Border.all(
          color: Colors.white,
          width: 1.5,
          style: BorderStyle.solid,
        ),
      ),
      child: OutlinedButton(
          style: OutlinedButton.styleFrom(
            minimumSize: Size(100, 50),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          onPressed: () {},
          child: Icon(
            Icons.abc,
          )),
    );
  }
}

// 회원가입 텍스트 버튼
class _RegisterTextButtonWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 50.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          RichText(
            text: TextSpan(
              text: 'Don\'t have an account?',
              style: TextStyle(
                color: Colors.white,
                fontFamily: 'GothicA1ExtraBold',
                fontSize: 10.0,
              ),
              children: <TextSpan>[
                TextSpan(
                  text: ' Register',
                  style: TextStyle(
                    color: Colors.white,
                    fontFamily: 'GothicA1ExtraBold',
                    fontSize: 10.0,
                    decoration: TextDecoration.underline,
                  ),
                  recognizer: TapGestureRecognizer()..onTap = () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => HomeScreen()),
                    );
                  },
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}
