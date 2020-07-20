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
import com.google.appengine.api.datastore.DatastoreFailureException;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.stream.*;
import com.google.appengine.api.datastore.KeyFactory;

//Object repsresenting the Room that the chat and video streaming will be in
//TODO: Restructure so logic is more split up
public class Room {
    private static final int MAX_MESSAGES = 10;
    private static final int MAX_VIDEOS = 15;
    private static final String ROOM_ENTITY = "Room";
    private static final String MEMBERS_PROPERTY = "members";
    private static final String MESSAGES_PROPERTY = "messages";
    private static final String VIDEOS_PROPERTY = "videos";
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private List<Member> members;
    private Queue<Message> messages;
    private Queue<Video> videos;
    private Key roomKey;

    //Room factory function
    public static Room createRoom(List<Member> members, Queue<Video> videos, Queue<Message> messages){
        return new Room(members, videos, messages, null);
    }

    //Creates a room object from a Datastore Key
    public static Room fromRoomId(long roomId) {
        return Room.fromRoomKey(KeyFactory.createKey(ROOM_ENTITY, roomId));
    }

    //Turns a Room key into a Room object
    private static Room fromRoomKey(Key roomKey) {
        try {
            Entity roomEntity = datastore.get(roomKey);
            Map<String, Object> properties = roomEntity.getProperties();
            List<Member> memberList = 
            ((ArrayList<EmbeddedEntity>) properties.get(MEMBERS_PROPERTY)).stream().map(Member::fromEmbeddedEntity).collect(Collectors.toCollection(ArrayList::new));
            Queue<Video> videoQueue = 
            ((ArrayList<EmbeddedEntity>) properties.get(VIDEOS_PROPERTY)).stream().map(Video::fromEmbeddedEntity).collect(Collectors.toCollection(LinkedList::new));
            Queue<Message> messageQueue = (Queue<Message>) properties.get(MESSAGES_PROPERTY) != null ?
            ((ArrayList<EmbeddedEntity>) properties.get(MESSAGES_PROPERTY)).stream().map(Message::fromEmbeddedEntity).collect(Collectors.toCollection(LinkedList::new)) : new LinkedList();
            return new Room(memberList, videoQueue, messageQueue, roomKey);
        } catch (EntityNotFoundException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    //Turns the Room object into a datastore entity
    private static Entity toEntity(Room room){
        Entity newRoom = room.roomKey == null ? new Entity(ROOM_ENTITY) : new Entity(ROOM_ENTITY, room.roomKey);
        newRoom.setProperty(MEMBERS_PROPERTY, room.getMembersAsEmbeddedEntities());
        newRoom.setProperty(VIDEOS_PROPERTY, room.getVideosAsEntities());
        newRoom.setProperty(MESSAGES_PROPERTY, room.getMessagesAsEntities());
        return newRoom;
    }

    //Takes a room object, puts it into datastore and returns the resulting entity's key
    public Long toDatastore(){
        Entity room = Room.toEntity(this);
        try {
            return DatastoreServiceFactory.getDatastoreService().put(room).getId();
        } catch (DatastoreFailureException e){
            System.out.println(e.toString());
        }
        return null;
    }

    //Returns the Room's video url list
    public Queue<Video> getVideos() {
        return new LinkedList<Video>(this.videos);
    }

    //Returns the Room's message list
    public Queue<Message> getMessages(){
        return new LinkedList<Message>(this.messages);
    }

    //Returns the Room's members
    public List<Member> getMembers() {
        return new ArrayList<Member>(this.members);
    }

    // Helper addMessage function to manipulate the list of messages
    public void addMessage(Message msg){
        if(messages.size() >= MAX_MESSAGES) {
            messages.poll();
        }
        messages.add(msg);
    }

    //Adds a video to the Room's video queue
    public boolean addVideo(Video video) {
        if(this.videos.size() >  MAX_VIDEOS){
            this.videos.add(video);
            return true;
        }
        return false;
    }

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
      * @param messages a Queue of message objects
      * @return a new Room object
      */
    private Room(List<Member> members, Queue<Video> videos, Queue<Message> messages, Key key) {
        this.members = members;
        this.videos = videos;
        this.messages = messages;
        this.roomKey = key;
    }

    //Get all of the members as a list of EmbeddedEntities
    private List<EmbeddedEntity> getMembersAsEmbeddedEntities() {
        return members.stream().map(Member::toEmbeddedEntity).collect(Collectors.toList());
    }

    //Returns the messages as a list of embedded entities
    private List<EmbeddedEntity> getMessagesAsEntities() {
        return messages.stream().map(Message::toEmbeddedEntity).collect(Collectors.toList());
    }

    //Returns a queue of embedded entities
    private List<EmbeddedEntity> getVideosAsEntities() {
        return this.videos.stream().map(Video::toEmbeddedEntity).collect(Collectors.toList());
    }
}
