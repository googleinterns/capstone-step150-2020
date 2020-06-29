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
        String playlistID = playlistUrl.substring(playlistUrl.indexOf(ServletUtil.PLAYLIST_QUERY_PARAMETER)+5);
        URL url = new URL("https://www.googleapis.com/youtube/v3/playlistItems?key="+ServletUtil.DATA_API_KEY+"&part=contentDetails&playlistId="+playlistID);
        HttpURLConnection YTDataCon = (HttpURLConnection) url.openConnection();
        YTDataCon.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(YTDataCon.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        JsonObject obj = ServletUtil.PARSER.fromJson(content.toString(), JsonObject.class);
        JsonArray inforay = obj.getAsJsonArray("items");
        ArrayList<String> videoUrls =  new ArrayList<String>();
        for(int i = 0; i < inforay.size() && i < 15; ++i ) {
            String videoid = inforay.get(i).getAsJsonObject().getAsJsonObject("contentDetails").get("videoId").getAsString();
            videoUrls.add(ServletUtil.YT_BASE_URL + videoid);
        }
        
        Room newRoom = new Room(members, videoUrls);
    }
}