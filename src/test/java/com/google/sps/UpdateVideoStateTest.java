package com.google.sps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import com.google.sps.data.*;

import java.util.*;

@RunWith(JUnit4.class)
public final class UpdateVideoStateTest {

    @Test
    public void testVideoUpdateTimeStamp(){
        long newTimestamp = 50;
        Video.VideoState newState = Video.VideoState.PLAYING;
        Queue<Video> videos = new LinkedList<Video>();
        videos.add(Video.createVideo("This is a url"));
        Room room = Room.createRoom(new LinkedList(),videos, new LinkedList());

        room.updateCurrentVideoState(newState, newTimestamp);

        long expected = newTimestamp;
        long actual = room.getVideos().peek().getCurrentTimeStamp();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testVideoUpdateState(){
        long newTimestamp = 50;
        Video.VideoState newState = Video.VideoState.PLAYING;
        Queue<Video> videos = new LinkedList<Video>();
        videos.add(Video.createVideo("This is a url"));
        Room room = Room.createRoom(new LinkedList(),videos, new LinkedList());

        room.updateCurrentVideoState(newState, newTimestamp);

        Video.VideoState expected = newState;
        Video.VideoState actual = room.getVideos().peek().getCurrentState();
        
        Assert.assertEquals(expected, actual);
    }
}
    