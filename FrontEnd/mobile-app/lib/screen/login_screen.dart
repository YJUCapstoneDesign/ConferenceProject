import 'package:flutter/material.dart';

class LoginScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFF0B0B5A),
      body: SafeArea(
        top: true,
        bottom: true,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            _Title(),
            _InputBox(),
          ],
        ),
      ),
    );
  }
}

class _Title extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.centerLeft,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            margin: EdgeInsets.only(bottom: 20.0, left: 30.0),
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

class _InputBox extends StatelessWidget {
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
                color: Colors.white,
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
                color: Colors.white,
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
        ],
      ),
    );
  }
}
