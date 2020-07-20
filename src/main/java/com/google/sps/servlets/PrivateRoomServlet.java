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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD:src/main/java/com/google/sps/servlets/PrivateRoomServlet.java
/** Servlet that takes in inputted room Id and directs them to that room
*/
@WebServlet("/private-room")
public final class PrivateRoomServlet extends HttpServlet {
  private String inputtedIdTag = "user-party-link";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String currentRoomId = request.getParameter(inputtedIdTag);
    response.sendRedirect("/views/private-room.html?id="+currentRoomId);
=======
/** Servlet that takes in the user's inputted Room ID and directs them
    to the associated private room once ensurred that it's an actual room*/
@WebServlet("/join-room")
public final class JoinRoomServlet extends HttpServlet {
  private String currentRoomId = "";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Take in user's private room and store it to global variable
	currentRoomId = request.getParameter(ServletUtil.INPUTTED_ID_TAG);
	response.getWriter().println(currentRoomId);
    // TODO: Create hard-coded hashmap of {Room ID : URL} Hashmap
    // TODO: Print json-ified string to /join-room page for private room to fetch
	response.sendRedirect(ServletUtil.PRIVATE_ROOM_PATH_WITH_QUERY+currentRoomId);
>>>>>>> 26367b8f2f7ebc6a4bc552c0d2fa4f27cada1ef5:src/main/java/com/google/sps/servlets/JoinRoomServlet.java
  }
}
