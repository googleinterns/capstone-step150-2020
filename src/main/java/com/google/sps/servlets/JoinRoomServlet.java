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
import java.io.IOException;
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

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("PrivateRoom").addSort("ID", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<PrivateRoom> privateRooms = new ArrayList<>();
    for (Entity entity : results.asIterable()){
        long id = entity.getKey().getId();
        String url = (String) entity.getProperty("youtubePlaylistUrl");
        long timestamp = (long) entity.getProperty("timestamp");

        PrivateRoom privateRoom = new PrivateRoom(id, url, timestamp);
        privateRooms.add(privateRoom);
    }

    String json = new Gson().toJson(privateRooms);
    
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  /* Receive Any New Inputed Comments from User */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //Take in the inputted ID
    currentRoomID = request.getParameter(inputtedUserTag);
    //TODO: Check if currentRoomId is a valid ID
    // if yes, direct to room page, if no, redirect back to join room page
    response.sendRedirect(joinRoomPageLink);
  }
}
