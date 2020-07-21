// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.sps.data.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Key;

/** Servlet that takes in the user's room Id and verifies that it
 * exists, then prints the json version of all the urls in playlist
*/
@WebServlet("/collect-videos")
public final class CollectVideosServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
		
    String tempStringOfRoomId = request.getParameter(ServletUtil.INPUTTED_ID_TAG);
    long currentRoomId = Long.parseLong(tempStringOfRoomId);
    // Already verified that the key is in datastore
    Room currentRoom = Room.fromRoomId(currentRoomId);

    // TODO: Redirect to a specific page telling the client that they inputted the wrong room id
    Queue<Video> videosOfPlaylist = currentRoom.getVideos();
    List<String> urlsOfPlaylist = extractVideoUrls(videosOfPlaylist);
    String jsonOfUrls = new Gson().toJson(urlsOfPlaylist);
    response.getWriter().println(jsonOfUrls);
  }

  /*
  * Take the queue of videos associated with the room and transfer it into a list
  */
  public List<String> extractVideoUrls(Queue<Video> videosOfPlaylist){
    return videosOfPlaylist.stream().map(Video::getUrl).collect(Collectors.toList());
  }
}
