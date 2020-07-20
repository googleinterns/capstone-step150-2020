var roomId;
var playlistUrls;
var playlistIds;
var youtubePlayer;
var YT_BASE_URL = "https://www.youtube.com/embed/";

// Calls the three functions associated with loading the room's iframe
async function loadPlayerDiv(){
    loadVideo();
    // Get the room id from the private room's url
    await getRoomId(window.location.href);
    await fetchPrivateRoomVideo(roomId);
    getRoomId_caseTested_expectedResult();
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
<<<<<<< HEAD
* Take in currentRoomId and return the Video Url associated with that room ID
=======
* Take in currentRoomId and create a global array of youtube video urls and 
* a global array of the video ids of the playlist
>>>>>>> 26367b8f2f7ebc6a4bc552c0d2fa4f27cada1ef5
* @param {currentRoomId} String Holds the room Id of the the user's room
* @return {roomVideoUrl} The Url of the video to be displayed for the room
 */
async function fetchPrivateRoomVideo(currentRoomId) {
<<<<<<< HEAD
    console.log("i am in the fetch private room function");
    // Check that the current room id exits, 
    // then return video url associated with that id
    let roomPromise = await fetch('/verify-room?roomId='+currentRoomId);
    let roomVideoUrl = await roomPromise.json();
    var arrayOfUrls = parseJsonOfVideos(roomVideoUrl);
    window.roomVideoUrl = arrayOfUrls[0];
    console.log("in the fetch function " + window.roomVideoUrl);
}

function parseJsonOfVideos(jsonOfVideos){
	var arrayOfUrls = [];
	for(i = 0; i < jsonOfVideos.length; i++) {
		arrayOfUrls.push(jsonOfVideos[i]);
	}
	return arrayOfUrls;
=======
    // Check that the current room id exits, then return playlist of given room
    let roomPromise = await fetch('/verify-room?roomId='+roomId);
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
>>>>>>> 26367b8f2f7ebc6a4bc552c0d2fa4f27cada1ef5
}

// This code loads the IFrame Player API code asynchronously.
function loadVideo(){
<<<<<<< HEAD
	var tag = document.createElement('script');
	tag.src = "https://www.youtube.com/iframe_api";
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}

// This function creates an <iframe> (and YouTube player)
// after the API code downloads.
function onYouTubeIframeAPIReady() {
	youtubePlayer = new YT.Player('player', {
		events: {
			'onReady': onPlayerReady,
			'onStateChange': onPlayerStateChange
		}
	});
=======
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
>>>>>>> 26367b8f2f7ebc6a4bc552c0d2fa4f27cada1ef5
}

// The API will call this function when the video player is ready.
function onPlayerReady(event) {
<<<<<<< HEAD
    document.getElementById('player').style.borderColor = '#FF6D00';
}

/**
* The API calls this function when the player's state changes.
* The function indicates that when playing a video (state=1),
* the player should play for six seconds and then stop.
*/ 
function onPlayerStateChange(event) {
	if (event.data == YT.PlayerState.PLAYING && !isVideoFinished) {
		setTimeout(stopVideo, 6000);
		isVideoFinished = true;
	}
=======
    document.getElementById('player-div').style.borderColor = '#FF6D00';
    loadRoomPlaylist();
}

function stopVideo() {
    youtubePlayer.stopVideo();
>>>>>>> 26367b8f2f7ebc6a4bc552c0d2fa4f27cada1ef5
}

// Log state changes
function onStateChange(event) {
    var state = "undefined";
    switch (event.data) {
        case YT.PlayerState.UNSTARTED:
            state = "unstarted";
            break;
        case YT.PlayerState.ENDED:
            state = "ended";
            break;
        case YT.PlayerState.PLAYING:
            state = "playing";
            break;
        case YT.PlayerState.PAUSED:
            state = "paused";
            break;
        case YT.PlayerState.BUFFERING:
            state = "buffering";
            break;
        case YT.PlayerState.CUED:
            state = "video cued";
            break;
        default:
            state = "unknown (" + event.data + ")";
    }
    console.log('onStateChange: ' + state);
}

// Shows and refreshes the messages shown on the private room page
async function displayChat() {   
    let response = await fetch(`/chat?roomID=${window.roomId}`);
    let messages = await response.json();    
    const messageElement = document.getElementById('chat-messages');    
    messageElement.innerHTML = '';    
    for (message in messages) {      
        messageElement.appendChild(createNewMessage(message));    
    }      
    displayChat();
}

// Inserts messages as HTML elements
function createNewMessage(msg) {    
    const listItem = document.createElement('li');    
    listItem.innerHTML += 
    `<div class="msgText">
        <p>
            <span class="sender">'+${msg.sender}+': </span><span class="msgBody">'+${msg.message}+'</span>
        </p> 
        <span class="sub-text"> Sent at ' + toTime(${msg.timestamp})+ '</span>
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

async function displayChat() {
    let response = await fetch('/chat');
    let messages = await response.json();
    const messageElement = document.getElementById('chat-messages');
    messageElement.innerHTML = '';
    for (message in messages) {
        commentElement.appendChild(createParagraph(comments[comment]));
    }  
}

function createParagraph(msgJson) {
    var msg = JSON.parse(msgJson);
    const paragraph = document.createElement('p');
    paragraph.innerHTML = '<h4>';
    paragraph.innerText = msg.sender;
    paragraph.innerHTML = '</4>';
    paragraph.innerText = msg.message;
    return paragraph;
}
