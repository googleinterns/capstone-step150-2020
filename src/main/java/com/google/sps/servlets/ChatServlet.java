package com.google.sps.servlets;

import com.google.sps.data.Room;
import com.google.sps.data.Message;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import java.util.LinkedList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
    // Retrieve messages from datastore  
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {    
        String query = request.getParameter("roomID");    
        long roomID = Long.parseLong(query);    
        Gson gson = new Gson();
        // Retrieves the entity with matching ID and its corresponding messages property as a JSON string    
        Room room = Room.fromRoomId(roomID));    
        Queue<Message> messages = room.getMessages();    
        String jsonMessages = gson.toJson(messages);          
        response.setContentType("application/json;");    
        response.getWriter().println(jsonMessages);  
    }   
    // Update messages in datastore  
    @Override  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {    
        String message = getParameter(request, "text-input", "");    
        long timestamp = System.currentTimeMillis();
        // TODO: get sender information based on their login    
        String sender = request.getParameter("userEmail"); 
        // Get room ID from URL request    
        String query = request.getParameter("roomId");    
        System.out.println(query);    
        long roomID = Long.parseLong(query);    
        Message chatMessage = Message.createNewMessage(sender, message, timestamp);
        Room room = Room.fromRoomId(roomID);
        room.addMessage(chatMessage);
        room.toDatastore();          
        // TODO: Correct redirect    
        response.sendRedirect("/index.html");  
    }
      
    private String getParameter(HttpServletRequest request, String name, String defaultValue) {    
        String value = request.getParameter(name);    
        if (value == null) {      
            return defaultValue;    
        }    
        return value;  
    }
}
