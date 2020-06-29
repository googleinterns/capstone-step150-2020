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
async function fetchPrivateRoom() {
  console.log('Fetching private room.');

  fetch('/join-room').then(response => response.json()).then((privateRoom) => {
    console.log(privateRoom);
    console.log(privateRoom.currentRoomID);
    //console.log(privateRoom.valueOf(privateRoom.currentRoomID));
    window.roomId = privateRoom.currentRoomID;
    redirectToRoom();
  });
    /*let response = await fetch('/join-room');
    let json = await response.json();
	console.log(json);
    console.log(window.roomId);*/
}

function redirectToRoom() {
    console.log('Now i am in the redirectToRoom function');
    console.log(window.roomId);
}

// 2. This code loads the IFrame Player API code asynchronously.
  //var tag = document.createElement('script');

  //tag.src = "https://www.youtube.com/iframe_api";
  //var firstScriptTag = document.getElementsByTagName('script')[0];
  //firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
