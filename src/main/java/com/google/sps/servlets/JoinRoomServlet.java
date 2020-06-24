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

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that both sends userComments to the client and receives + stores inputed User Comments*/
@WebServlet("/join-room")
public final class JoinRoomServlet extends HttpServlet {
  private String inputtedUserTag = "user-party-link";
  private String joinRoomPageLink = "/join-room";
  private String currentRoomID = "";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");
    // TODO: Receive the room URL from Database and print 
    // it in JSON for User to receive
  }

  /* Receive Any New Inputed Comments from User */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //Take in the inputted ID
    currentRoomID = request.getParameter(inputtedUserTag);
    response.sendRedirect(joinRoomPageLink);
  }
}
