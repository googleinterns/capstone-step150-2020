function toTime_caseTested_expectedResult() {
    var ms = 3600000;
    var res = toTime(ms);
    console.log("Expected 1:00AM");
    console.log("Actual "+res);
    var ms = 46800000;
    var res = toTime(ms);
    console.log("Expected 1:00PM");
    console.log("Actual "+res);
}

function getRoomId_caseTested_expectedResult(){
    var testUrl = 'youtubeparty.appspot.com/views/private-getRoomId_castTested_expectedResult.html?roomId=4674621675131';
    var expectedRoomId = 4674621675131;
    var actualRoomId = getRoomId(testUrl);
    console.log("Expected: " + expectedRoomId);
    console.log("Actual: " + actualRoomId);
    testUrl = 'youtubeparty.appspot.com/views/private-getRoomId_castTested_expectedResult.html?roomId=45';
    expectedRoomId = 45;
    actualRoomId = getRoomId(testUrl);
    console.log("Expected: " + expectedRoomId);
    console.log("Actual: " + actualRoomId);
}
