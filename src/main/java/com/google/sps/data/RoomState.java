package com.google.sps.data;
import com.google.appengine.api.datastore.EmbeddedEntity;

public class RoomState {

    public static VideoState currentState;
    public static long currentVideoTimestamp;
    public static int currentVideo;

    //Default RoomState constructor
    public RoomState() {
        this.currentState = VideoState.UNSTARTED;
        this.currentVideoTimestamp = 0;
        this.currentVideo = 0;
    }

    /**
      * RoomState constructor
      * @param state an enum element representing the YT player state
      * @param videoTimeStamp a long representing the seek position of the current video
      * @param currentVideo an int representing the current video's index in the URL list
      * @param messageCount an int representing the number of messages currently in the message list 
      * @return a new RoomState object
      */
    public RoomState(VideoState state, long videoTimeStamp, int currentVideo) {
        this.currentState = state;
        this.currentVideoTimestamp = videoTimeStamp;
        this.currentVideo = currentVideo;
    }
    //Turns the video state to an embedded entity
    public EmbeddedEntity toEmbeddedEntity() {
        EmbeddedEntity stateEntity = new EmbeddedEntity();
        stateEntity.setProperty("currentState", this.currentState.getValue());
        stateEntity.setProperty("currentVideoTimestamp", this.currentVideoTimestamp);
        stateEntity.setProperty("currentVideo", this.currentVideo);
        return stateEntity;
    }
}