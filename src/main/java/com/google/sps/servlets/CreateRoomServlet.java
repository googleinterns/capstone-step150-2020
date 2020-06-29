package com.google.sps.servlets;

import com.google.sps.data.Room;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.sps.data.PrivateRoom;
import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet("/create-room")
public final class CreateRoomServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String emails = req.getParameter("Invitees").replace(" ", "");
        List<String> members = new ArrayList<String>();
        members = Arrays.asList(emails.split(","));

        //Requests playlist information from YT api and transforms playlist url to video url list
        String playlistUrl = req.getParameter("PlaylistUrl");
        String playlistId = playlistUrl.substring(playlistUrl.indexOf(ServletUtil.PLAYLIST_QUERY_PARAMETER)+5);
        
        ArrayList<String> videoUrls = playlistIdToUrls(playlistId);
        Room newRoom = new Room(members, videoUrls);
        
        //TODO(rossjohnson): Find an efficient way to store this in datastore since it has collections of objects in it
    }
    /**
      * Communicates with the Youtube Data API to get playlistItem information
      * @param playlistId the string representing the playlistId
      * @return an arraylist of video Urls (limit is 15)
      */
    ArrayList<String> playlistIdToUrls(String playlistId){
        //Connect to the YouTube Data API
        URL url = new URL("https://www.googleapis.com/youtube/v3/playlistItems?key="+ServletUtil.DATA_API_KEY+"&part=contentDetails&playlistId="+playlistID);
        HttpURLConnection YTDataCon = (HttpURLConnection) url.openConnection();
        YTDataCon.setRequestMethod("GET");
        //Read the response
        BufferedReader in = new BufferedReader(
        new InputStreamReader(YTDataCon.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        //Parse json for the specific data that is necessary
        JsonObject obj = ServletUtil.PARSER.fromJson(content.toString(), JsonObject.class);
        JsonArray VideoInformation = obj.getAsJsonArray("items");
        ArrayList<String> videoUrls =  new ArrayList<String>();
        //Create urls from video IDs
        for(int i = 0; i < VideoInformation.size() && i < 15; ++i ) {
            String videoid = VideoInformation.get(i).getAsJsonObject().getAsJsonObject("contentDetails").get("videoId").getAsString();
            videoUrls.add(ServletUtil.YT_BASE_URL + videoid);
        }
        return videoUrls
    }
}