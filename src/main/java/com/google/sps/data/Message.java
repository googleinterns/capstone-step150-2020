package com.google.sps.data;
import com.google.appengine.api.datastore.EmbeddedEntity;

public class Message{
    private String sender;
    private String message;
    private Long timestamp;
    
    /**
      * Message constructor
      * @param sender a String of the senders email/email alias
      * @param message the message body of the text message
      * @param timestamp a long representing the time that the message was sent at
      * @return a new Message Object
      */
    public Message(String sender, String message, Long timestamp){
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSender(){
        return this.sender;
    }

    public String getMessageBody(){
        return this.message;
    }

    public Long getTimestamp(){
        return this.timestamp;
    }
    
    //Turns the MEssage object into an embedded entity
    public EmbeddedEntity toEmbeddedEntity(){
        EmbeddedEntity messageEntity = new EmbeddedEntity();
        messageEntity.setProperty("sender", this.sender);
        messageEntity.setProperty("message", this.sender);
        messageEntity.setProperty("timestamp", this.timestamp);
        return messageEntity;
    }
}
