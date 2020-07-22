package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.sps.data.*;

/** Servlet that takes in the user's inputted Room ID and directs them
    to the associated private room once ensurred that it's an actual room*/
@WebServlet("/sync-room")
public final class SyncServlet extends HttpServlet {
    private static final String UPDATE_STATE_PARAMETER = "userState";
    private static final String ROOM_ID_PARAMETER = "roomId";
    private static final String VIDEO_TIMESTAMP_PARAMETER = "timeStamp";
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        long roomId = Long.parseLong(req.getParameter(ROOM_ID_PARAMETER));
        Room updatedRoom = Room.fromRoomId(roomId);
        Video currentVideo = updatedRoom.getCurrentVideo();
        String responseBody = videoObjectToJsonString(currentVideo);
        res.setContentType(ServletUtil.JSON_CONTENT_TYPE);
        res.getWriter().println(responseBody);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        long roomId = Long.parseLong(req.getParameter(ROOM_ID_PARAMETER));
        Video.VideoState newState = Video.VideoState.fromInt(Integer.parseInt(req.getParameter(UPDATE_STATE_PARAMETER)));
        long currentVideoTimestamp = Long.parseLong(req.getParameter(VIDEO_TIMESTAMP_PARAMETER));

        Room syncRoom = Room.fromRoomId(roomId);

        if(newState == Video.VideoState.ENDED){
            syncRoom.changeCurrentVideo();
        } else {
            syncRoom.updateCurrentVideoState(newState, currentVideoTimestamp);
        }
        syncRoom.toDatastore();
        res.setStatus(200);
    }

    //Takes a video in and turns it into a JSON String with the necessary attributes
    private String videoObjectToJsonString(Video video){
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{\"timestamp\" : \"");
        jsonString.append(video.getCurrentTimeStamp() + "\" , ");
        jsonString.append("\"currentUrl\" : \"");
        jsonString.append(video.getUrl() + "\" , ");
        jsonString.append("\"currentState\" : \"");
        jsonString.append(video.getCurrentState().getValue() + "\"}");
        return jsonString.toString();
    }
}