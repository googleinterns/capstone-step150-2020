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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Key;

/** Servlet that takes in the user's room Id and verifies that it
 * exists, then prints the url associated with the verified room id
*/
@WebServlet("/verify-room")
public final class VerifyRoomServlet extends HttpServlet {
  private String inputtedUserTag = "user-party-link";
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // If the room Id provided is in the privateRooms hashmap, print that url
    // if not print a hardcoded dummy youtube video
    response.setContentType("application/json");
		
    String currentRoomId = request.getParameter("roomId");
		Key currentRoomKey = getKeyFromString(currentRoomId);
		Room currentRoom = Room.fromKey(currentRoomKey);

    // If the user sent in a room id not in the datastore, send them to a dummy room
    // TODO: Redirect to a specific page telling the client that they inputted the wrong room id
    if(currentRoom == null){
      String jsonOfTempUrl = new Gson().toJson("https://www.youtube.com/embed/Bey4XXJAqS8");
      response.getWriter().println(jsonOfTempUrl);
    } else {
			Queue<Video> videosOfPlaylist = currentRoom.getVideos();
			ArrayList<String> urlsOfPlaylist = extractVideoUrls(videosOfPlaylist);
			String jsonOfUrls = new Gson().toJson(urlsOfPlaylist);
			response.getWriter().println(jsonOfUrls);
    }
	}

	// Finds the key from the datastore using the String of the ID
	public Key getKeyFromString(String roomId){
		Query query = new Query(Room.ROOM_ENTITY);
		PreparedQuery results = datastore.prepare(query);
		for(Entity currentRoomEntity : results.asIterable()) {
			Key currentRoomKey = currentRoomEntity.getKey();
			System.out.println("The temp currentRoomKey is " + currentRoomKey.toString());
			String parsedRoomKey = currentRoomKey.toString().substring(5,currentRoomKey.toString().length() - 1);
			System.out.println("parsedRoomKey is " + parsedRoomKey);
			if(roomId.equals(parsedRoomKey)){
				return currentRoomKey;
			}
		}
		return null;
	}

	/*
	* Take the queue of videos associated with the room and transfer it into an array
	*/
	public ArrayList<String> extractVideoUrls(Queue<Video> videosOfPlaylist){
		ArrayList<String> videoUrls = new ArrayList<>();
		while(!videosOfPlaylist.isEmpty()){
			Video currVideo = videosOfPlaylist.remove();
			videoUrls.add(currVideo.getUrl());
		}
		return videoUrls;
	}
}
