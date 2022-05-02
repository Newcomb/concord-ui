package modelTest;




import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.RoomList;
import model.User;
import model.UserList;
import model.Role;

class ConcordDataTest
{
	User john;
	User bob;
	User janice;

	UserList ul;
	
	RoomList rl;
	
	@BeforeEach
	void setUp() throws Exception
	{
		// Add some test case users
		john = new User("john", "John Doe", "john.jpeg", true, "I am a freelance coder!", "HelloWorld");
		bob = new User("bob", "Bob Allen", null, false, "I am a freelance encoder!", "ILoveDogs");
		janice = new User("janice", "Janice Nelson", null, true, "I am a freelance decoder!", "CodingRocks");
		
		// Add a user list to hold test case users
		ul = new UserList();
		ul.addUser(john);
		ul.addUser(bob);
		ul.addUser(janice);
		
		// Create a room list to hold rooms
		rl = new RoomList();
		
		
		// Create some rooms
		rl.addRoom(john.createRoom("This room is for data science majors!", "GO DS!", true), john);
		rl.addRoom(bob.createRoom("This room is for computer scientists!", "GO CS!", false), bob);
		
		// Create a direct message between bob and janice
		// Will have roomID 2 and janice as other person
		bob.createDirectMessage(2, rl, ul);
		
		// Add some rooms to other people
		janice.addRoom(0, rl);
		// This room should not be added because it is private and janice is not invited
		janice.addRoom(1, rl);
		
		// Block a user and try to block twice
		janice.addBlocked(0);
	}

	@AfterEach
	void tearDown() throws Exception
	{
	}

	@Test
	void test()
	{
		// Test if the room has the correct user
		ArrayList<Integer> keys = new ArrayList<>();
		keys.add(0);
		assertEquals(rl.getRoom(0).getKeys(), keys);
		
		// Test if the room has the right permission for the user
		assertEquals(rl.getRoom(0).getUserRoleName(0), "Admin");
		
		// Test if direct message has correct users
		keys.clear();
		keys.add(1);
		keys.add(2);
		assertEquals(rl.getRoom(2).getKeys(), keys);
		
		keys.clear();
		keys.add(0);
		
		//Test if janice was able to add first room and not the second
		assertEquals(ul.getUser(2).getRooms(), keys);
		
		// Test that profile data is set properly
		assertEquals(ul.getUser(0).getProfileData(), "I am a freelance coder!");
		
		// Test that updating profile data works
		ul.getUser(0).setProfileData("I am not a freelance coder anymore!");
		assertEquals(ul.getUser(0).getProfileData(), "I am not a freelance coder anymore!");
		
		// Test Server logo
		assertEquals(rl.getRoom(0).getLogo(),"GO DS!");
		
		// Test server description
		assertEquals(rl.getRoom(0).getDescription(),"This room is for data science majors!");
		
		
		// Test that user current status is set
		assertEquals(ul.getUser(0).getStatus(), true);
		
		// Test that you can change user status
		ul.getUser(0).setStatus(false);
		assertEquals(ul.getUser(0).getStatus(), false);
		
		// Test if a user has been blocked
		assertEquals(ul.getUser(2).isBlocked(0), true);
		
		// Try to create a direct message with someone who blocked you
		ul.getUser(0).createDirectMessage(2, rl, ul);
		assertEquals(ul.getUser(0).getDirectMessages().size(), 0);
		
		// Test if blocked user is removed
		ul.getUser(2).removeBlocked(0);
		assertEquals(ul.getUser(2).isBlocked(0), false);
		
		// Test username
		assertEquals(john.getUsername(), "john");
		
		// Test username change
		john.setUsername("johne");
		assertEquals(john.getUsername(), "johne");
		
		// Test real name
		assertEquals(john.getRealName(), "John Doe");
		
		// Test real name change
		john.setRealName("John Do");
		assertEquals(john.getRealName(), "John Do");
		
		// Test password
		assertEquals(john.getPassword(), "HelloWorld");
		
		// Test password change
		john.setPassword("HelloSpace");
		assertEquals(john.getPassword(), "HelloSpace");
		
		// Test adding multiple channels
		rl.getRoom(0).addChatLog("Homework", 0, false);
		rl.getRoom(0).addChatLog("Studying", 0, false);
		assertEquals(rl.getRoom(0).getChatLogs().size(), 2);
		
		
		// Test adding a new user
		rl.getRoom(0).addUser(1);
		// assertEquals(rl.getRoom(0).getUserRoleName(1), "Noob");
		
		// Test chats
		rl.getRoom(0).getChatLog(0).addChat("Hello!", 1);
		rl.getRoom(0).getChatLog(1).addChat("Hello!", 1);
		
		// Test message
		assertEquals(rl.getRoom(0).getChatLog(0).getChat(0).getMessage(), "Hello!");
		assertEquals(rl.getRoom(0).getChatLog(1).getChat(0).getMessage(), "Hello!");
		// Test sender id
		assertEquals(rl.getRoom(0).getChatLog(0).getChat(0).getSenderID(), 1);
		assertEquals(rl.getRoom(0).getChatLog(1).getChat(0).getSenderID(), 1);
		
		// Test message pinning
		rl.getRoom(0).getChatLog(0).pinMessage(0);
		
		// Test getting pinned messages
		assertEquals(rl.getRoom(0).getChatLog(0).getPinned().size(), 1);
		
		// Test online offline users
		rl.getRoom(0).addUser(2);
		assertEquals(rl.getRoom(0).getOnline(ul).size(), 0);
		assertEquals(rl.getRoom(0).getOffline(ul).size(), 1);
		
		// Test viewing all users in room
		ArrayList<String> names = new ArrayList<>();
		names.add("johne");

		assertEquals(rl.getRoom(0).getMembers(ul), names);
		
		// Test total number of people server has
		assertEquals(rl.getRoom(0).getUserTable().size(), 1);
		
		// Test that non admin can leave
		rl.getRoom(0).leaveServer(2);
		assertEquals(rl.getRoom(0).getUserTable().containsKey(2), false);
		
		// Test that leave doesnt break if you call it on a random number
		rl.getRoom(0).leaveServer(3);
		
		// Test that admin cant leave
		rl.getRoom(0).leaveServer(0);
		
		// check that admin can private a channel
		rl.getRoom(0).setChatLogLocked(true, 0, 0);
		assertEquals(rl.getRoom(0).getChatLog(0).getLocked(), true);
		
		// Test inviting other users
		rl.getRoom(1).inviteUser(2, 1);
		assertEquals(rl.getRoom(1).getInviteStatus(2), true);
		
		
		// Test giving a new role 
		rl.getRoom(0).giveRole(0, 1, new Role("Nooberator", true, false, false, false, true, false));
		assertEquals(rl.getRoom(0).getUserRoleName(1), "Nooberator");

		// Test reply to message
		rl.getRoom(0).getChatLog(0).addReply("Hello!", 0, 0);
		assertEquals(rl.getRoom(0).getChatLog(0).getChat(1).getReplyToID(), 0);
		
		// Test that user has profile pic string
		assertEquals(ul.getUser(0).getProfilePic(), "john.jpeg");
	}

}
