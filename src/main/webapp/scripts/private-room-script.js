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
		events: {
			'onReady': onPlayerReady,
			'onStateChange': onPlayerStateChange
		}
	});
}

// The API will call this function when the video player is ready.
function onPlayerReady(event) {
	event.target.playVideo();
}

// The API calls this function when the player's state changes.
// The function indicates that when playing a video (state=1),
// the player should play for six seconds and then stop.
function onPlayerStateChange(event) {
	if (event.data == YT.PlayerState.PLAYING && !isVideoFinished) {
		setTimeout(stopVideo, 6000);
		isVideoFinished = true;
	}
}

function stopVideo() {
	youtubePlayer.stopVideo();
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
