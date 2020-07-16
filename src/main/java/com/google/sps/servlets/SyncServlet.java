package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that takes in the user's inputted Room ID and directs them
    to the associated private room once ensurred that it's an actual room*/
@WebServlet("/sync-room")
public final class JoinRoomServlet extends HttpServlet {
    private static final String UPDATE_STATE_PARAMETER = "userState";
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        long roomId =  req.getParameter("roomId");
        int newState = req.getParameter(UPDATE_STATE_PARAMETER);
        
    }
}