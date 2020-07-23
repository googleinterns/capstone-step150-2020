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
        Video video = Video.createVideo("www.RossJohnson.com");

        String expected = "{\"timestamp\" : \"0\", \"currentUrl\" : \"www.RossJohnson.com\", \"currentState\" : \"-1\"}";
        String actual = SyncServlet.videoObjectToJsonString(video);
        
        Assert.assertEquals(expected, actual);
    }
}
    