const STATE_LISTENER_TIMER_MS = 1000;
const PLAYER_SECONDS_DISCREPANCY = 2;
const PLAYER_STATE_UNSTARTED = "UNSTARTED"
const PLAYER_STATE_ENDED = "ENDED";
const PLAYER_STATE_PLAYED = "PLAYING";
const PLAYER_STATE_PAUSED = "PAUSED";
const PLAYER_STATE_BUFFERING = "BUFFERING";
const PLAYER_STATE_VIDEO_CUED = "CUED";
const YT_BASE_URL = "https://www.youtube.com/embed/";
const SYNC_PATH_WITH_QUERY_PARAM = '/sync-room?roomId=';
var roomId;
var playlistUrls;
var playlistIds;
var youtubePlayer;
var playerTimeStamp;
var currentVideoId;

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
* Take in currentRoomId and fetch the current video the private room is on
* @param {currentRoomId} String Holds the room Id of the the user's room
 */
async function fetchPrivateRoomVideo(currentRoomId) {
    // Check that the current room id exits, then return playlist of given room
    let roomPromise = await fetch('/collect-video?roomId='+roomId);
    // fetch the json-version of the urls for all the youtube videos
    let privateRoom = await roomPromise.json();
    // play video of where private room is at
    currentVideoId = privateRoom.id;
}

// Load the current video of the private room from the specified start time
function loadRoomVideo(currentVideoId, startSeconds){
    youtubePlayer.loadVideoById(currentVideoId, startSeconds);
}

// The API will call this function when the video player is ready.
function onPlayerReady(event) {
    document.getElementById('player-div').style.borderColor = '#FF6D00';
    console.log('In the on player ready function');
    loadRoomVideo(currentVideoId, 0);
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

// Log state changes and update the servlet with those changes
function onStateChange(event) {
    var state = event.data
    playerTimeStamp = Math.round(youtubePlayer.getCurrentTime());
    updateCurrentState(state, playerTimeStamp);
}

// Every three seconds you listen to youtube player for any detection of change
window.setInterval(function(){
    listenForStateChange();
}, STATE_LISTENER_TIMER_MS);

// This function fetches the information of the private room and adjusts
// the time, video id, and state of their personal player accordingly
async function listenForStateChange(){
    let privateRoomDataPromise = await fetch(SYNC_PATH_WITH_QUERY_PARAM+roomId);
    // fetch the json-version of the urls for all the youtube videos
    let privateRoomData = await privateRoomDataPromise.json();
    // Change timestamp to match group timestamp if client is not within two seconds of room
    if(Math.abs(playerTimeStamp - privateRoomData.timestamp) >= PLAYER_SECONDS_DISCREPANCY){
        youtubePlayer.seekTo(privateRoomData.timestamp);
    }
    // When the video is done, the servlet sends back the next videos id
    // This will not match the currentVideoId, so you must update the current
    // video to match that of the private rooms video
    if(privateRoomData.id !== currentVideoId){
        currentVideoId = privateRoomData.id;
        loadRoomVideo(privateRoomData.id, privateRoomData.timestamp);
    }
    // Change state to match group state
    if(privateRoomData.currentState === PLAYER_STATE_UNSTARTED) {
        console.log('Video is Unstarted');
    } else if(privateRoomData.currentState === PLAYER_STATE_ENDED) {
        console.log('Video has Ended');
    } else if(privateRoomData.currentState === PLAYER_STATE_PLAYED){
        console.log('Video is Playing');
        youtubePlayer.playVideo();
    } else if(privateRoomData.currentState === PLAYER_STATE_PAUSED) {
        console.log('Video is Paused');
        youtubePlayer.pauseVideo();
    } else if(privateRoomData.currentState === PLAYER_STATE_BUFFERING) {
        console.log('Video is Buffering');
    } else if(privateRoomData.currentState === PLAYER_STATE_VIDEO_CUED) {
        console.log('Video is Cued');
    } else {
        console.log('Unknown State');
    }
}

function getCurrentVideo(){
    var currentVideoIndex = youtubePlayer.getPlaylistIndex();
    return playlistIds[currentVideoIndex];
}

// Send the user's state to the servlet every time their state changes
function updateCurrentState(currentState, currentTime){
    fetch(`/sync-room?roomId=${roomId.toString()}&userState=${currentState}&timeStamp=${currentTime}`,{method:'POST'})
}

/* Chat Room Feature */

// Shows and refreshes the messages shown on the private room page
async function displayChat() {   
    let response = await fetch(`/chat?roomId=${window.roomId}`);
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
