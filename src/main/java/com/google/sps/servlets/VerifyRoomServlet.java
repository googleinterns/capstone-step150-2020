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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that takes in the user's room Id and verifies that it
 * exists, then prints the json version of all the urls in playlist
*/
@WebServlet("/verify-room")
public final class VerifyRoomServlet extends HttpServlet {
  public static int ERROR_CODE_FOUND = 404;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    String tempStringOfRoomId = request.getParameter(ServletUtil.INPUTTED_ID_TAG);
    long currentRoomId = Long.parseLong(tempStringOfRoomId);
    // TODO: handle if they inputted a key string that does not exist in datastore
    Room currentRoom = Room.fromRoomId(currentRoomId);
    // If the user sent in a room id not in the datastore, send them a hardcoded youtube video
    // TODO: Redirect to a specific page telling the client that they inputted the wrong room id
    if(currentRoom == null){
      System.out.println("VerifyRoomServlet could not find a corresponding room");
      response.setStatus(ERROR_CODE_FOUND);
      response.sendRedirect(ServletUtil.JOIN_ROOM_PATH);
      // Set type to HTML
    } else {
      Queue<Video> videosOfPlaylist = currentRoom.getVideos();
      List<String> urlsOfPlaylist = currentRoom.getVideos().stream().map(Video::getUrl).collect(Collectors.toList());
      String jsonOfUrls = new Gson().toJson(urlsOfPlaylist);
      response.getWriter().println(jsonOfUrls);
    }
  }
}
