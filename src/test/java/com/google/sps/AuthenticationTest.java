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

@RunWith(JUnit4.class)
public final class AuthenticationTest {

    public String testAuth(UserService mock){
        if(mock.isUserLoggedIn()){
            String userEmail = mock.getCurrentUser().getEmail();
            String urlToRedirectToAfterUserLogsOut = "/";
            String logoutUrl = mock.createLogoutURL(urlToRedirectToAfterUserLogsOut);
            return userEmail;  
        }
        else{
            String urlToRedirectToAfterUserLogsIn = "redirect";
            String loginUrl = mock.createLoginURL(urlToRedirectToAfterUserLogsIn);
            return "<center><a href=\"" + loginUrl + "\"><button style=\"background-color: cyan; border-radius:12px;\"><span style=\"color:white;\">Login</span></button/></a></center>";
        }
    }

    @Test
    public void UserServiceTestUserIsLoggedIn(){
        UserService UserServiceMock = Mockito.mock(UserService.class);
        when(UserServiceMock.isUserLoggedIn()).thenReturn(true);
        when(UserServiceMock.getCurrentUser()).thenReturn(new User("rossjohnson@google.com", "yeet"));
        String expected = "rossjohnson@google.com";
        String actual = testAuth(UserServiceMock);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void UserServiceTestUserIsNotLoggedIn(){
        UserService UserServiceMock = Mockito.mock(UserService.class);
        when(UserServiceMock.isUserLoggedIn()).thenReturn(false);
        when(UserServiceMock.createLoginURL("redirect")).thenReturn("");

        String expected = "<center><a href=\"" + "\"><button style=\"background-color: cyan; border-radius:12px;\"><span style=\"color:white;\">Login</span></button/></a></center>";
        String actual = testAuth(UserServiceMock);

        Assert.assertEquals(expected, actual);
    }

}