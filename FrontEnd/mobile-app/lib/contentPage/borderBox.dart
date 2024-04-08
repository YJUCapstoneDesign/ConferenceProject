import 'package:flutter/material.dart';

class borderBox extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Center(
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
                child: Row(
                  children: [
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: [
                          Container(
                            margin: EdgeInsets.only(bottom: 15),
                            child: Text(
                                '" BrainStorming techniques \nhave a new feature! "',
                                style: TextStyle(color: Colors.white),
                                textAlign: TextAlign.end),
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.end,
                            children: [
                              SizedBox(
                                  width: 60,
                                  height: 20,
                                  child: OutlinedButton(
                                    style: OutlinedButton.styleFrom(
                                      foregroundColor: Colors.white,
                                      padding:
                                          EdgeInsets.symmetric(horizontal: 0.0),
                                      side: BorderSide(color: Colors.white),
                                    ),
                                    onPressed: () {},
                                    child: Text(
                                      "new",
                                      style: TextStyle(fontSize: 10),
                                    ),
                                  )),
                              SizedBox(width: 15),
                              SizedBox(
                                  width: 60,
                                  height: 20,
                                  child: OutlinedButton(
                                    style: OutlinedButton.styleFrom(
                                      foregroundColor: Colors.white,
                                      padding:
                                          EdgeInsets.symmetric(horizontal: 0.0),
                                      side: BorderSide(color: Colors.white),
                                    ),
                                    onPressed: () {},
                                    child: Text(
                                      "Update",
                                      style: TextStyle(fontSize: 10),
                                    ),
                                  )),
                              SizedBox(width: 15),
                              SizedBox(
                                  width: 80,
                                  height: 20,
                                  child: OutlinedButton(
                                    style: OutlinedButton.styleFrom(
                                      foregroundColor: Colors.white,
                                      padding:
                                          EdgeInsets.symmetric(horizontal: 0.0),
                                      side: BorderSide(color: Colors.white),
                                    ),
                                    onPressed: () {},
                                    child: Text(
                                      "Brainstorming",
                                      style: TextStyle(fontSize: 10),
                                    ),
                                  )),
                            ],
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Expanded(
                                child: Padding(
                                  padding: EdgeInsets.only(top: 20.0, left: 15),
                                  child: Text('198742 views',
                                      textAlign: TextAlign.start,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 10)),
                                ),
                              ),
                              Expanded(
                                child: Padding(
                                  padding: EdgeInsets.only(top: 20.0),
                                  child: Text('38972 likes',
                                      textAlign: TextAlign.center,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 10)),
                                ),
                              ),
                              Expanded(
                                child: Padding(
                                  padding: EdgeInsets.only(top: 20.0),
                                  child: Text('329302 comments',
                                      textAlign: TextAlign.end,
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 10)),
                                ),
                              ),
                            ],
                          )
                        ],
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 5.0, bottom: 80.0),
                      child: Icon(
                        Icons.face,
                        color: Colors.white,
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
