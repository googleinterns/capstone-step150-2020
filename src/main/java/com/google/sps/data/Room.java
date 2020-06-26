<<<<<<< HEAD
package com.google.sps.data.servlets;
=======
package com.google.sps.data;
>>>>>>> structure

import Java.util.ArrayList;
import Java.util.LinkedList;
import Java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Room {
<<<<<<< HEAD
    public static video_state current_state;
    private LinkedList<String> members;
    private ArrayList<Message> messages;
    private ArrayList<String> video_urls;
    public static Long video_timestamp;
    public static int current_video;
    public static int message_count;

    public Room(JsonObject obj, ArrayList<String> urls){
        this.current_state = video_state.UNSTARTED;
        this.messages = new LinkedList<Message>();
        this.video_timestamp = 0;
        this.message_count = 0;
        this.current_video = 0;
=======

    private LinkedList<String> members;
    private ArrayList<Message> messages;
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
>>>>>>> structure
        this.members = obj.get("members");
        this.video_urls = urls;
    }
    
<<<<<<< HEAD
    public HashMap<String, Object> toMap() {
        HashMap<String,Object> ret = new HashMAp<String,Object>();
        ret.put("video_state", this.current_state);
        ret.put("video_timestamp", this.video_timestamp);
        ret.put("members", this.members);
        ret.put("video_urls", this.video_urls);
        ret.put("invitees", this.invitees);
        ret.put("current_video", this.current_video);
        return ret;
    }
    public LinkedList<String> getInvitees() {
        return this.members;
    }
    public ArrayList<String> getUrls() {
        return this.video_urls;
    }
=======
    //Function that transforms a Room to a HashMap
    public HashMap<String, Object> toMap() {
        HashMap<String,Object> ret = new HashMAp<String,Object>();
        ret.put("video_state", this.state.currentState);
        ret.put("video_timestamp", this.state.timestamp);
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
>>>>>>> structure
    public ArrayList<Message> getMessages(){
        return this.messages;
    }
}
