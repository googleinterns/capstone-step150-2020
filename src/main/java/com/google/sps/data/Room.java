package com.google.sps.data;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class Room {
    public static final Gson PARSER = new Gson();
    private List<String> members;
    private LinkedList<Message> messages;
    private ArrayList<String> videoUrls;
    private RoomState state;
 
    /**
      * Room constructor
      * @param members a list of strings that represent members of the room
      * @param urls an arraylist of the video urls of the initial Room playlist
      * @return a new Room object
      */
    public Room(List<String> members, ArrayList<String> urls){
        this.messages = new LinkedList<Message>();
        this.state = new RoomState();
        this.members = members;
        this.videoUrls = urls;
    }

    //Function that returns the Room as a Json String
    public String toJson(){
        return this.PARSER.toJson(this);
    }

    //Returns the Room's members
    public List<String> getMembers() {
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
