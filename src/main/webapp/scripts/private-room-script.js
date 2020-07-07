/**
 * Fetches prvivate room and prints the room to console
 */
function fetchPrivateRoom() {
  console.log('Fetching private room.');
  // TODO: fetch hard-coded private room
}

// This code loads the IFrame Player API code asynchronously.
var scriptTag = document.createElement('script');

scriptTag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

/**
* This function creates an <iframe> (and YouTube player)
* after the API code downloads.
* TODO: Make videoId mutable
*/
var player;
function onYouTubeIframeAPIReady() {
	player = new YT.Player('player', {
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
var videoFinished = false;
function onPlayerStateChange(event) {
	if (event.data == YT.PlayerState.PLAYING && !videoFinished) {
		setTimeout(stopVideo, 6000);
		videoFinished = true;
	}
}

function stopVideo() {
	player.stopVideo();
}
