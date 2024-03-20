import 'package:flutter/material.dart';

class MyCustomBottomNavigationBar extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
          items: [
            BottomNavigationBarItem(
              icon: Icon(Icons.home),
              label: 'Home',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.videocam),
              label: 'VideoCam',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.notifications),
              label: 'alarm',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.people),
              label: 'MyPage',
            ),
          ]
      ),
    );
  }
}