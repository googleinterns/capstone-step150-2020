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