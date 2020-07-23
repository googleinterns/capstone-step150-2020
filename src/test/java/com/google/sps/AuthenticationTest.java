package com.google.sps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;
import java.util.*;
import com.google.sps.data.*;
import com.google.sps.servlets.*;

@RunWith(JUnit4.class)
public final class AuthenticationTest {

    @Test
    public void UserServiceTestUserIsLoggedIn(){
        //Setup
        UserService UserServiceMock = Mockito.mock(UserService.class);
        when(UserServiceMock.isUserLoggedIn()).thenReturn(true);
        when(UserServiceMock.getCurrentUser()).thenReturn(new User("rossjohnson@google.com", "yeet"));
        String redirectUrl = "/redirect";
        Boolean loggedin = AuthServlet.isUserLoggedIn(UserServiceMock);
        //Expected output
        String expected = "rossjohnson@google.com";
        
        //Actual outpud
        String actual = AuthServlet.getResponseBody(loggedin, redirectUrl, UserServiceMock);

        //Check if equal
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void UserServiceTestUserIsNotLoggedIn(){
        //Setup
        UserService UserServiceMock = Mockito.mock(UserService.class);
        when(UserServiceMock.isUserLoggedIn()).thenReturn(false);
        when(UserServiceMock.createLoginURL("redirect")).thenReturn("");
        Boolean loggedin = AuthServlet.isUserLoggedIn(UserServiceMock);
        //Expected result
        String expected = "<center><a href=\"" + "\"><button style=\"background-color: cyan; border-radius:12px;\"><span style=\"color:white;\">Login</span></button/></a></center>";
        
        //Actual result
        String actual = AuthServlet.getResponseBody(loggedin,"redirect",UserServiceMock);

        //Check if equal
        Assert.assertEquals(expected, actual);
    }
}