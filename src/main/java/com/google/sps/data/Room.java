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
    //Returns the messages as a list of embedded entities
    private LinkedList<EmbeddedEntity> getMessagesAsEntities() {
        return (LinkedList) messages.stream().map(Message::toEmbeddedEntity).collect(Collectors.toList());
    }
    //Returns a queue of embedded entities
    private Queue<EmbeddedEntity> getVideosAsEntities() {
        return (Queue<EmbeddedEntity>) this.videos.stream().map(Video::toEmbeddedEntity).collect(Collectors.toList());
    }
    //Turns the Room object into a datastore entity
    public static Entity toEntity(Room room){
        Entity newRoom = new Entity(ROOM_ENTITY);
        newRoom.setProperty(MEMBERS_PROPERTY, room.getMembersAsEmbeddedEntities());
        newRoom.setProperty(VIDEOS_PROPERTY, room.getVideosAsEntities());
        newRoom.setProperty(MESSAGES_PROPERTY, room.getMessagesAsEntities());
        return newRoom;
    }
    public static Room fromKey(Key roomKey){
        try {
        Entity roomEntity = datastore.get(roomKey);
        return Room.fromEntity(roomEntity);
        } catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
        return null;
    }
    public void setMessages(LinkedList<Message> messages){
        this.messages = messages;
    }
    private void addMessage(Message msg){
        if(messages.size() >= 10) {
            messages.remove();
        }
        messages.add(msg);
    }
    public static void addMessagesFromKey(Key roomKey, Message chatMessage) {
        try {
        Entity roomEntity = datastore.get(roomKey);
        Room room = Room.fromEntity(roomEntity);
        room.addMessage(chatMessage);

        roomEntity.setProperty("messages", room.getMessagesAsEntities());
        datastore.put(roomEntity);
        } catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
    }

    //Turns a Room entitiy into a Room object
    public static Room fromEntity(Entity roomEntity) {
        Map<String, Object> properties = roomEntity.getProperties();
        ArrayList<Member> memberList = new ArrayList<Member>();
        for(EmbeddedEntity e : (List<EmbeddedEntity>) properties.get(MEMBERS_PROPERTY)){
            memberList.add(Member.fromEmbeddedEntity(e));
        }
        Queue<Video> videoQueue = new LinkedList<Video>();
        for(EmbeddedEntity e : (List<EmbeddedEntity>) properties.get(VIDEOS_PROPERTY)){
            videoQueue.add(Video.fromEmbeddedEntity(e));
        }
        LinkedList<Message> messageList = new LinkedList<Message>();
        for(EmbeddedEntity e : (List<EmbeddedEntity>) properties.get(MESSAGES_PROPERTY)){
            messageList.add(Message.fromEmbeddedEntity(e));
        }
        Room room = new Room(memberList, videoQueue);
        room.setMessages(messageList);
        return room;
    }
}
