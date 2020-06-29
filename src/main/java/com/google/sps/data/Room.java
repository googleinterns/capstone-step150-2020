
package com.google.sps.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Room {

    private List<String> members;
    private LinkedList<Message> messages;
    private ArrayList<String> video_urls;
    private RoomState state;

    /**
      * Room constructor
      * @param members a JsonObject representing the configurations the Room creator specified
      * @param urls an arraylist of the video urls of the initial Room playlist
      * @return a new Room object
      */
    public Room(List<String> members, ArrayList<String> urls){
        this.messages = new LinkedList<Message>();
        this.state = new RoomState();
        this.members = members;
        this.video_urls = urls;
    }
    //Returns the Room's members
    public List<String> getMembers() {
        return this.members;
    }
    //Returns the Room's video url list
    public ArrayList<String> getUrls() {
        return this.video_urls;
    }
    //Returns the Room's message list
    public LinkedList<Message> getMessages(){
        return this.messages;
    }
}
