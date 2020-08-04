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
        List<Member> members = Arrays.asList(Member.createNewMember("RJ"), Member.createNewMember("RMJ"), Member.createNewMember("@google.com"), Member.createNewMember("rossjohnson@google.com"));
        Room room = new Room(members);

        //Setup of user
        String user = "rossjohnson@google.com";

        Boolean actual = VerifyRoomServlet.isUserOnMemberList(user, room);

        Assert.assertTrue(actual);
   }

   @Test
   public void userIsNotAMember(){
        //Setup of Room
        List<Member> members = Arrays.asList(Member.createNewMember("RJ"), Member.createNewMember("RMJ"), Member.createNewMember("@google.com"));
        Room room = new Room(members);

        //Setup of user
        String user = "rossjohnson@google.com";

        Boolean actual = VerifyRoomServlet.isUserOnMemberList(user, room);

        Assert.assertFalse(actual);
   }
}
