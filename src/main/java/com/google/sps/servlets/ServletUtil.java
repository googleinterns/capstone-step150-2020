package com.google.sps.servlets;
import com.google.gson.Gson;

public class ServletUtil {
    public static final Gson PARSER = new Gson();
    public static final enum video_state = {UNSTARTED(-1), ENDED(0), PLAYING(1), PAUSED(2), BUFFERING(3), CUED(5)};
    public static final  String DATA_API_KEY = "AIzaSyA6NypfS8qTc_1O73fx2ZP6JwMZdB_FSas";
}