package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

//Servlet to authenticate users before they create or join a Room
@WebServlet("/auth")
public class AuthServlet extends HttpServlet{
    private static final String REDIRECT_URL_PARAMETER = "redirectUrl";
    private static final String URL_TO_REDIRECT_AFTER_LOG_OUT = "/";
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{

        Boolean isUserLoggedIn = isUserLoggedIn(ServletUtil.USER_SERVICE);
        String urlToRedirectToAfterUserLogsIn = req.getParameter(REDIRECT_URL_PARAMETER);
        String responseBody = getResponseBody(isUserLoggedIn, urlToRedirectToAfterUserLogsIn, ServletUtil.USER_SERVICE);
        if(isUserLoggedIn){
            res.setContentType(ServletUtil.JSON_CONTENT_TYPE);
            res.getWriter().println(ServletUtil.PARSER.toJson(responseBody));  
        }
        else{
            res.setContentType(ServletUtil.HTML_CONTENT_TYPE);
            res.getWriter().println(responseBody);
        }
    }

    public static Boolean isUserLoggedIn(UserService userService){
        return userService.isUserLoggedIn();
    }

    public static String getResponseBody(Boolean loggedin, String redirectUrl, UserService userService){
        if(loggedin) return userService.getCurrentUser().getEmail();

        String loginUrl = userService.createLoginURL(redirectUrl);
        StringBuilder builder = new StringBuilder();
        builder.add("<center><a href=\"");
        builder.add(loginUrl);
        builder.add("\"><button style=\"background-color: cyan; border-radius:12px;\"><span style=\"color:white;\">Login</span></button/></a></center>");
        return builder.toString();
    }
}
