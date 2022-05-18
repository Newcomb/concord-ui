package modelTest;




import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
		assertEquals(rl.getRoom(0).getOnline(ul).size(), 1);
		assertEquals(rl.getRoom(0).getOffline(ul).size(), 2);
		
		// Test viewing all users in room
		ArrayList<String> names = new ArrayList<>();
		names.add("johne");
		names.add("bob");
		names.add("janice");


		assertEquals(rl.getRoom(0).getMembers(ul), names);
		
		// Test total number of people server has
		assertEquals(rl.getRoom(0).getUserTable().size(), 3);
		
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
		
		
		
		
		
		// Game Log Testing 
		// Not testing for more than 4 users here as that will be taken care of in the JVMServerTest
		List<Integer> users = new ArrayList<Integer>() {{
			add(0);
			add(1);
		}};
		
		rl.getRoom(0).addGameChatLog("RSPLS", users);
		assertEquals(rl.getRoom(0).getChatLog(2).chatLogName, "RSPLS");
		
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().containsKey(0), true);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().containsKey(1), true);
		
		
		// Test all combinations of RSPLS
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Rock");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Rock"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Paper");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Paper"), true);
		
		// Check that player move resets after game restarts
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0), "Rock");
		
		// Check that you can still send messages
		rl.getRoom(0).addChat(2, 0, "Howdy");
		assertEquals(rl.getRoom(0).getChatLog(2).getChat(2).getMessage(), "Howdy");
		
		// Check that round win tracker reset after the game
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), 1);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), 1);
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Rock");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Rock"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Scissors");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Scissors"), true);
		
		// Check for the winner with two people and no extra entries
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), -1);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), 0);
		
		
		
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Rock");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Rock"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Lizard");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Lizard"), true);
		
		// Check for the winner with two people and no extra entries
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), -1);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), 0);
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Rock");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Rock"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Spock");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Spock"), true);
		
		// Check for the winner with two people and no extra entries
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), 0);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), -1);
		
		
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Paper");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Paper"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Scissors");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Scissors"), true);
		
		// Check for the winner with two people and no extra entries
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), 0);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), -1);
		
		
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Paper");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Paper"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Lizard");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Lizard"), true);
		
		// Check for the winner with two people and no extra entries
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), 0);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), -1);
		
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Paper");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Paper"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Spock");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Spock"), true);
		
		// Check for the winner with two people and no extra entries
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), -1);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), 0);
		
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Scissors");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Scissors"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Lizard");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Lizard"), true);
		
		// Check for the winner with two people and no extra entries
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), -1);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), 0);
		
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Scissors");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Scissors"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Spock");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Spock"), true);
		
		// Check for the winner with two people and no extra entries
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), 0);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), -1);
		
		
		// Check that you can send rock and it gets marked in player moves
		rl.getRoom(0).addChat(2, 0, "Lizard");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(0).equals("Lizard"), true);
		
		// Check that you can send paper and it gets marked in player moves
		rl.getRoom(0).addChat(2, 1, "Spock");
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getPlayerMoves().get(1).equals("Spock"), true);
		
		// Check for the winner with two people and no extra entries
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(1), -1);
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getRoundWinTracker().get(0), 0);
		
		List<String> moves = new ArrayList<String>() {{
			add("Rock");
			add("Spock");
			add("Scissors");
			add("Lizard");
			add("Paper");
		}};
		assertEquals(rl.getRoom(0).getChatLog(2).getGame().getMoves(), moves);
		
		
		
		// Test making the chatlog Deleted
		rl.getRoom(0).addChat(2, 0, "Delete");
		assertEquals(rl.getRoom(0).getChatLog(2).getVisible(), false);
		
		// Check that you cant still send messages
		rl.getRoom(0).addChat(2, 0, "Still works");
		// assertEquals(rl.getRoom(0).getChatLog(2).getChat(8),null);
	}

}
