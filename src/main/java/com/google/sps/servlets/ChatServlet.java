package com.google.sps.servlets;

import com.google.sps.data.Message;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import java.util.LinkedList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chat")

public class ChatServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      long roomID = Long.parseLong(request.getQueryString());
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Gson gson = new Gson();

     // Handle entity not found exception
    try{
        // Retrieves the entity with matching ID and its corresponding messages property as a JSON string
        Entity roomEntity = datastore.get(KeyFactory.createKey("Room", roomID));
        String jsonMessages = (String) roomEntity.getProperty("messages");
        
        response.setContentType("application/json;");
        response.getWriter().println(jsonMessages);


        // TODO: Correct redirect
        response.sendRedirect("/index.html");
        }

    catch(EntityNotFoundException exc){
        System.out.println(exc.toString());
        response.sendRedirect("/error.html");
    }
      
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // TODO: get the correct HTML element from room page
    String message = getParameter(request, "text-input", "");
    long timestamp = System.currentTimeMillis();

    // TODO: get sender information based on their login
    String sender = "sender";

    // TODO: get room ID from URL request
    long roomID = Long.parseLong(request.getQueryString());

    Message chatMessage = new Message(sender, message, timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Gson gson = new Gson();

    // Handle entity not found exception
    try{
        // Retrieves the entity with matching ID and its corresponding messages property as a JSON string
        Entity roomEntity = datastore.get(KeyFactory.createKey("Room", roomID));
        String jsonMessages = (String) roomEntity.getProperty("messages");
        LinkedList<Message> messages = gson.fromJson(jsonMessages, LinkedList.class);

        // Add the most recent message to the end of the linked list and remove the oldest if necessary
        if(messages.size() >= 10) {
            messages.remove();
        }
        messages.add(chatMessage);

        String newJsonMessages = gson.toJson(messages);
        roomEntity.setProperty("chatRoom", newJsonMessages);
        datastore.put(roomEntity);
        
        // TODO: Correct redirect
        response.sendRedirect("/index.html");
        }
    catch(EntityNotFoundException exc){
        System.out.println(exc.toString());
        response.sendRedirect("/error.html");
    }
  }


  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}