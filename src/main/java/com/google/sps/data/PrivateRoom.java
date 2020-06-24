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

package com.google.sps.data;

/** An item on a todo list. */
public final class PrivateRoom {

  private final long id;
  private final String youtubePlaylistUrl;
  private final long roomStartTime;
  private final ArrayList<String> members; 

  public PrivateRoom(long id, String youtubePlaylistUrl, long roomStartTime, ArrayList<String> members) {
    this.id = id;
    this.youtubePlaylistUrl = youtubePlaylistUrl;
    this.roomStartTime = roomStartTime;
    this.members = members;
  }

  public long getId(){
    return id;
  }

  public void setId(long id){
    this.id = id;
  }

  public String getUrl(){
    return youtubePlaylistUrl;
  }

  public void setUrl(String url){
    this.youtubePlaylistUrl = url;
  }

  public ArrayList<String> getMembers(){
    return members;
  }
}
