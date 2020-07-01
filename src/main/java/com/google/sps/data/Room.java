package com.google.sps.data;

import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.appengine.api.datastore.EmbeddedEntity;

public class Room {

    private List<String> members;
    private LinkedList<Message> messages;
    private Queue<String> videoUrls;
    private RoomState state;

    /**
      * Room constructor
      * @param members a List of strings representing the members of the room
      * @param urls an arraylist of the video urls of the initial Room playlist
      * @return a new Room object
      */
    public Room(List<String> members, Queue<String> urls){
        this.messages = new LinkedList<Message>();
        this.state = new RoomState();
        this.members = members;
        this.videoUrls = urls;
    }
    //Returns the Room's members
    public List<String> getMembers() {
        return this.members;
    }
    //Returns the Room's video url list
    public Queue<String> getUrls() {
        return this.videoUrls;
    }
    //Returns the Room's message list
    public LinkedList<Message> getMessages(){
        return this.messages;
    }
    public LinkedList<EmbeddedEntity> getMessagesAsEntities() {
        LinkedList<EmbeddedEntity> ret = new LinkedList<EmbeddedEntity>();
        for(Message m : this.messages){
            ret.add(m.toEmbeddedEntity());
        }
        return ret;
    }
    public Entity toEntity(){
        Entity newRoom = new Entity("Room");
        newRoom.setProperty("members", this.members);
        newRoom.setProperty("videoUrls", this.videoUrls);
        List<EmbeddedEntity> messageEntities = getMessagesAsEntities();
        newRoom.setProperty("messages", messageEntities);
        newRoom.setProperty("roomState", this.state.toEmbeddedEntity());
        return newRoom;
    }
}