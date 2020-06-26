<<<<<<< HEAD
package com.google.sps.data.servlets;
=======
package com.google.sps.data;
>>>>>>> structure

public class Message{
    public static String sender;
    public static String message_body;
    public static Long timestamp;
    
<<<<<<< HEAD
    public Message(){

    }
}
=======
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
        return this;
    }
}
>>>>>>> structure
