import 'package:flutter/material.dart';

class createRoom extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Padding(
            padding: EdgeInsets.symmetric(vertical: 5.0),
            child: Container(
              margin: EdgeInsets.symmetric(horizontal: 15.0),
              padding: EdgeInsets.all(24.0),
              decoration: BoxDecoration(
                border: Border.all(
                  width: 1,
                  color: Colors.white,
                ),
                borderRadius: BorderRadius.circular(10.0),
              ),


             child: Column(
               crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                  margin: EdgeInsets.only(left: 10.0, bottom: 10.0, top: 10),
                  child: Text(
                    'Meeting Title',
                    style: TextStyle(color: Colors.white),
                  ),
                ),



                SizedBox(
                  width: 340,
                  height: 45,
                  child: TextField(

                    style: TextStyle(
                      color: Color(0xFF0B0B5A),
                    ),
                    decoration: InputDecoration(
                      filled: true,
                      fillColor: Colors.white,
                      hintText: 'Enter your title',
                      hintStyle: TextStyle(
                        color: Color(0xFF0B0B5A),
                        fontFamily: 'GothicA1Extra',
                        fontSize: 16.0,
                      ),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(30.0),
                        borderSide: BorderSide.none,
                      ),
                      contentPadding: EdgeInsets.symmetric(vertical: 15.0, horizontal: 20.0),
                    ),
                  ),
                ),

                Container(
                  margin: EdgeInsets.only(left: 10.0, bottom: 10.0, top: 10),
                  child: Text(
                    'Creation date',
                    style: TextStyle(color: Colors.white),
                  ),
                ),


                SizedBox(
                  width: 340,
                  height: 45,
                  child: TextField(
                    style: TextStyle(
                      color: Color(0xFF0B0B5A),
                    ),
                    decoration: InputDecoration(
                      filled: true,
                      fillColor: Colors.white,
                      hintText: '20xx-xx-xx',
                      hintStyle: TextStyle(
                        color: Color(0xFF0B0B5A),
                        fontFamily: 'GothicA1Extra',
                        fontSize: 16.0,
                      ),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(30.0),
                        borderSide: BorderSide.none,
                      ),
                      contentPadding: EdgeInsets.symmetric(vertical: 15.0, horizontal: 20.0),
                    ),
                  ),
                ),

                Container(
                  margin: EdgeInsets.only(left: 10.0, bottom: 10.0, top: 10),
                  child: Text(
                    'End date',
                    style: TextStyle(color: Colors.white),
                  ),
                ),


                SizedBox(
                  width: 340,
                  height: 45,
                  child: TextField(
                    style: TextStyle(
                      color: Color(0xFF0B0B5A),
                    ),
                    decoration: InputDecoration(
                      filled: true,
                      fillColor: Colors.white,
                      hintText: '20xx-xx-xx',
                      hintStyle: TextStyle(
                        color: Color(0xFF0B0B5A),
                        fontFamily: 'GothicA1Extra',
                        fontSize: 16.0,
                      ),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(30.0),
                        borderSide: BorderSide.none,
                      ),
                      contentPadding: EdgeInsets.symmetric(vertical: 15.0, horizontal: 20.0),
                    ),
                  ),
                ),


                Container(
                  margin: EdgeInsets.only(top: 40.0, bottom: 20.0),
                  alignment: Alignment.center,

                  child: OutlinedButton(
                    style: OutlinedButton.styleFrom(
                      minimumSize: Size(350, 50),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30),
                      ),
                      side: BorderSide(color: Colors.white, width: 1),
                    ),
                    onPressed: () {},
                    child: Text(
                      'Create!',
                      style: TextStyle(
                        color: Colors.white,
                        fontFamily: 'GothicA1Extra',
                        fontSize: 16.0,
                      ),
                    ),
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
