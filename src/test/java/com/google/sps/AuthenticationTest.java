package com.google.sps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
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
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.io.PrintWriter;

@RunWith(JUnit4.class)
public final class AuthenticationTest {
    private static final String REDIRECT_URL_PARAMETER = "redirectUrl";
    static HttpServletRequest req;
    static HttpServletResponse res;
    StringWriter stringWriter;
    @Before
    public void setup() throws Exception {
        req = Mockito.mock(HttpServletRequest.class);
        when(req.getParameter(REDIRECT_URL_PARAMETER)).thenReturn("/redirect");

        res = Mockito.mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(res.getWriter()).thenReturn(writer);
    }
    @Test
    public void UserServiceTestUserIsLoggedIn() throws Exception {
        //Setup
        UserService UserServiceMock = Mockito.mock(UserService.class);
        when(UserServiceMock.isUserLoggedIn()).thenReturn(true);
        when(UserServiceMock.getCurrentUser()).thenReturn(new User("rossjohnson@google.com", "yeet"));
        when(UserServiceMock.createLoginURL("/redirect")).thenReturn("");
        setFinalStatic(ServletUtil.class.getField("USER_SERVICE"), UserServiceMock);
        //Expected output
        String expected = "rossjohnson@google.com";
        
        //Execution
        new AuthServlet().doGet(req, res);
        //Check if equal
        Assert.assertTrue(expected, stringWriter.getBuffer().toString().contains(expected));
    }

    @Test
    public void UserServiceTestUserIsNotLoggedIn() throws Exception{
        //Setup
        UserService UserServiceMock = Mockito.mock(UserService.class);
        when(UserServiceMock.isUserLoggedIn()).thenReturn(false);
        when(UserServiceMock.createLoginURL("/redirect")).thenReturn("");
        setFinalStatic(ServletUtil.class.getField("USER_SERVICE"), UserServiceMock);
        //Expected result
        String expected = "<center><a href=\"" + "\"><button id= \"loginbutton\">Login</button/></a></center>";
        
        //Execution
        new AuthServlet().doGet(req, res);

        //Check if equal
        Assert.assertTrue(expected, stringWriter.getBuffer().toString().contains(expected));
    }

    static void setFinalStatic(Field fieldToChange, Object newObjValue) throws Exception {
        fieldToChange.setAccessible(true);

        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(fieldToChange, fieldToChange.getModifiers() & ~Modifier.FINAL);

        fieldToChange.set(null, newObjValue);
    }
}
