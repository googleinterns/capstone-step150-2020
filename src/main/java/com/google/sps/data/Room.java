package com.google.sps.data;

import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.google.appengine.api.datastore.Key;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.stream.*;

public class Room {
    private static final String ROOM_ENTITY = "Room";
    private static final String MEMBERS_PROPERTY = "members";
    private static final String MESSAGES_PROPERTY = "messages";
    private static final String VIDEOS_PROPERTY = "videos";
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private List<Member> members;
    private LinkedList<Message> messages;
    private Queue<Video> videos;

    /**
      * Room constructor
      * @param members a List of Member objects
      * @param videos an Queue of video objects
      * @return a new Room object
      */
    private Room(List<Member> members, Queue<Video> videos){
        this.messages = new LinkedList<Message>();
        this.members = members;
        this.videos = videos;
    }
    
    /**
      * Room constructor
      * @param members a List of Member objects
      * @param videos an Queue of video objects
      * @param messages a List of message objects
      * @return a new Room object
      */
    private Room(List<Member> members, Queue<Video> videos, LinkedList<Message> messages) {
        this.messages = messages;
        this.videos = videos;
        this.messages = messages;
    }

    //Room factory function
    public static Room createRoom(List<Member> members, Queue<Video> videos, LinkedList<Message> messages){
        return new Room(members, videos, messages);
    }

    //Returns the Room's members
    public List<Member> getMembers() {
        return this.members;
    }

    //Get all of the members as a list of EmbeddedEntities
    private List<EmbeddedEntity> getMembersAsEmbeddedEntities(){
        return members.stream().map(Member::toEmbeddedEntity).collect(Collectors.toList());
    }

    //Returns the Room's video url list
    public Queue<Video> getVideos() {
        return this.videos;
    }

    //Returns the Room's message list
    public LinkedList<Message> getMessages(){
        return this.messages;
    }

    //Updates the current video in the room's state
    public void updateCurrentVideoState(Video.VideoState state, long videoTimeStamp){
        this.videos.peek().updateVideoState(state, videoTimeStamp);
    }

    //Returns the messages as a list of embedded entities
    private List<EmbeddedEntity> getMessagesAsEntities() {
        return messages.stream().map(Message::toEmbeddedEntity).collect(Collectors.toList());
    }

    //Returns a queue of embedded entities
    private List<EmbeddedEntity> getVideosAsEntities() {
        return this.videos.stream().map(Video::toEmbeddedEntity).collect(Collectors.toList());
    }

    //Turns the Room object into a datastore entity
    public static Entity toEntity(Room room){
        Entity newRoom = new Entity(ROOM_ENTITY);
        newRoom.setProperty(MEMBERS_PROPERTY, room.getMembersAsEmbeddedEntities());
        newRoom.setProperty(VIDEOS_PROPERTY, room.getVideosAsEntities());
        newRoom.setProperty(MESSAGES_PROPERTY, room.getMessagesAsEntities());
        return newRoom;
    }
    //Creates a room object from a Datastore Key
    public static Room fromKey(Key roomKey) {
        try {
            return Room.fromEntity(datastore.get(roomKey));
        } catch (EntityNotFoundException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    //Adds a message to the room
    public void addMessage(Message message){
        if(this.messages.size() < 10) {
            this.messages.add(message);
        } else {
            this.messages.remove(0);
            this.messages.add(message);
        }
    }

    //Adds a video to the Room's video queue
    public void addVideo(String url) {
        this.videos.add(Video.createVideo(url));
    }

    //Turns a Room entitiy into a Room object
    public static Room fromEntity(Entity roomEntity) {
        Map<String, Object> properties = roomEntity.getProperties();
        List<Member> memberList = 
        ((ArrayList<EmbeddedEntity>) properties.get(MEMBERS_PROPERTY)).stream().map(Member::fromEmbeddedEntity).collect(Collectors.toCollection(ArrayList::new));
        Queue<Video> videoQueue = 
        ((Queue<EmbeddedEntity>) properties.get(VIDEOS_PROPERTY)).stream().map(Video::fromEmbeddedEntity).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Message> messageList = 
        ((LinkedList<EmbeddedEntity>) properties.get(MESSAGES_PROPERTY)).stream().map(Message::fromEmbeddedEntity).collect(Collectors.toCollection(LinkedList::new));
        return new Room(memberList, videoQueue, messageList);
    }

    //Removes the current video from the head of the queue
    public void changeCurrentVideo(){
        this.videos.poll();
    }

    //Rrturns the video object at the head of the queue
    public Video getCurrentVideo(){
        return this.videos.peek();
    }
}
