package com.google.sps;

import static org.junit.Assert.assertEquals;
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
    private static long testTimestamp = 50;
    @Test
    public void testCorrectVideoStringOutput(){
        Room testRoom = new Room(new LinkedList<Member>());
        testRoom.addVideo(Video.createVideo("www.RossJohnson.com"));
        String expected = "{\"currentState\":\"UNSTARTED\",\"currentVideoTimestamp\":0,\"id\":\"www.RossJohnson.com\"}";
        
        String actual = SyncServlet.roomToVideoJson(testRoom);
        
        assertEquals(expected, actual);
    }

    @Test
    public void testVideoUpdateTimeStamp(){
        Video.VideoState newState = Video.VideoState.PLAYING;
        Queue<Video> videos = new LinkedList<Video>();
        videos.add(Video.createVideo("This is a url"));
        Room room = Room.createRoom(new LinkedList(),videos, new LinkedList());
        long expected = testTimestamp;
        room.updateCurrentVideoState(newState, testTimestamp);
        long actual = room.getVideos().peek().getCurrentTimeStamp();

        assertEquals(expected, actual);
    }

    @Test
    public void testVideoUpdateState(){
        
        Video.VideoState newState = Video.VideoState.PLAYING;
        Queue<Video> videos = new LinkedList<Video>();
        videos.add(Video.createVideo("This is a url"));
        Room room = Room.createRoom(new LinkedList(), videos, new LinkedList());
        Video.VideoState expected = newState;

        room.updateCurrentVideoState(newState, testTimestamp);
        Video.VideoState actual = room.getVideos().peek().getCurrentState();
        
        assertEquals(expected, actual);
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

        String expected = "{\"currentState\":\"UNSTARTED\",\"currentVideoTimestamp\":0,\"id\":\"www.Johnson.com\"}";
        String actual = syncServletPost(req, res, room);

        assertEquals(expected, actual);
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

        String expected = "{\"currentState\":\"PAUSED\",\"currentVideoTimestamp\":1234,\"id\":\"www.Ross.com\"}";
        System.out.println(expected);
        String actual = syncServletPost(req, res, room);

        assertEquals(expected, actual);
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
