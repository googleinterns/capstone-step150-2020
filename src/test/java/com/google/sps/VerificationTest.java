package com.google.sps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import com.google.sps.data.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.sps.servlets.*;
import java.util.*;
import java.lang.Long;

//Tests user Verification Logic
@RunWith(JUnit4.class)
public final class VerificationTest {

   @Test
   public void userIsaMember(){
        //Setup of Room
        List<Member> members = new LinkedList<Member>();
        members.add(Member.createNewMember("RJ"));
        members.add(Member.createNewMember("RMJ"));
        members.add(Member.createNewMember("@google.com"));
        members.add(Member.createNewMember("rossjohnson@google.com"));
        Room room = Room.createRoom(members);

        //Setup of user
        String user = "rossjohnson@google.com";

        Boolean expected = true;
        Boolean actual = VerifyRoomServlet.isUserOnMemberList(user, room);

        Assert.assertEquals(expected, actual);
   }

   @Test
   public void userIsNotAMember(){
        //Setup of Room
        List<Member> members = new LinkedList<Member>();
        members.add(Member.createNewMember("RJ"));
        members.add(Member.createNewMember("RMJ"));
        members.add(Member.createNewMember("@google.com"));
        Room room = Room.createRoom(members);

        //Setup of user
        String user = "rossjohnson@google.com";

        Boolean expected = false;
        Boolean actual = VerifyRoomServlet.isUserOnMemberList(user, room);

        Assert.assertEquals(expected, actual);
   }
}
