var roomId;
var playlistUrls;
var playlistIds;
var youtubePlayer;
var playerTimeStamp
var YT_BASE_URL = "https://www.youtube.com/embed/";

// Calls the three functions associated with loading the room's iframe
async function loadPlayerDiv(){
    loadVideo();
    // Get the room id from the private room's url
    await getRoomId(window.location.href);
    await fetchPrivateRoomVideo(roomId);
}

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

/**
* Take in currentRoomId and create a global array of youtube video urls and 
* a global array of the video ids of the playlist
* @param {currentRoomId} String Holds the room Id of the the user's room
* @return {roomVideoUrl} The Url of the video to be displayed for the room
 */
async function fetchPrivateRoomVideo(currentRoomId) {
    // Check that the current room id exits, then return playlist of given room
    let roomPromise = await fetch('/collect-videos?roomId='+roomId);
    // fetch the json-version of the urls for all the youtube videos
    let roomVideoUrls = await roomPromise.json();
    // create an array of all the YT videos' urls
    playlistIds = extractVideoIds(roomVideoUrls);
}

// Take the array of urls and create an array of their youtube ids
function extractVideoIds(roomVideoUrls){
    return roomVideoUrls.map(id => id.substring(YT_BASE_URL.length));
}

// load the playlist of videos to the container
function loadRoomPlaylist(){
    youtubePlayer.loadPlaylist({playlist: playlistIds});
}

// This code loads the IFrame Player API code asynchronously.
function loadVideo(){
    var tag = document.createElement('script');
    tag.src = "https://www.youtube.com/iframe_api";
    var firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}

// This function puts player in the div tag
// after the API code downloads.
function onYouTubeIframeAPIReady() {
    youtubePlayer = new YT.Player('player-div', 
    {
        playerVars: {
            autoplay: 0,      // Don't autoplay the initial video
            rel: 0,           //  Don’t show related videos
            theme: "light",   // Use a light player instead of a dark one
            controls: 1,      // Show player controls
            showinfo: 0,      // Don’t show title or loader
            modestbranding: 1 // No You Tube logo on control bar
        },
        events: {               
            // Callback when onReady fires
            onReady: onPlayerReady,
            // Callback when onStateChange fires
            onStateChange: onStateChange,
        }
    });
}

function stopVideo() {
    youtubePlayer.stopVideo();
}

// Log state changes
function onStateChange(event) {
    var state = "undefined";
    switch (event.data) {
        case YT.PlayerState.UNSTARTED:
            state = "-1";
            break;
        case YT.PlayerState.ENDED:
            state = "0";
            break;
        case YT.PlayerState.PLAYING:
            state = "1";
            break;
        case YT.PlayerState.PAUSED:
            state = "2";
            break;
        case YT.PlayerState.BUFFERING:
            state = "3";
            break;
        case YT.PlayerState.CUED:
            state = "5";
            break;
        default:
            state = "unknown (" + event.data + ")";
    }
    console.log('onStateChange: ' + state);
    playerTimeStamp = Math.round(youtubePlayer.getCurrentTime());
    updateCurrentState(state, playerTimeStamp);
}

// Every three seconds you listen to youtube player for any detection of change
window.setInterval(function(){
    listenForStateChange();
}, 1000);

// This function fetches the state of the private room video and
// plays/pauses it accordingly
function listenForStateChange(){
    $(document).ready(function(){
        $.get(`/sync-room?roomId=${roomId.toString()}`,function(data, status){
            console.log(data);
            // Change timestamp to match group timestamp if client is not within two seconds of room
            if(Math.abs(playerTimestamp - data.timestamp) >= 2){
                console.log('Seeking to ' + data.timestamp)
                youtubePlayer.seekTo(data.timestamp);
            }
            // Change state to match group state
            if(data.currentState === "1"){
                console.log('Group video is on state: playing')
                playVideo();
            } else if(data.currentState === "2") {
                console.log('Group video is on state: paused')
                pauseVideo();
            } else {
                console.log("State is not paused nor played. Do nothing.")
            }
        })
    })
}

// Send the user's state to the servlet every time their state changes
function updateCurrentState(currentState, currentTime){
    console.log(currentState);
    console.log(currentTime);
    fetch(`/sync-room?roomId=${roomId.toString()}&userState=${currentState}&timeStamp=${currentTime}`,{method:'POST'})
    // fetch(`/chat?roomId=${window.roomId}&userEmail=${window.localStorage.getItem("userEmail")}&text-input=${document.getElementById("text-in").value}`, {method: 'POST'})
    // $(document).ready(function(){
    //     $.post(`/sync-room?roomId=${roomId.toString()}`,
    //     {
    //         userState: currentState,
    //         timeStamp: currentTime
    //     }
    //     );
    // })
    console.log('I am sending the state: ' + currentState)
}

// The API will call this function when the video player is ready.
function onPlayerReady(event) {
    document.getElementById('player-div').style.borderColor = '#FF6D00';
    loadRoomPlaylist();
}

function playVideo(){
    youtubePlayer.playVideo();
}

function pauseVideo(){
    youtubePlayer.pauseVideo();
}

/* Chat Room Feature */

// Shows and refreshes the messages shown on the private room page
async function displayChat() {   
    let response = await fetch(`/chat?roomID=${window.roomId}`);
    let messages = await response.json();    
    const messageElement = document.getElementById('chat-messages');    
    messageElement.innerHTML = '';    
    for (message in messages) {      
        messageElement.appendChild(createNewMessage(messages[message]));    
    }      
    displayChat();
}

// Inserts messages as HTML elements
function createNewMessage(msg) {    
    const listItem = document.createElement('li');    
    listItem.innerHTML += 
    `<div class="msgText">
        <p>
            <span class="sender">${msg.sender}: </span><span class="msgBody">${msg.message}</span>
        </p> 
        <span class="sub-text"> Sent at  ${toTime(msg.timestamp)}</span>
    </div>`;    
    return listItem;
}

// Manipulates timestamp value to be displayed in hh:mm(+AM/PM) format 
function toTime(ms) {    
    // Adds '0' if necessary for single digit values    
    function checkZero(n) {        
        return (n<10 ? '0':'') + n;    
    }    
    var date = new Date(ms);    
    var hrs = date.getHours();    
    var mins = date.getMinutes();    
    var ampm = hrs >= 12 ? 'PM' : 'AM';    
    hrs = hrs % 12;    
    hrs = hrs ? hrs : 12;    
    var time = checkZero(hrs) + ':' + checkZero(mins)+ ' ' + ampm;    
    return time;
}
