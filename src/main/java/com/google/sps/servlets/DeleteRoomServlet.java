package com.google.sps.servlets;

import com.google.sps.data.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Servlet to handle room deletion
@WebServlet("/delete-room")
public final class DeleteRoomServlet extends HttpServlet{
    private static final String ROOM_PARAMETER = "roomId";

    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Long roomId = Long.parseLong(req.getParameter(ROOM_PARAMETER));
        Room.deleteRoomFromId(roomId);
        res.setStatus(200);
    }
}
