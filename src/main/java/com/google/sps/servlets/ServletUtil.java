package com.google.sps.servlets;
import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

// Util File for Servlet that defines the paths and other strings
public class ServletUtil {
    //Path to the private room page
    public static final String PRIVATE_ROOM_PATH = "/views/private-room.html";
    //Path to the join room page
    public static final String JOIN_ROOM_PATH = "/views/join-room.html";
    //Path to private room with query for room ID
    public static final String PRIVATE_ROOM_PATH_WITH_QUERY = "/views/private-room.html?roomId=";
    //Path to redirect page with query for room ID
    public static final String REDIRECT_ROOM_PATH_WITH_QUERY = "/views/redirect-page.html?id=";
    //The query title for the room id
    public static String INPUTTED_ID_TAG = "roomId";
    //Title of the Room Entity in the datastore
    public static final String ROOM_ENTITY = "Room";
    //Gson parser
    public static final Gson PARSER = new Gson();
    //The query parameter for the Youtube playlist id
    public static final String PLAYLIST_QUERY_PARAMETER = "list=";
    //String reresenting the json content type
    public static final String JSON_CONTENT_TYPE = "application/json";
    //Appengine Uerservice instance
    public static final UserService USER_SERVICE = UserServiceFactory.getUserService();
    //String representing the html content type
    public static final String HTML_CONTENT_TYPE = "text/html";
    //String representing the base Url youtube data api endpoint
    public static final String YT_DATA_API_BASE_URL = "https://www.googleapis.com/youtube/v3/playlistItems?key=";
    //String representing the parameters of the Youtube data api endpoint
    public static final String YT_DATA_API_PARAMETERS = "&part=contentDetails&playlistId=";
}
