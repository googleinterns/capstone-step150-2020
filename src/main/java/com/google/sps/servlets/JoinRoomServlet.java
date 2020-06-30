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

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that takes in the user's inputted Room ID and directs them
    to the associated private room once ensurred that it's an actual room*/
@WebServlet("/join-room")
public final class JoinRoomServlet extends HttpServlet {
  private String inputtedUserTag = "user-party-link";
  private String currentRoomId = "";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HashMap<String, String> privateRooms = new HashMap<String, String>();
    privateRooms.put("currentRoomID", currentRoomId);
    privateRooms.put("234532", "https://www.youtube.com/embed?v=a9HIaGcBocc");
    privateRooms.put("4822654", "https://www.youtube.com/embed?v=Bc9Y58TeZk0");
    String json = new Gson().toJson(privateRooms);

		// Print hard-coded map of private room's and their url to the writer
    response.setContentType("application/json");
    response.getWriter().println(json);
  }

	@Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Take in user's private room and store it to global variable
		currentRoomId = request.getParameter(inputtedUserTag);
		response.sendRedirect("/views/private-room.html");
  }
}