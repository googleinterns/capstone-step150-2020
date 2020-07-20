package com.google.sps.data;
import com.google.appengine.api.datastore.EmbeddedEntity;
import java.util.Map;

//Message object representing messages sent in the chat of the Room
public class Message{
    private static final String SENDER_PROPERTY = "sender";
    private static final String MESSAGE_PROPERTY = "message";
    private static final String TIMESTAMP_PROPERTY = "timestamp";
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
    private Message(String sender, String message, Long timestamp){
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    //Creates a new message
    public static Message createNewMessage(String sender, String message, Long timestamp) {
        return new Message(sender, message, timestamp);
    }

    //Returns the sender of the string
    public String getSender(){
        return this.sender;
    }

    //Returns the body of the message
    public String getMessageBody(){
        return this.message;
    }
    
    //Returns when the message was sent
    public Long getTimestamp(){
        return this.timestamp;
    }
    
    //Turns the Message object into an embedded entity
    public static EmbeddedEntity toEmbeddedEntity(Message message){
        EmbeddedEntity messageEntity = new EmbeddedEntity();
        messageEntity.setProperty(SENDER_PROPERTY, message.getSender());
        messageEntity.setProperty(MESSAGE_PROPERTY, message.getMessageBody());
        messageEntity.setProperty(TIMESTAMP_PROPERTY, message.getTimestamp());
        return messageEntity;
    }

    //Turns the message embedded entity into a Message object
    public static Message fromEmbeddedEntity(EmbeddedEntity messageEntity) {
        Map<String,Object> properties = messageEntity.getProperties();
        return new Message((String) properties.get(SENDER_PROPERTY), (String) properties.get(MESSAGE_PROPERTY), (long) properties.get(TIMESTAMP_PROPERTY));
    }
}
