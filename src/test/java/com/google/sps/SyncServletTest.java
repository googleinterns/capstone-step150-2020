package com.google.sps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import com.google.sps.data.*;
import com.google.sps.servlets.SyncServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class SyncServletTest {
    
    private static final String UPDATE_STATE_PARAMETER = "userState";
    private static final String VIDEO_TIMESTAMP_PARAMETER = "timeStamp";

    @Test
    public void testCorrectVideoStringOutput(){
        Room testRoom = new Room(new LinkedList<Member>());
        testRoom.addVideo(Video.createVideo("www.RossJohnson.com"));
        String expected = "{\"currentState\":\"UNSTARTED\",\"currentVideoTimestamp\":0,\"url\":\"www.RossJohnson.com\"}";
        
        String actual = SyncServlet.roomToVideoJson(testRoom);
        
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testVideoUpdateTimeStamp(){
        long newTimestamp = 50;
        Video.VideoState newState = Video.VideoState.PLAYING;
        Queue<Video> videos = new LinkedList<Video>();
        videos.add(Video.createVideo("This is a url"));
        Room room = Room.createRoom(new LinkedList(),videos, new LinkedList());
        long expected = newTimestamp;

        room.updateCurrentVideoState(newState, newTimestamp);
        long actual = room.getVideos().peek().getCurrentTimeStamp();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testVideoUpdateState(){
        long newTimestamp = 50;
        Video.VideoState newState = Video.VideoState.PLAYING;
        Queue<Video> videos = new LinkedList<Video>();
        videos.add(Video.createVideo("This is a url"));
        Room room = Room.createRoom(new LinkedList(), videos, new LinkedList());
        Video.VideoState expected = newState;

        room.updateCurrentVideoState(newState, newTimestamp);
        Video.VideoState actual = room.getVideos().peek().getCurrentState();
        
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testVideoSyncWithVideoEnded(){
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
        //Room that is being passed through the helper function
        Room room = new Room(new LinkedList<Member>());
        room.addVideo(Video.createVideo("www.Ross.com"));
        room.addVideo(Video.createVideo("www.Johnson.com"));
        //Room to have the expected properties when we test
        Room room2 = new Room(new LinkedList<Member>());
        room2.addVideo(Video.createVideo("www.Johnson.com"));
        when(req.getParameter(UPDATE_STATE_PARAMETER)).thenReturn("0");

        String expected = SyncServlet.roomToVideoJson(room2);
        String actual = syncServletPost(req, res, room);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testVideoSyncWithVideoOngoing(){
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
        //Room that is being passed through the helper function
        Room room = new Room(new LinkedList<Member>());
        room.addVideo(Video.createVideo("www.Ross.com"));
        room.addVideo(Video.createVideo("www.Johnson.com"));
        //Room to have the expected properties when we test
        Room room2 = new Room(new LinkedList<Member>());
        room2.addVideo(Video.createVideo("www.Ross.com"));
        room2.updateCurrentVideoState(Video.VideoState.PAUSED,1234);
        when(req.getParameter(UPDATE_STATE_PARAMETER)).thenReturn("2");
        when(req.getParameter(VIDEO_TIMESTAMP_PARAMETER)).thenReturn("1234");

        String expected = SyncServlet.roomToVideoJson(room2);
        String actual = syncServletPost(req, res, room);

        Assert.assertEquals(expected, actual);
    }

    private String syncServletPost(HttpServletRequest req, HttpServletResponse res, Room room){
        Video.VideoState newState = Video.VideoState.fromInt(Integer.parseInt(req.getParameter(UPDATE_STATE_PARAMETER)));
        if(newState == Video.VideoState.ENDED){
            room.changeCurrentVideo();
        } 
        else {
            long currentVideoTimestamp = Long.parseLong(req.getParameter(VIDEO_TIMESTAMP_PARAMETER));
            room.updateCurrentVideoState(newState, currentVideoTimestamp);
        }

        return SyncServlet.roomToVideoJson(room);
    }
}
