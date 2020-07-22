var roomId;
const privateRoomPath = "/views/private-room.html?id=";
const joinRoomPath = "/views/join-room.html";

// Send inputted room Id to VerifyRoomServlet them redirect them to proper page
async function verifyRoom() {
    let user = window.localStorage.getItem("userEmail");
    roomId = getRoomId(window.location.href);
     // Check that the current room id exits, then return playlist of given room
    let roomPromise = await fetch(`/verify-room?roomId=${roomId}&userEmail=${user}`);
    // fetch the json-version of the urls for all the youtube videos
    let isRoomIdValid = await roomPromise.json();
    redirectPage(isRoomIdValid);
}

// If the room is valid, redirect them to private room page
// If not valid, send them back to join room page
function redirectPage(isRoomIdValid){
    if(isRoomIdValid){
        window.location.assign(privateRoomPath+roomId);
    } else {
        window.location.assign(joinRoomPath)
    }
}
