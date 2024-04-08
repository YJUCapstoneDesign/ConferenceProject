import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class aboutUs extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(right: 30, top: 30),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              Icon(
                Icons.category,
                color: Colors.white,
              ),
              SizedBox(width: 8),
              Text(
                'about Us',
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 15,
                  fontFamily: 'GothicA1Extra',
                ),
              ),
            ],
          ),
          Text(
            '"Our project is a conference program\n'
                'using Ai-based summarization\n'
                'brainstorming techniques, and mind maps."\n\n',
            textAlign: TextAlign.end,
            style: TextStyle(
              color: Colors.white,
              fontSize: 12,
              fontFamily: 'GothicA1Extra',
            ),
          ),
        ],
      ),
    );
  }
}
