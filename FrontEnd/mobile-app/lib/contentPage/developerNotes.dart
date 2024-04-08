import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class developerNotes extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: Row(
        children: [
          Expanded(
            child: Container(
              margin: EdgeInsets.only(right: 20, bottom: 5),
              alignment: Alignment.centerRight,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  Icon(
                    Icons.category,
                    color: Colors.white,
                  ),
                  SizedBox(width: 8),
                  Text(
                    'developer Notes',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 15,
                      fontFamily: 'GothicA1Extra',
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
