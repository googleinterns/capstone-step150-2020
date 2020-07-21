package com.google.sps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import java.util.*;

@RunWith(JUnit4.class)
public final class CollectVideosTest {

    @Test
    public void MockMethodsOfDoGet(){
        HttpServletRequest RequestMock = Mockito.mock(HttpServletRequest.class);
        Room RoomMock = Mockito.mock(Room.class);
        // When the doGet asks for the RoomId, return the hard-coded String 456
        when(RequestMock.getParameter(ServletUtil.INPUTTED_ID_TAG)).thenReturn("456");
        // When the doGet goes to fetch the Mocked Room Object
        when(RoomMock.fromRoomId((Long)456)).thenReturn(RoomMock);

        // Hard-coded queue of Video Objects
        Video VideoMock1 = Mockito.mock(Video.class);
        when(VideoMock1.getUrl()).thenReturn("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        Video VideoMock2 = Mockito.mock(Video.class);
        when(VideoMock2.getUrl()).thenReturn("https://www.youtube.com/watch?v=nhPcn-2iHJ4");
        Video VideoMock3 = Mockito.mock(Video.class);
        when(VideoMock3.getUrl()).thenReturn("https://www.youtube.com/watch?v=gqDRFD5qOx8");
        // Add mocked videos into queue
        Queue<Video> VideoQueueMock = new LinkedList<Video>();
        VideoQueueMock.add(VideoMock1);
        VideoQueueMock.add(VideoMock2);
        VideoQueueMock.add(VideoMock3);

        // When the doGet tries to get get the videos of the mock room object
        // return the queue of hard-coded videos
        when(RoomMock.getVideos()).thenReturn(VideoQueueMock);

        List<String> expectedList = new ArrayList<>();
        expectedList.add("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        expectedList.add("https://www.youtube.com/watch?v=nhPcn-2iHJ4");
        expectedList.add("https://www.youtube.com/watch?v=gqDRFD5qOx8");

        List<String> actualList = extractVideoUrls(VideoQueueMock);
        Assert.assertEquals(expectedList, actualList);
    }
}
