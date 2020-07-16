var roomId;
const privateRoomPath = "/views/private-room.html?id=";
const joinRoomPath = "/views/join-room.html";

function verifyRoom() {
    roomId = getRoomId(window.location.href);
     // Check that the current room id exits, then return playlist of given room
    let roomPromise = await fetch('/verify-room?roomId='+roomId);
    // fetch the json-version of the urls for all the youtube videos
    let isRoomIdValid = await roomPromise.json();
    redirectPage(isRoomIdValid);
}

function redirectPage(isRoomIdValid){
    if(isRoomIdValid){
        window.location.assign(privateRoomPath+roomId);
    } else {
        window.location.assign(joinRoomPath)
    }
}
