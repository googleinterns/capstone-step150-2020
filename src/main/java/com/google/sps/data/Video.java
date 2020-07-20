package com.google.sps.data;

import com.google.appengine.api.datastore.EmbeddedEntity;
import java.util.Map;
import java.util.HashMap;

//Video object representing the videos in the room

public class Video {
    private static final String URL_PROPERTY = "url";
    private static final String CURRENT_STATE_PROPERTY = "currentState";
    private static final String TIMESTAMP_PROPERTY = "currentVideoTimestamp";
    private VideoState currentState;
    private long currentVideoTimestamp;
    private String url;

    //Default Video constructor
    private Video(String url) {
        this.url = url;
        this.currentState = VideoState.UNSTARTED;
        this.currentVideoTimestamp = 0;
    }

    //Video constructor
    private Video(String url, VideoState currentVideoState, long currentVideoTimestamp) {
        this.url = url;
        this.currentState = currentVideoState;
        this.currentVideoTimestamp = currentVideoTimestamp;
    }

    //Static video factory
    public static Video createVideo(String url) {
        return new Video(url);
    }

    //Static video factory
    public static Video createVideo(String url, VideoState currentVideoState, long currentVideoTimestamp) {
        return new Video(url, currentVideoState, currentVideoTimestamp);
    }

    /**
      * Updates the state of a video object
      * @param state an enum element representing the YT player state
      * @param videoTimeStamp a long representing the seek position of the current video
      * @param currentVideo an int representing the current video's index in the URL list
      * @param messageCount an int representing the number of messages currently in the message list 
      * @return a new RoomState object
      */
    public void updateVideoState(VideoState state, long videoTimeStamp) {
        this.currentState = state;
        this.currentVideoTimestamp = videoTimeStamp;
    }

    //Returns the video's current state
    public VideoState getCurrentState(){
        return this.currentState;
    }

    //Returns the TimeStamp of the video
    public long getCurrentTimeStamp(){
        return this.currentVideoTimestamp;
    }

    //Returns the url of the video
    public String getUrl(){
        return this.url;
    }

    //Turns the video state to an embedded entity
    public static EmbeddedEntity toEmbeddedEntity(Video video) {
        EmbeddedEntity videoEntity = new EmbeddedEntity();
        videoEntity.setProperty(URL_PROPERTY, video.getUrl());
        videoEntity.setProperty(CURRENT_STATE_PROPERTY, (int) video.getCurrentState().getValue());
        videoEntity.setProperty(TIMESTAMP_PROPERTY, video.getCurrentTimeStamp());
        return videoEntity;
    }

    //Turns an embedded entity into a video object
    public static Video fromEmbeddedEntity(EmbeddedEntity videoEntity) {
        Map<String, Object> properties = videoEntity.getProperties();
        return new Video((String) properties.get(URL_PROPERTY), VideoState.fromInt( ((Long)properties.get(CURRENT_STATE_PROPERTY)).intValue()), (long) properties.get(TIMESTAMP_PROPERTY));
    }

    //Enum representing the YT player state
    public static enum VideoState {
        //Video has not started playing
        UNSTARTED(-1), 
        //Video has ended
        ENDED(0), 
        //Video is playing
        PLAYING(1), 
        //Video is paused
        PAUSED(2), 
        //Video is buffering
        BUFFERING(3), 
        //Video has been cued
        CUED(5);
        
        private int value;
        private static Map<Integer, VideoState> VIDEO_STATE_MAP = new HashMap<Integer, VideoState>();

        //constructor
        private VideoState(int value) {
            this.value = value;
        }

        //Instantiates map
        static {
            for (VideoState videoState : VideoState.values()) {
                VIDEO_STATE_MAP.put(videoState.value, videoState);
            }
        }

        //To get int value from VideoState enum
        public int getValue() {
            return this.value;
        }

        //To turn integer into VideoState enum
        public static VideoState fromInt(int val) {
            return VIDEO_STATE_MAP.get(val);
        }
    };
}
