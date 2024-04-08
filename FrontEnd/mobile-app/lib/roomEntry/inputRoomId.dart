import 'package:flutter/material.dart';

class inputRoomId extends StatelessWidget {
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
                        fillColor: Color(0xFF9E9EBD),
                        hintText: 'Capstone Design Meeting',
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
                    margin: EdgeInsets.only(left: 10.0, bottom: 10.0, top: 10), // Meeting title 위젯에 아래쪽 마진 추가
                    child: Text(
                      'Copy invitation link',
                      style: TextStyle(color: Colors.white),
                    ),
                  ),


                  SizedBox(
                    width: 340,
                    height: 45,
                    child:TextField(
                      style: TextStyle(
                        color: Color(0xFF0B0B5A),
                      ),
                      decoration: InputDecoration(
                        filled: true,
                        fillColor: Colors.white,
                        hintText: '1Z2X3C4V5B6N7M8',
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
                    ),),


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
                        'Enter the meeting!',
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
          Container(
            margin: EdgeInsets.only(left: 20, top: 70, right: 20),
            alignment: Alignment.centerLeft,
            child: Column(
              children:[
                Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    Icon(
                      Icons.category,
                      color: Colors.white,
                    ),
                    SizedBox(width: 8),
                    Text(
                      'Copy invitation Code',
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 15,
                        fontFamily: 'GothicA1Extra',
                      ),
                    ),
                  ],
                ),
                Container(
                  margin: EdgeInsets.only(left: 10, top: 20, right: 10, bottom: 40),
                  width: 340,
                  height: 45,
                  child: TextField(
                    style: TextStyle(
                      color: Color(0xFF0B0B5A),
                    ),
                    textAlign: TextAlign.center,
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 10.0),
                      filled: true,
                      fillColor: Colors.white,
                      hintText: '1Z2X3C4V5B6N7M8',
                      hintStyle: TextStyle(
                        color: Color(0xFF0B0B5A),
                        fontFamily: 'GothicA1Extra',
                        fontSize: 15.0,
                      ),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(25.0),
                        borderSide: BorderSide.none,
                      ),
                      suffixIcon: IconButton(
                        icon: Icon(Icons.copy),
                        onPressed: () {
                          // 클릭 이벤트 처리
                          print('Search button clicked');
                        },
                      ),


                    ),
                  ),
                )

              ]

            ),

          ),


        ],
      ),
    );
  }
}
