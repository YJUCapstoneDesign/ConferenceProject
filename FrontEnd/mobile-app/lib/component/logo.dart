import 'package:flutter/material.dart';

import 'package:flutter/material.dart';

class logo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(25),
      child: Row(
        children: [
          Text(
            'UNMUTE',
            style: TextStyle(
              color: Colors.white,
              fontSize: 20,
              fontFamily: 'GothicA1ExtraBold',
            ),
          ),
        ],
      ),
    );
  }
}
