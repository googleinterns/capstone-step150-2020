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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.PrivateRoom;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that takes in the user's inputted Room ID and directs them
    to the associated private room once ensurred that it's an actual room*/
@WebServlet("/join-room")
public final class JoinRoomServlet extends HttpServlet {
  private String inputtedUserTag = "user-party-link";
  private String joinRoomPageLink = "/join-room";
  private String currentRoomID = "";
  private PrivateRoom userRoom;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Take in the current room ID
    currentRoomID = request.getParameter(inputtedUserTag);
    
    // TODO: Check if the Room is Valid
    Query query = new Query("PrivateRoom").addSort("ID", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()){
      // Only created a private room for the user that matches the given id
      if(currentRoomID.equals(entity.getProperty("id"))){
        long id = entity.getKey().getId();
        String url = (String) entity.getProperty("youtube-playlist-url");
        long roomStartTime = (long) entity.getProperty("room-start-time");
        userRoom = new PrivateRoom(id, url, roomStartTime);
      }
    }
    String json = new Gson().toJson(userRoom);
    response.setContentType("application/json;");
    response.getWriter().println(json);
    
    // Direct User to specific room
    response.sendRedirect("/" + currentRoomID);
  }
