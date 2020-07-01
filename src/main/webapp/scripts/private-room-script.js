// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Fetches prvivate room and prints the room to console
 */
function fetchPrivateRoom() {
  console.log('Fetching private room.');
  fetch('/verify-room').then(response => response.json()).then((privateRoom) => {
		console.log(privateRoom);
    window.roomUrl = privateRoom;
    console.log('In the fetch function');
		console.log('The room url is ' + window.roomUrl);
		loadIframe();
  });
}

function loadIframe(){
	console.log('Inside iframe function');
	var iframeTag = document.getElementById("video-tag");
	iframeTag.src = window.roomUrl;
	loadVideo();
}
//2. This code loads the IFrame Player API code asynchronously.
function loadVideo(){
	var tag = document.createElement('script');

	console.log('About to append the src');
	tag.src = "https://www.youtube.com/iframe_api";
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}

// 3. This function creates an <iframe> (and YouTube player)
//    after the API code downloads.
var player;
function onYouTubeIframeAPIReady() {
	player = new YT.Player('video-tag', {
		events: {
			'onReady': onPlayerReady,
			'onStateChange': onPlayerStateChange
		}
	});
}

// 4. The API will call this function when the video player is ready.
function onPlayerReady(event) {
    document.getElementById('video-tag').style.borderColor = '#FF6D00';
  }

// 5. The API calls this function when the player's state changes.
//    The function indicates that when playing a video (state=1),
//    the player should play for six seconds and then stop.
var done = false;
function onPlayerStateChange(event) {
	if (event.data == YT.PlayerState.PLAYING && !done) {
		setTimeout(stopVideo, 6000);
		done = true;
	}
}
function stopVideo() {
	player.stopVideo();
}
