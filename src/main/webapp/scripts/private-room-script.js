/*** Calls the three functions associated with loading the room's iframe */
async function loadIframe(){    
    // Get the room id from the private room's url    
    await getRoomId(window.location.href);    
    await fetchPrivateRoomVideo(window.roomId);    
    var iframeTag = document.getElementById("video-tag");    
    iframeTag.src = window.roomVideoUrl;
}

/*** Get the room id by parsing the room's url* 
@param {url} String url of the current webpage* 
@return {roomId} room id at end of the url*/
function getRoomId(url) {    
    var parser = document.createElement('a');    
    parser.href = url;    
    var query = parser.search.substring(1);    
    // Create a temp array of len=2,     
    // holds 'roomId=' in [0] and room id in [1]    
    var tempArrayForRoomId = query.split('=');    
    var roomId = tempArrayForRoomId[1];    
    window.roomId = roomId;    
    return roomId;
}

/*** Take in currentRoomId and return the Video Url associated with that room ID* 
@param {currentRoomId} String Holds the room Id of the the user's room* 
@return {roomVideoUrl} The Url of the video to be displayed for the room */
async function fetchPrivateRoomVideo(currentRoomId) {    
    console.log("i am in the fetch private room function");    
    // Check that the current room id exits,     
    // then return video url associated with that id    
    let roomPromise = await fetch('/verify-room?roomId='+currentRoomId);    
    let roomVideoUrl = await roomPromise.json();    
    var arrayOfUrls = parseJsonOfVideos(roomVideoUrl);    
    window.roomVideoUrl = arrayOfUrls[0];    
    console.log("in the fetch function " + window.roomVideoUrl);
}

function parseJsonOfVideos(jsonOfVideos){    
    var arrayOfUrls = [];    
    for(i = 0; i < jsonOfVideos.length; i++) {        
        arrayOfUrls.push(jsonOfVideos[i]);    
    }    
    return arrayOfUrls;
}

// This code loads the IFrame Player API code asynchronously.
function loadVideo(){    
    var tag = document.createElement('script');    
    tag.src = "https://www.youtube.com/iframe_api";    
    var firstScriptTag = document.getElementsByTagName('script')[0];    
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}

// This function creates an <iframe> (and YouTube player)
// after the API code downloads.
var player;
function onYouTubeIframeAPIReady() {    
    player = new YT.Player('player', {        
        events: {            
            'onReady': onPlayerReady,            
            'onStateChange': onPlayerStateChange        
        }    
    });
}

// The API will call this function when the video player is ready.
function onPlayerReady(event) {    
    document.getElementById('player').style.borderColor = '#FF6D00';
}

/*** The API calls this function when the player's state changes.
* The function indicates that when playing a video (state=1),
* the player should play for six seconds and then stop.*/ 
var done = false;
function onPlayerStateChange(event) {    
    if (event.data == YT.PlayerState.PLAYING && !done) {        
        setTimeout(stopVideo, 6000);        
        done = true;    
    }
}

function stopVideo() {    
    player.stopVideo();
}

// Shows and refreshes the messages shown on the private room page
async function displayChat() {    
    let response = await fetch(`/chat?roomID=${window.roomId}`);
    let messages = await response.json();    
    const messageElement = document.getElementById('chat-messages');    
    messageElement.innerHTML = '';    
    for (message in messages) {      
        messageElement.appendChild(createNewMessage(messages[message]));    
    }      
    displayChat();
}

// Inserts messages as HTML elements
function createNewMessage(msg) {    
    const listItem = document.createElement('li');    
    listItem.innerHTML += '<div class="msgText"><p><span class="sender">'+msg.sender+': </span><span class="msgBody">'+msg.message+'</span></p> <span class="sub-text"> Sent at ' + toTime(msg.timestamp)+ '</span></div>';    
    return listItem;
}

// Manipulates timestamp value to be displayed in hh:mm(+AM/PM) format 
function toTime(ms) {    
    // Adds '0' if necessary for single digit values    
    function checkZero(n) {        
        return (n<10 ? '0':'') + n;    
    }    
    var date = new Date(ms);    
    var hrs = date.getHours();    
    var mins = date.getMinutes();    
    var ampm = hrs >= 12 ? 'PM' : 'AM';    
    hrs = hrs % 12;    
    hrs = hrs ? hrs : 12;    
    var time = checkZero(hrs) + ':' + checkZero(mins)+ ' ' + ampm;    
    return time;
}
