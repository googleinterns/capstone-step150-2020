var roomId;
var playlistUrls;
var playlistIds;
var player;

/**
* Calls the three functions associated with loading the room's iframe
 */
async function loadPlayerDiv(){
    loadVideo();
	// Get the room id from the private room's url
	await getRoomId(window.location.href);
	await fetchPrivateRoomVideo(roomId);
	//var iframeTag = document.getElementById("video-tag");
	//iframeTag.src = window.roomVideoUrl;
    //loadRoomPlaylist();
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
    let roomPromise = await fetch('/verify-room?roomId='+currentRoomId);
    // fetch the json-version of the urls for all the youtube videos
    let roomVideoUrl = await roomPromise.json();
    // create an array of all the YT videos' urls
    playlistUrls = parseJsonOfVideos(roomVideoUrl);
    // window.roomVideoUrl = playlistUrls[0];
    playlistIds = extractVideoIds(playlistUrls);
}

function parseJsonOfVideos(jsonOfVideos){
	var playlistUrls = [];
	for(i = 0; i < jsonOfVideos.length; i++) {
		playlistUrls.push(jsonOfVideos[i]);
	}
	return playlistUrls;
}

var YT_BASE_URL = "https://www.youtube.com/embed/";
function extractVideoIds(playlistUrls){
    var currentPlaylistIds = [];
    for(i = 0; i < playlistUrls.length; i++) {
        var currentUrl = playlistUrls[i];
        var currentRoomId = currentUrl.substring(YT_BASE_URL.length);
        currentPlaylistIds.push(currentRoomId);
    }
    return currentPlaylistIds;
}

function loadRoomPlaylist(){
    player.loadPlaylist({playlist: playlistIds});
}

// This code loads the IFrame Player API code asynchronously.
function loadVideo(){
	var tag = document.createElement('script');
	tag.src = "https://www.youtube.com/iframe_api";
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}

// This function creates an <iframe> (and YouTube player)
// after the API code downloads.
function onYouTubeIframeAPIReady() {
    player = new YT.Player('player-div', 
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

// The API will call this function when the video player is ready.
function onPlayerReady(event) {
    document.getElementById('player-div').style.borderColor = '#FF6D00';
    loadRoomPlaylist();
}

function stopVideo() {
	player.stopVideo();
}

// Log state changes
function onStateChange(event) {
    var state = "undefiend";
    switch (event.data) {
        case YT.PlayerState.UNSTARTED:
            state= "unstarted";
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
