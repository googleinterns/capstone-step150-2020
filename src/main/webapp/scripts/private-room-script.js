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
            <span class="sender">'+msg.sender+': </span><span class="msgBody">'+msg.message+'</span>
        </p> 
        <span class="sub-text"> Sent at ' + toTime(msg.timestamp)+ '</span>
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
