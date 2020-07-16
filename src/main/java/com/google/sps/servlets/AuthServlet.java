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
    public static final String REDIRECT_URL_PARAMETER = "redirectUrl";
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        if(ServletUtil.USER_SERVICE.isUserLoggedIn()){
            String userEmail = ServletUtil.USER_SERVICE.getCurrentUser().getEmail();
            String urlToRedirectToAfterUserLogsOut = "/";
            String logoutUrl = ServletUtil.USER_SERVICE.createLogoutURL(urlToRedirectToAfterUserLogsOut);
            res.setContentType(ServletUtil.JSON_CONTENT_TYPE);
            res.getWriter().println(ServletUtil.PARSER.toJson(userEmail));  
        }
        else{
            String urlToRedirectToAfterUserLogsIn = req.getParameter(REDIRECT_URL_PARAMETER);
            String loginUrl = ServletUtil.USER_SERVICE.createLoginURL(urlToRedirectToAfterUserLogsIn);
            res.setContentType(ServletUtil.HTML_CONTENT_TYPE);
            res.getWriter().println("<center><a href=\"" + loginUrl + "\"><button style=\"background-color: cyan; border-radius:12px;\"><span style=\"color:white;\">Login</span></button/></a></center>");
        }
    }
}
