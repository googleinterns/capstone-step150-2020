package com.google.sps.data;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class Room {
    private ArrayList<String> members;
    private LinkedList<Message> messages;
    private ArrayList<String> videoUrls;
    private RoomState state;
    public static final Gson PARSER = new Gson(); 

    /**
      * Room constructor
      * @param obj a JsonObject representing the configurations the Room creator specified
      * @param urls an arraylist of the video urls of the initial Room playlist
      * @return a new Room object
      */
    public Room(JsonObject obj, ArrayList<String> urls){
        this.messages = new LinkedList<Message>();
        this.state = new RoomState();
        this.members = this.PARSER.fromJson(obj.getAsJsonArray("members"),ArrayList.class);
        this.videoUrls = urls;
    }
    
    //Function that transforms a Room to a HashMap
    public HashMap<String, Object> toMap() {
        HashMap<String,Object> ret = new HashMap<String,Object>();
        ret.put("video_state", this.state.currentState);
        ret.put("video_timestamp", this.state.currentVideoTimestamp);
        ret.put("members", this.members);
        ret.put("videoUrls", this.videoUrls);
        ret.put("currentVideo", this.state.currentVideo);
        ret.put("messageCount", this.state.messageCount);
        ret.put("messages", this.messages);
        return ret;
    }

    //Function that returns the Room as a Json String
    public String toJson(){
        return this.PARSER.toJson(this);
    }

    //Returns the Room's members
    public ArrayList<String> getMembers() {
        return this.members;
    }

    //Returns the Room's video url list
    public ArrayList<String> getUrls() {
        return this.videoUrls;
    }

    //Returns the Room's message list
    public LinkedList<Message> getMessages(){
        return this.messages;
    }
}
