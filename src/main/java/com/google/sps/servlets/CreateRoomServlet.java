package com.google.sps.servlet;

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

@Webservlet("/create-room")
public final class CreateRoomServlet extends HttpServlet{

    public doPost(HttpServletRequest req, HttpServletResponse res) {
        JsonObject roomJson = PARSER.fromjson(req.body,JsonObject.class);
        String playlistUrl =  roomJson.get("playlistUrl");

        //TODO(rossjohnson): implement playlist url to video url list
        String playlistID = playlistUrl.substring(playlistUrl.indexOf("list=")+5);
        URL url = new URL("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+playlistID);
        HttpURLConnection YTDataCon = (HttpURLConnection) url.openConnection();
        YTDataCon.setRequestMethod("POST");
        Room newRoom = Room(roomJson,);

    }
}