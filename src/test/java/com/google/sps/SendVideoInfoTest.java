package com.google.sps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import com.google.sps.data.*;
import java.util.*;
import com.google.sps.servlets.SyncServlet;

@RunWith(JUnit4.class)
public final class SendVideoInfoTest {
    
    @Test
    public void testCorrectVideoStringOutput(){
        Room testRoom = Mockito.mock(Room.class);
        when(testRoom.getCurrentVideo()).thenReturn(Video.createVideo("www.RossJohnson.com"));
        String expected = "{\"currentState\":\"UNSTARTED\",\"currentVideoTimestamp\":0,\"url\":\"www.RossJohnson.com\"}";
        
        String actual = SyncServlet.roomToVideoJson(testRoom);
        
        Assert.assertEquals(expected, actual);
    }
}
    