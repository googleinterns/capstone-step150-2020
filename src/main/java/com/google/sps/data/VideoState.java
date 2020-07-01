package com.google.sps.data;

public enum VideoState {
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
        private VideoState(int value){
            this.value = value;
        }
    public int getValue(){
        return this.value;
    }
};
