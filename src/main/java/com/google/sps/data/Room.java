package com.google.sps.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Room {

    private ArrayList<String> members;
    private LinkedList<Message> messages;
    private ArrayList<String> video_urls;
    private RoomState state;

    /**
      * Room constructor
      * @param obj a JsonObject representing the configurations the Room creator specified
      * @param urls an arraylist of the video urls of the initial Room playlist
      * @return a new Room object
      */
    public Room(JsonObject obj, ArrayList<String> urls){
        this.messages = new LinkedList<Message>();
        this.state = new RoomState();
        this.members = DATAUTIL.PARSER.fromJson(obj.getAsJsonArray("members"),ArrayList<String>.class);
        this.video_urls = urls;
    }
    
    //Function that transforms a Room to a HashMap
    public HashMap<String, Object> toMap() {
        HashMap<String,Object> ret = new HashMap<String,Object>();
        ret.put("video_state", this.state.currentState);
        ret.put("video_timestamp", this.state.videoTimestamp);
        ret.put("members", this.members);
        ret.put("video_urls", this.video_urls);
        ret.put("current_video", this.state.currentVideo);
        ret.put("message_count", this.state.messageCount);
        ret.put("messages", this.messages);
        return ret;
    }
    //Returns the Room's members
    public LinkedList<String> getMembers() {
        return this.members;
    }
    //Returns the Room's video url list
    public ArrayList<String> getUrls() {
        return this.video_urls;
    }
    //Returns the Room's message list
    public ArrayList<Message> getMessages(){
        return this.messages;
    }
}
