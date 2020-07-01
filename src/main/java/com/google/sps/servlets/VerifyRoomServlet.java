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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that takes in the user's room Id and verifies that it
 * exists, then redirects you to that room
*/
@WebServlet("/verify-room")
public final class VerifyRoomServlet extends HttpServlet {
  private String inputtedUserTag = "user-party-link";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String currentRoomId = request.getParameter(inputtedUserTag);
    // TODO: If the currentRoomId is included in the datastore redirect them to the private room page
    if(true){
      response.getWriter().println("valid URL");
    } else {
      response.getWriter().println("not valid url");
    }
  }
}
