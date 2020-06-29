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
  fetch('/join-room').then(response => response.json()).then((privateRoom) => {
    console.log(privateRoom);
    //postYoutubeUrl(privateRoom);
    //document.getElementById('quote-container').innerText = privateRoom;
  });
}

/**
* Take the Room ID and post it in the HTML
 */
function postYoutubeUrl(privateRoomJson){
  console.log('Post Youtube Url');
  const videoDisplayElement = document.getElementById('video-display');
  videoDisplayElement.innerText = privateRoomJson;
}

function onYouTubeIframeAPIReady() {
  player = new YT.Player('player', {
    height: '390',
    width: '640',
    videoId: 'M7lc1UVf-VE',
    events: {
      'onReady': onPlayerReady,
      'onStateChange': onPlayerStateChange
    }
  })
}
