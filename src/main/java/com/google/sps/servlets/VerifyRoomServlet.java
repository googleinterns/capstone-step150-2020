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

/** 
  Servlet that takes in the user's room Id and prints whether or not it exists
  If so, it also checks if the user that is trying to join is on the Room's member list
*/
@WebServlet("/verify-room")
public final class VerifyRoomServlet extends HttpServlet {
  private static final String USER_PARAMETER =  "userEmail";
  private static final int STRINGS_EQUAL = 0;
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    String user = request.getParameter(USER_PARAMETER);
    String tempStringOfRoomId = request.getParameter(ServletUtil.INPUTTED_ID_TAG);
    long currentRoomId = Long.parseLong(tempStringOfRoomId);
    Room currentRoom = Room.fromRoomId(currentRoomId);
    
    response.getWriter().println(currentRoom != null /*&& isUserOnMemberList(user, currentRoom)*/);

  }

  /**
   * Determines if the user trying to join the Room is on the Room's member list
   * @param user a string representing the email of the user that is trying to join
   * @param room the Room that the user is trying to join
   * @return a boolean 
  */
  public static Boolean isUserOnMemberList(String user, Room room){
    return room.getMembers().stream().anyMatch(member -> member.getAlias().equalsIgnoreCase(user));
  }
}
