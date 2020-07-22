/**
* Get the room id by parsing the room's url
* @param {url} String url of the current webpage
* @return {roomId} room id at end of the url
*/
function getRoomId(url) {
    var parser = document.createElement('a');
    parser.href = url;
    var query = parser.search.substring(1);
    // Create a temp array of len=2, 
    // holds 'roomId=' in [0] and room id in [1]
    var tempArrayForRoomId = query.split('=');
    var currentRoomId = tempArrayForRoomId[1];
    roomId = currentRoomId;
    return roomId;
}
