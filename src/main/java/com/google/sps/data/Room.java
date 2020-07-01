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
import java.util.Iterator;

public class Room {

    private List<Member> members;
    private LinkedList<Message> messages;
    private Queue<Video> videos;

    /**
      * Room constructor
      * @param members a List of strings representing the members of the room
      * @param urls an arraylist of the video urls of the initial Room playlist
      * @return a new Room object
      */
    public Room(List<Member> members, Queue<Video> videos){
        this.messages = new LinkedList<Message>();
        this.members = members;
        this.videos = videos;
    }
    //Returns the Room's members
    public List<Member> getMembers() {
        return this.members;
    }

    //Get all of the members as a list of strings
    public List<String> getMembersAsStrings(){
        List<String> ret= new ArrayList<String>();
        for(Member m : members){
            ret.add(m.getAlias());
        }
        return ret;
    }

    //Returns the Room's video url list
    public Queue<Video> getVideos() {
        return this.videos;
    }

    //Returns the Room's message list
    public LinkedList<Message> getMessages(){
        return this.messages;
    }
    //Returns the messages as a list of embedded entities
    public LinkedList<EmbeddedEntity> getMessagesAsEntities() {
        LinkedList<EmbeddedEntity> ret = new LinkedList<EmbeddedEntity>();
        for(Message m : this.messages){
            ret.add(m.toEmbeddedEntity());
        }
        return ret;
    }
    //Returns a queue of embedded entities
    public Queue<EmbeddedEntity> getVideosAsEntities() {
        Video [] vids = this.videos.toArray(new Video[this.videos.size()]);
        Queue<EmbeddedEntity> ret = new LinkedList<EmbeddedEntity>();
        for(int i = vids.length; i >= 0; --i){
            ret.add(vids[i].toEmbeddedEntity());
        }
        return ret;
    }
    //Turns the Room object into a datastore entity
    public Entity toEntity(){
        Entity newRoom = new Entity("Room");
        newRoom.setProperty("members", getMembersAsStrings());
        newRoom.setProperty("videos", getVideosAsEntities());
        List<EmbeddedEntity> messageEntities = getMessagesAsEntities();
        newRoom.setProperty("messages", messageEntities);
        return newRoom;
    }
}