package com.google.sps.data;
import com.google.appengine.api.datastore.EmbeddedEntity;

public class Video {

    private VideoState currentState;
    private long currentVideoTimestamp;
    private int currentVideo;
    private String url;

    //Default Video constructor
    public Video(String url) {
        this.url = url;
        this.currentState = VideoState.UNSTARTED;
        this.currentVideoTimestamp = 0;
        this.currentVideo = 0;
    }

    /**
      * Updates the state of a video object
      * @param state an enum element representing the YT player state
      * @param videoTimeStamp a long representing the seek position of the current video
      * @param currentVideo an int representing the current video's index in the URL list
      * @param messageCount an int representing the number of messages currently in the message list 
      * @return a new RoomState object
      */
    public void updateVideoState(VideoState state, long videoTimeStamp, int currentVideo) {
        this.currentState = state;
        this.currentVideoTimestamp = videoTimeStamp;
        this.currentVideo = currentVideo;
    }
    public VideoState getCurrentState(){
        return this.currentState;
    }
    public long getCurrentTimeStamp(){
        return this.currentVideoTimestamp;
    }
    public int getCurrentVideo(){
        return this.currentVideo;
    } 
    //Turns the video state to an embedded entity
    public EmbeddedEntity toEmbeddedEntity() {
        EmbeddedEntity videoEntity = new EmbeddedEntity();
        videoEntity.setProperty("url", this.url);
        videoEntity.setProperty("currentState", this.currentState.getValue());
        videoEntity.setProperty("currentVideoTimestamp", this.currentVideoTimestamp);
        videoEntity.setProperty("currentVideo", this.currentVideo);
        return videoEntity;
    }
}