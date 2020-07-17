package com.google.sps.servlets;
import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ServletUtil {
    //Gson parser
    public static final Gson PARSER = new Gson();
    //API key for the Youtube Data API
    public static final String DATA_API_KEY = "AIzaSyA6NypfS8qTc_1O73fx2ZP6JwMZdB_FSas";
    //Base url for youtube embed videos
    public static final String YT_BASE_URL = "https://www.youtube.com/embed/";
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
