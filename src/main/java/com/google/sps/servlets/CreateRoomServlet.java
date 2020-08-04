package com.google.sps.servlets;

import com.google.sps.data.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import java.util.Queue;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.JsonArray;
import java.util.Arrays;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.QueryResultList;
import java.util.stream.Collectors;

//Tested by end to end testing the creation of the new Room. 
//This was successful and the room Id was returned to the user

//Servlet that handles the creation of a new Room
@WebServlet("/create-room")
public final class CreateRoomServlet extends HttpServlet {
    private static final String INVITEES_PARAMETER = "Invitees";
    private static final String PLAYLIST_URL_PARAMETER = "PlaylistUrl";
    private static final String ERROR_HTML = "<h1>An error occured while creating you room.</h1>";

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String emails = req.getParameter(INVITEES_PARAMETER).replace(" ", "");
        List members = Arrays.asList(emails.split(",")).stream().map(Member::createNewMember).collect(Collectors.toList());
        //Requests playlist information from YT api and transforms playlist url to video url list
        String playlistUrl = req.getParameter(PLAYLIST_URL_PARAMETER);
        String playlistId = playlistUrl.substring(playlistUrl.indexOf(ServletUtil.PLAYLIST_QUERY_PARAMETER)+ServletUtil.PLAYLIST_QUERY_PARAMETER.length());

        Room newRoom = new Room(members);
        playlistIdToVideoQueue(playlistId, newRoom);
        Long newRoomId = newRoom.toDatastore();
        if(newRoomId != null) {
           res.setContentType("text/html");
           res.getWriter().println(createHtmlString(newRoomId));
        } 
        else {
            res.setContentType("text/html");
            res.getWriter().println(ERROR_HTML);
        }
    }
    
    /**
      * Communicates with the Youtube Data API to get playlistItem information
      * @param playlistId the string representing the playlistId
      * @return an arraylist of video Urls (limit is 15)
      */
    public void playlistIdToVideoQueue(String playlistId, Room room) throws IOException {
        //Connect to the YouTube Data API
        URL url = new URL(ServletUtil.YT_DATA_API_BASE_URL+System.getenv("DATA_API_KEY")+ServletUtil.YT_DATA_API_PARAMETERS+playlistId);
        HttpURLConnection youtubeDataConnection = (HttpURLConnection) url.openConnection();
        youtubeDataConnection.setRequestMethod("GET");
        //Read the response
        InputStreamReader responseReader = new InputStreamReader(youtubeDataConnection.getInputStream(), "UTF-8");
        JsonObject response = ServletUtil.PARSER.fromJson(responseReader, JsonObject.class);
        responseReader.close();
        JsonArray videoInformation = response.getAsJsonArray("items");
        //Create urls from video IDs
        for(int i = 0; i < videoInformation.size(); ++i) {
            String videoId = videoInformation.get(i).getAsJsonObject().getAsJsonObject("contentDetails").get("videoId").getAsString();
            if(!room.addVideo(Video.createVideo(videoId))){
                break;
            }
        }
    }
    //Returns the HTML string for with the new Room ID
    public String createHtmlString(Long key) {
        return "<center><h2>Congratulations! This is your new Room ID.</h2><br><br><h1>"+key+"</h1></center>";
    }
}
