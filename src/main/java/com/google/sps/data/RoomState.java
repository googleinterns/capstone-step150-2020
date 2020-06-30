package com.google.sps.data;

public class RoomState {

    public static DataUtil.video_state currentState;
    public static long currentVideoTimestamp;
    public static int currentVideo;
    public static int messageCount;

    //Default RoomState constructor
    public RoomState() {
        this.currentState = DataUtil.video_state.UNSTARTED;
        this.currentVideoTimestamp = 0;
        this.currentVideo = 0;
        this.messageCount = 0;
    }
    /**
      * RoomState constructor
      * @param state an enum element representing the YT player state
      * @param videoTimeStamp a long representing the seek position of the current video
      * @param currentVideo an int representing the current video's index in the URL list
      * @param messageCount an int representing the number of messages currently in the message list 
      * @return a new RoomState object
      */
    public RoomState(DataUtil.video_state state, long videoTimeStamp, int currentVideo, int messageCount) {
        this.currentState = state;
        this.currentVideoTimestamp = videoTimeStamp;
        this.currentVideo = currentVideo;
        this.messageCount = messageCount;
    }
}
