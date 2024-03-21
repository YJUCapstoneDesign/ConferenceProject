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
              _SignupTitleWidget(),
              _SignupInputWidget(),
              _SignupButtonWidget(),
            ],
          ),
        ),
      ),
    );
  }
}

class _SignupTitleWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(right: 230.0, bottom: 20.0),
      child: Text(
        'Sign up!',
        style: TextStyle(
          color: Colors.white,
          fontSize: 30,
          fontFamily: 'GothicA1ExtraBold',
        ),
      ),
    );
  }
}

class _SignupInputWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: [
          Container(
            margin: EdgeInsets.only(bottom: 20.0),
            padding: EdgeInsets.only(left: 30.0, right: 35.0),
            child: TextField(
              style: TextStyle(
                color: Color(0xFF0B0B5A), // Change text color to black
              ),
              decoration: InputDecoration(
                filled: true,
                fillColor: Colors.white,
                hintText: 'Enter your name',
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
            margin: EdgeInsets.only(bottom: 20.0),
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
            margin: EdgeInsets.only(bottom: 20.0),
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
            margin: EdgeInsets.only(bottom: 20.0),
            padding: EdgeInsets.only(left: 30.0, right: 35.0),
            child: TextField(
              style: TextStyle(
                color: Color(0xFF0B0B5A), // Change text color to black
              ),
              decoration: InputDecoration(
                filled: true,
                fillColor: Colors.white,
                hintText: 'Enter your address',
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
            margin: EdgeInsets.only(bottom: 20.0),
            padding: EdgeInsets.only(left: 30.0, right: 35.0),
            child: TextField(
              style: TextStyle(
                color: Color(0xFF0B0B5A), // Change text color to black
              ),
              decoration: InputDecoration(
                filled: true,
                fillColor: Colors.white,
                hintText: 'Enter your phone',
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
        ],
      ),
    );
  }
}

class _SignupButtonWidget extends StatelessWidget {
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
          minimumSize: Size(350, 50),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10),
          ),
        ),
        onPressed: () {},
        child: Text(
          'Sign up',
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
