<<<<<<< HEAD
/**
* Calls the three functions associated with loading the room's iframe
 */
async function loadIframe(){
	// Get the room id from the private room's url
	await getRoomId(window.location.href);
	await fetchPrivateRoomVideo(window.roomId);
	var iframeTag = document.getElementById("video-tag");
	iframeTag.src = window.roomVideoUrl;
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
	var roomId = tempArrayForRoomId[1];
	window.roomId = roomId;
    return roomId;
}

/**
* Take in currentRoomId and return the Video Url associated with that room ID
* @param {currentRoomId} String Holds the room Id of the the user's room
* @return {roomVideoUrl} The Url of the video to be displayed for the room
 */
async function fetchPrivateRoomVideo(currentRoomId) {
	console.log("i am in the fetch private room function");
    // Check that the current room id exits, 
    // then return video url associated with that id
    let roomPromise = await fetch('/verify-room?roomId='+currentRoomId);
    let roomVideoUrl = await roomPromise.json();
	window.roomVideoUrl = roomVideoUrl;
	console.log("in the fetch function" + window.roomVideoUrl);
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
var player;
function onYouTubeIframeAPIReady() {
	player = new YT.Player('player', {
=======
// This code loads the IFrame Player API code asynchronously.
var scriptTag = document.createElement('script');
scriptTag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
// Boolean used to handle statechange function
var isVideoFinished = false;
var youtubePlayer;

/**
 * Fetches prvivate room and prints the room to console
 */
function fetchPrivateRoom() {
  console.log('Fetching private room.');
  // TODO: fetch hard-coded private room
}

/**
* This function creates an <iframe> (and YouTube player)
* after the API code downloads.
* TODO: Make videoId mutable
*/
function onYouTubeIframeAPIReady() {
	youtubePlayer = new YT.Player('player', {
		height: '390',
		width: '640',
		videoId: 'M7lc1UVf-VE',
>>>>>>> 1350517c522ff56cc3081af8fd6914f1d5f5a90a
		events: {
			'onReady': onPlayerReady,
			'onStateChange': onPlayerStateChange
		}
	});
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
var done = false;
function onPlayerStateChange(event) {
	if (event.data == YT.PlayerState.PLAYING && !done) {
		setTimeout(stopVideo, 6000);
		done = true;
=======
	event.target.playVideo();
}

// The API calls this function when the player's state changes.
// The function indicates that when playing a video (state=1),
// the player should play for six seconds and then stop.
function onPlayerStateChange(event) {
	if (event.data == YT.PlayerState.PLAYING && !isVideoFinished) {
		setTimeout(stopVideo, 6000);
		isVideoFinished = true;
>>>>>>> 1350517c522ff56cc3081af8fd6914f1d5f5a90a
	}
}

function stopVideo() {
<<<<<<< HEAD
	player.stopVideo();
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
    paragraph.innerHTML = '</h4>';
    paragraph.innerText = msg.message;
    return paragraph;
}
=======
	youtubePlayer.stopVideo();
}
>>>>>>> 1350517c522ff56cc3081af8fd6914f1d5f5a90a
