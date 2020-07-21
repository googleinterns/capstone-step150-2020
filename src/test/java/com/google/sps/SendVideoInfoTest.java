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
public final class SendVideoInfoTest {

    private String videoObjectToJsonString(Video video){
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{\"timestamp\" : \"");
        jsonString.append(video.getCurrentTimeStamp() + "\", ");
        jsonString.append("\"currentUrl\" : \"");
        jsonString.append(video.getUrl() + "\", ");
        jsonString.append("\"currentState\" : \"");
        jsonString.append(video.getCurrentState().getValue() + "\"}");
        return jsonString.toString();
    }
    
    @Test
    public void testCorrectVideoStringOutput(){
        Video video = Video.createVideo("www.RossJohnson.com");

        String expected = "{\"timestamp\" : \"0\", \"currentUrl\" : \"www.RossJohnson.com\", \"currentState\" : \"-1\"}";
        String actual = videoObjectToJsonString(video);
        
        Assert.assertEquals(expected, actual);
    }
}
    