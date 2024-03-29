package modelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Admin;
import model.JVMClient;
import model.JVMServer;
import model.Moderator;
import model.RoomList;
import model.User;
import model.UserList;


class JVMServerTest
{

	JVMServer serv;
	User bob;
	UserList ul;
	RoomList rl;
	User janice;
	Registry registry;
	User denise;
	User janet;
	User ralph;
	JVMClient c;
	JVMClient c2;
	JVMClient c3;
	JVMClient c4; 
	JVMClient c5;
	
	@BeforeEach
	void setUp() throws Exception
	{
		ul = new UserList();
		rl = new RoomList();
	
		
		try
		{
			serv = JVMServer.startServer();
			registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Concord", serv);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bob = new User("bob", "Bob Allen", null, false, "I am a freelance encoder!", "ILoveDogs");
		janice = new User("janice", "Janice Nelson", null, true, "I am a freelance decoder!", "CodingRocks");
		denise = new  User("denise", "denise Nelson", null, true, "I am a freelance decoder!", "DeniseRocks");
		janet = new User("janet", "janet nelson", null, true, "I am a freelance person", "JanetRocks");
		ralph = new User("ralph", "ralph holms", null, true, "I am a human", "RalphRocks");
		
		ul.addUser(bob);
		ul.addUser(janice);
		ul.addUser(denise);
		ul.addUser(janet);
		ul.addUser(ralph);
		
		rl.addRoom(bob.createRoom("This room is for data science majors!", "GO DS!", true), bob);
		rl.getRoom(0).inviteUser(1, 0);
		rl.getRoom(0).addUser(1);
		rl.getRoom(0).inviteUser(2, 0);
		rl.getRoom(0).inviteUser(3, 0);
		rl.getRoom(0).inviteUser(4, 0);
		
		rl.getRoom(0).addChatLog("Gaming", 0, false);
		rl.getRoom(0).getChatLog(0).addChat("Hello!", 0);
		serv.setRl(rl);
		serv.setUl(ul);
		c = new JVMClient();
		c2 = new JVMClient();
		c3 = new JVMClient();
		c4 = new JVMClient();
		c5 = new JVMClient();
	}

	@AfterEach
	void tearDown() throws Exception
	{
		try
		{
			Registry rmiregistry = LocateRegistry.getRegistry(1099);
			rmiregistry.unbind("Concord");
		}
		catch (RemoteException e)
		{
			fail(e.getStackTrace().toString());
		}
	}

	/* 
	 * Tested so far
	 * 1. User calling server methods before authentification
	 */
	@Test
	void test() throws RemoteException
	{
		/*
		 * Test Methods Before Auth
		 */
		// Test creating a room before the user has been assigned
		assertEquals(c.createRoom("New", "Bobs room", false), false);
		
		// Test creating a chat log before auth
		assertEquals(c.createChatLog("New", 0, false), false);
		
		// Test that you cant remove a chatlog before auth
		assertEquals(c.removeChatLog(0, 0), false);
		
		// Test that you cannot delete chat
		assertEquals(c.deleteChat(0, 0, 0), false);
		
		// Check that you cant get chatlog before auth
		assertEquals(c.getChatLog(0, 0), null);
		
		// Check that new room does not exist in server
		assertEquals(serv.getRoom(1), null);
		
		// Check that you cant get roomList if not logged in
		assertEquals(c.getAllRooms(), null);
		
		// Check that you cant add room if you are not logged in
		assertEquals(c.addRoom(0), false);
		
		// Check that user cant add another user to room
		assertEquals(c.addUserToRoom(1, 0), false);
		
		// Check that you cant remove a user when not logged in
		assertEquals(c.removeUserFromRoom(1, 0), false);
		
		// Check that you cant set room descriptoin
		assertEquals(c.setRoomDescription(0, "I am not a freelance encoder"), false);
		
		// CHeck that you cant change the room logo 
		assertEquals(c.setRoomLogo(0, "New Logo"), false);
		
		// CHeck that you cant set room type
		// assertEquals(c.setRoomType(false, 0), false);
		
		// Check that you cant give role before log in
		assertEquals(c.giveRole(1, 0, new Moderator()), false);
		
		// CHeck that you cant invite usre to room before auth
		assertEquals(c.inviteUserToRoom(1, 0), false);
		
		// check that you ant set the chat log as locked
		assertEquals(c.setChatLogLocked(0, 0, true), false);
		
		// Check that you cant add chat
		assertEquals(c.addChat(0, 0, "Hello"), false);
		
		//Check that you cant pin message
		assertEquals(c.pinMessage(0, 0, 0), false);
		
		// check that you cant get pinned messages 
		assertEquals(c.getPinnedMessages(0, 0), null);
		
		// Check that you cant get rooms 
		assertEquals(c.getRoom(0), null);
		
		// Check that you cant get online 
		assertEquals(c.getOnline(0), null);
		
		// Check that you cant get offline 
		assertEquals(c.getOffline(0), null);
		
		// Check that you cant get room logo
		assertEquals(c.getRoomLogo(0), null);
		
		// check that you cant get description
		assertEquals(c.getRoomDescription(0), null);
		
		// check that you cant get your role wihtout logging in
		assertEquals(c.getRole(0, 0), null);
		
		// check that you cant change profile if you are not logged in 
		assertEquals(c.setProfilePic("hello.jpg"), false);
		
		// Check that you cant change profile data before log in
		assertEquals(c.setProfileData("I like cats"), false);
		
		// Check that you cant set status when not  logged in
		assertEquals(c.setStatus(false), false);

		
		
		/*
		 * Test Authorization
		 */
		// Test trying to log the user in with the wrong password
		assertEquals(c.authenticate("bob", "ILoveCats"), false);
		
		// Test trying to log the wrong username in with 
		assertEquals(c.authenticate("ron", "password"),false);
		
		// Test log in with user
		assertEquals(c.authenticate("bob", "ILoveDogs"), true);
		
		// Test another log in 
		assertEquals(c2.authenticate("janice", "CodingRocks"), true);
		
		// Test another log in 
		assertEquals(c3.authenticate("denise", "DeniseRocks"), true);
		
		assertEquals(c4.authenticate("janet", "JanetRocks"), true);
		
		assertEquals(c5.authenticate("ralph", "RalphRocks"), true);
		
		
		/*
		 * Test Methods After Authorization 
		 */
		// Test creating a room wihen there is a user assigned
		assertEquals(c.createRoom("New", "Bobs room", true), true);
		
		// Check that new room exists in server
		assertEquals(serv.getRoom(1).getDescription(), "New");
		
		// Check that admin can change description
		assertEquals(c.setRoomDescription(1, "Better Desc"), true);
		
		// Check that description changed
		assertEquals(c.getRoomDescription(1), "Better Desc");
		
		// Check that user gets logged in 
		assertEquals(c.checkLogInStatus(), true);

		// Test creating a chat log 
		assertEquals(c.createChatLog("New", 0, false), true);
		
		// Check that chatlog was made
		assertEquals(c.getChatLog(1, 0).getChatLogName(), "New");

		// Test removing chatlog
		assertEquals(c.removeChatLog(0, 1), true);
		
		// Check that chat log was removed
		assertEquals(c.getChatLog(1, 0), null);
		
		// CHeck that user without permisisions cannot remove chatLog
		assertEquals(c2.removeChatLog(0, 0), false);
		
		// Check that user without permissions cannot create chatlog
		assertEquals(c2.createChatLog("New", 0, false), false);

		// Test that admin can delete chat
		assertEquals(c.deleteChat(0, 0, 0), true);
		
		// Test that chat was actually deleted
		assertEquals(c.getChatLog(0, 0).getChat(0).getDeleted(), true);
		
		// Test that you can add chat
		assertEquals(c.addChat(0, 0, "New Chat"), true);
		
		assertEquals(c.pinMessage(1, 0, 0), true);

		// Test that chat was created
		assertEquals(c.getChatLog(0, 0).getChat(1).getMessage(),"New Chat");
		
		// Test that someone with noob cannot delete chat
		assertEquals(c2.deleteChat(0, 0, 1), false);
		
		// Test that chat is still there
		assertEquals(c.getChatLog(0, 0).getChat(1).getMessage(), "New Chat");
		
		// CHeck that noob cant change description
		assertEquals(c2.setRoomDescription( 0, "New"), false);
		
		// Check that noob cant set room logo!!
		assertEquals(c2.setRoomLogo(0, "Bob sucks"), false);
		
		// Check that noob cant set room type
		assertEquals(c2.setRoomType(true, 0), false);
		
		// check that admin can
		assertEquals(c.setRoomType(true, 1), true);
		
		
		// Check that noob can invite user to room
		assertEquals(c2.inviteUserToRoom(2, 0), true);
		
		// Check that noob cant set chat log locked
		assertEquals(c2.setChatLogLocked(0, 0, true), false);
		
		// Check that admin can set chat locked
		assertEquals(c.setChatLogLocked(0, 0, true), true);
		
		// check that a message can be pinned
		assertEquals(c2.pinMessage(1, 0, 0), false);
		
		// show that message was pinned
		assertEquals(c2.getPinnedMessages(0, 0).size(), 1);
		
		// Test that role can be updated
		assertEquals(c.giveRole(1, 0, new Moderator()), true);
		
		// CHeck that moderator cant change description
		assertEquals(c2.setRoomDescription(0, "New Desc"), false);
		
		// Test that moderator can delete chat
		assertEquals(c2.deleteChat(0,0,1), true);
		
		// Test that moderator cant give itself a higher role
		assertEquals(c2.giveRole(1, 0, new Admin()), false);
		
		// Check that you can get roomList
		assertEquals(c.getAllRooms().getSize(), 2);
		
		// Check that you cant add room if you are already in it
		assertEquals(c2.addUserToRoom(1, 0), false);
		
		// Check that you cant add room if you are in the room
		assertEquals(c.addUserToRoom(0, 0), false);
		
		
		// Check that you cant add room if you are already in it
				assertEquals(c2.addUserToRoom(0, 0), false);
		
		
		// CHeck that you cant invite usre to room before auth
		assertEquals(c.inviteUserToRoom(1, 0), true);
		
		// Check that if you are invited you can add a room
		// assertEquals(c2.addUserToRoom(1, 1), true);
		
	
		// Check that you cant remove people if you arent admin
		assertEquals(c2.removeUserFromRoom(0, 0), false);
		
		
		System.out.println("Below");
		System.out.println(bob.getRooms());
		// Check that you can remove people if you are admin
		assertEquals(c.removeUserFromRoom(1, 0), true);
		
		
		
		System.out.println("Below");
		System.out.println(bob.getRooms());
		
		// Check that they are removed
		assertEquals(c.getRoom(0).getUserTable().containsKey(1), false);
		
		// Add user back
		assertEquals(c2.addRoom(0), true);
		
		// Check that user can remove themselves from room
		assertEquals(c2.removeUserFromRoom(1, 0), true);
		
		// Check that user no longer has room
		assertEquals(c2.getAllRooms().getRoom(0).getUserTable().containsKey(1), false);
				
		// Add user back
		assertEquals(c2.addRoom(0), true);
		
		// Check that user has room
		assertEquals(c.getAllRooms().getRoom(0).getUserTable().containsKey(1), true);
		
		// Show that you can repy to a chat
		assertEquals(c2.chatLogReply("I love it!", 0, 0, 1), true);

		
		// check that a message can be pinned
		assertEquals(c.pinMessage(2, 0, 0), true);
		
		
		
		// Show that it created the reply
		assertEquals(c2.getChatLog(0, 0).getChat(2).getReplyToID(), 1);
		
		// Check online 
		assertEquals(c.getOnline(0).size(), 1);

		// Check that you cant get offline 
		assertEquals(c.getOffline(0).size(),  1);

		// check that you can change the profile picture
		assertEquals(c.setProfilePic("hello.jpg"), true);

		// Check that you can set profile data
		assertEquals(c.setProfileData("I like cats"), true);
		
		// Check that you can set status
		assertEquals(c.setStatus(false),true);
		
		
		/*
		 * Check Client side updates to Server
		 */
		// Check that notify is working
		assertEquals(c.getUserList().getUser(0).getProfileData(), "I like cats");
		
		// chekc that notify is working
		assertEquals(c.getUserList().getUser(0).getProfilePic(), "hello.jpg");
		
		// Check that notify is working
		assertEquals(c.getUserList().getUser(0).getStatus(), false);
		
		
		
		/*
		 * Check server side notifications to the client are working
		 * getRL returns the roomlist that is stored on the client here
		 */
		assertEquals(c.createDirectMessage(1), true);
		
		// Shows that there are 2 people in dm
		assertEquals(c.getOnline(2).size(), 1);
		assertEquals(c.getOffline(2).size(), 1);
		assertEquals(c.getRoom(2).getUserTable().get(1).getRoleName(), "Admin");
		
		assertEquals(c.getRoom(2).getDescription(), "Direct Message");
		
		assertEquals(c.createChatLog("New DM", 2, false), true);
		assertEquals(c.getRoom(2).getChatLog(0).getChatLogName(), "New DM");
		
		// Check that notify is working
		assertEquals(c.blockUser(2), true);
		assertEquals(c.getUserList().getUser(0).getBlocked().contains(2), true);
		
		assertEquals(c.createDirectMessage(2), false);
		
		// Check that rl is being updated client side
		assertEquals(c.addChat(0, 0, "Hello"), true);
		
		// Check that other client is getting update for new DM
		assertEquals(c2.rl.getRoom(0).getChatLog(0).getChat().get(3).message, "Hello");
		
		// Check that message sends in chatlog
		assertEquals(c2.getRoom(0).getChatLog(0).getChat().get(3).message, "Hello");
		
		// Check that direct messages grows on the client side as well
		assertEquals(c2.getU().directMessages.size(), 2);
		
		// Check all room update scenarios
		assertEquals(c.giveRole(1, 0, new Admin()), true);
		assertEquals(c.getRl().getRoom(0).getUserRoleName(1), "Admin");
		
		assertEquals(c.deleteChat(0, 0, 3), true);
		assertEquals(c.getRl().getRoom(0).getChatLog(0).getChat(3).deleted, true);
		
		// Unblock user
		assertEquals(c.unblockUser(2), true);
		assertEquals(c.getUserList().getUser(0).getBlocked().contains(2), false);
		assertEquals(c.unblockUser(2), false);
		
		// These below were done previously
		
		assertEquals(c.getRl().getRoom(0).getChatLog(0).getPinned().size(), 2);
		
		assertEquals(c.getRl().getRoom(0).getChatLog(0).getLocked(), true);
		
		assertEquals(c.getRl().getRoom(1).getDescription(), "Better Desc");
		
		assertEquals(c.getRl().getRoom(1).getType(), true);
		
		assertEquals(c.getRl().getRoom(0).getChatLog(0).getChat().get(2).replyToID, 1);
		
		assertEquals(c2.createRoom("Hello", "ILC", true), true);
		

		// assertEquals(c2.getUserRooms().remove(1), 1);
		
		assertEquals(c2.getUserRooms().contains(1), false);
		
		assertEquals(c2.createRoom("Hello", "ILD", true), true);
		
		
		
		
		
		
		// Test GameLog
		
		// Test setting game log status
		
		assertEquals(c3.addRoom(0), true); 
		
		// Test trying to create a gameLog with no gamers
		assertEquals(c.createGameChatLog("RSPLS", 0), false);
		
		// change a users status to wanting to game
		assertEquals(c.changeGameStatus(0), true);
		
		// Test trying to create a gameLog with one person 
		assertEquals(c.createGameChatLog("RSPLS", 0), false);
		
		// Change more users to wantting to game
		assertEquals(c2.changeGameStatus(0), true);
		
		assertEquals(c3.changeGameStatus(0), true);
		
		// Check that creating a gamelog works now
		assertEquals(c.createGameChatLog("RSPLS", 0), true);
		
		assertEquals(c.getRoom(0).getChatLog(2).getChatLogName(), "RSPLS");
		
		
		// Check initial messages
		assertEquals(c.getRoom(0).getChatLog(2).getChat(0).getMessage(), "Players: bob janice denise ");
		
		assertEquals(c2.getRoom(0).getChatLog(2).getChatLogName(), "RSPLS");
		
		assertEquals(c2.getRoom(0).getChatLog(2).getChat(1).getMessage(), "Type: \"Delete\" to end the game");
		
		assertEquals(c2.getRoom(0).getChatLog(2).getChat(2).getMessage(), "Moves: \"Rock\" \"Spock\" \"Scissors\" \"Lizard\" \"Paper\" ");
		
		// Check that inputting all the same values gives everyone as winner
		
		assertEquals(c3.addChat(2, 0, "Rock"), true);
		
		assertEquals(c2.addChat(2, 0, "Rock"), true);
		
		assertEquals(c.addChat(2, 0, "Rock"), true);
		
		// Check that winner message gets sent when all three players submit
		assertEquals(c.getRoom(0).getChatLog(2).getChat(6).getMessage(), "Winner: bob janice denise ");
		
		// Check that visible for values are correct
		
		assertEquals(c.getRoom(0).getChatLog(2).getChat(4).getVisibleFor(), 1);
		
		assertEquals(c.getRoom(0).getChatLog(2).getChat(5).getVisibleFor(), 0);
		
		assertEquals(c.getRoom(0).getChatLog(2).getChat(3).getVisibleFor(), 2);
		
		assertEquals(c.getRoom(0).getChatLog(2).getChat(6).getVisibleFor(), -1);
		
		// Check that adding a normal chat wihtout a move is visible for everyone
		assertEquals(c.addChat(2, 0, "Hello"), true);
		assertEquals(c.getRoom(0).getChatLog(2).getChat(7).getVisibleFor(), -1);
		
		// Check that you can play more rounds after conclusion
		assertEquals(c3.addChat(2, 0, "Rock"), true);
		
		assertEquals(c2.addChat(2, 0, "Lizard"), true);
		
		assertEquals(c.addChat(2, 0, "Spock"), true);
		
		// If everyone is cancelled out print no winner
		assertEquals(c.getRoom(0).getChatLog(2).getChat(11).getMessage(), "Winner: ");
		
		// Check that you can play more rounds after conclusion
		assertEquals(c3.addChat(2, 0, "Rock"), true);
		
		assertEquals(c2.addChat(2, 0, "Scissors"), true);
		
		assertEquals(c.addChat(2, 0, "Spock"), true);
		
		assertEquals(c.getRoom(0).getChatLog(2).getChat(15).getMessage(), "Winner: bob ");
		
		
		// Check that you can play more rounds after conclusion and that both winners will be rewarded
		assertEquals(c3.addChat(2, 0, "Rock"), true);
		
		assertEquals(c2.addChat(2, 0, "Spock"), true);
		
		assertEquals(c.addChat(2, 0, "Spock"), true);
		
		assertEquals(c.getRoom(0).getChatLog(2).getChat(19).getMessage(), "Winner: bob janice ");
		
		// The logic from the previous tests works and it works given three options so I am not testing all combinations here.
		
		// Test that delete works
		assertEquals(c3.addChat(2, 0, "Delete"), true);
		
		assertEquals(c3.getRoom(0).getChatLog(2).getVisible(), false);
		
		// Add more users to put in the game and change game status
		assertEquals(c4.addRoom(0), true);
		assertEquals(c5.addRoom(0), true);
		assertEquals(c4.changeGameStatus(0), true);
		assertEquals(c5.changeGameStatus(0), true);
		
//		// Check that creating a gamelog works now
		assertEquals(c.createGameChatLog("RSPLS", 0), true);
		
		
		List<Integer> users = new ArrayList<Integer>() {{
			for (int id: c.getRoom(0).getChatLog(3).getGame().getPlayerMoves().keySet()) {
				add(id);
			}
		}};
		
		// Tests that all permutations work and that randomization works
		try
		{
			if (users.indexOf(4) == -1) {
				testRSPLS(users,c, c2, c3, c4);
			}
			else if (users.indexOf(3) == -1){
				testRSPLS(users,c, c2, c3, c5);
			}
			else if (users.indexOf(2) == -1){
				testRSPLS(users,c, c2, c4, c5);
			}
			else if (users.indexOf(1) == -1){
				testRSPLS(users,c, c3, c4, c5);
			}
			else if (users.indexOf(0) == -1){
				testRSPLS(users,c2, c3, c4, c5);
			}
		} catch (RemoteException | InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Test that delete works
		assertEquals(c3.addChat(3, 0, "Delete"), true);
		
		assertEquals(c4.changeGameStatus(0), true);
		assertEquals(c5.changeGameStatus(0), true);
		
		
		
//		// Test that XML has been keeping up with changes above
		JVMServer servCopy = JVMServer.readFromDisk();
		
		assertEquals(serv.rl.getRoomTracker(), servCopy.rl.getRoomTracker());
		
		assertEquals(serv.equals(servCopy), true);
		
		
		
		

		
		
		
		 
		/*
		 * Test LogOut
		 */
		// Check that log out works
		assertEquals(c.logOut(), true);
		
		// Check that user gets removed from client
		assertEquals(c.checkLogInStatus(), false);
		
		
		
	}
	
	
	// This method checks every possible combination with 4 people and works on the randomly chosen people
	public void testRSPLS(List<Integer> users, JVMClient c, JVMClient c2, JVMClient c3, JVMClient c4) throws RemoteException, InterruptedException {
		List<String> moves = new ArrayList<String>() {{
			add("Rock");
			add("Paper");
			add("Scissors");
			add("Lizard");
			add("Spock");
		}};
		
		List<String> moves1 = new ArrayList<String>() {{
			add("Paper");
			add("Scissors");
			add("Lizard");
			add("Spock");
		}};
		
		List<String> moves2 = new ArrayList<String>() {{
			add("Scissors");
			add("Lizard");
			add("Spock");
		}};
		
		List<String> moves3 = new ArrayList<String>() {{
			add("Lizard");
			add("Spock");
		}};
		
		int chatTracker = 2;
		for (String s: moves) {
			
			for (String s1: moves1) {
				
				for (String s2: moves2) {
					
					for (String s3: moves3) {
						
							assertEquals(c.addChat(3, 0, s), true);
							assertEquals(c2.addChat(3, 0, s1), true);
							assertEquals(c3.addChat(3, 0, s2), true);
							assertEquals(c4.addChat(3, 0, s3), true);
							chatTracker = chatTracker + 4;
							HashMap<Integer, String> playerMoves = new HashMap<Integer, String>(){{;
								put(c.getU().getUserID(), s);
								put(c2.getU().getUserID(), s1);
								put(c3.getU().getUserID(), s2);
								put(c4.getU().getUserID(), s3);
							}};
							HashMap<Integer, Integer> roundWinTracker = c.getRoom(0).getChatLog(3).getGame().getRoundWinTracker();
							for (int key: playerMoves.keySet()) {
								roundWinTracker.put(key, 0);
								for (int secKey: playerMoves.keySet()) {
									if (playerMoves.get(key).equals("Rock")){
										if (playerMoves.get(secKey).equals("Spock") || playerMoves.get(secKey).equals("Paper")) {
											roundWinTracker.put(key, -1);
										}
									}
									if (playerMoves.get(key).equals("Paper")) {
										if (playerMoves.get(secKey).equals("Scissors") || playerMoves.get(secKey).equals("Lizard")) {
											roundWinTracker.put(key, -1);
										}
									}
									if (playerMoves.get(key).equals("Scissors")) {
										if (playerMoves.get(secKey).equals("Rock") || playerMoves.get(secKey).equals("Spock")) {
											roundWinTracker.put(key, -1);
										}
									}
									if (playerMoves.get(key).equals("Lizard")) {
										if (playerMoves.get(secKey).equals("Scissors") || playerMoves.get(secKey).equals("Rock")) {
											roundWinTracker.put(key, -1);
										}
									}
									if (playerMoves.get(key).equals("Spock")) {
										if (playerMoves.get(secKey).equals("Paper") || playerMoves.get(secKey).equals("Lizard")) {
											roundWinTracker.put(key, -1);
										}
									}
								}
							}
	
							chatTracker++;
							String winners = "Winner: ";
							int track = 0;
							for (int key: roundWinTracker.keySet()) {
								if (roundWinTracker.get(key) == 0) {
									winners = winners + c.getUserList().getUser(key).getUsername() + " ";

									
								}
	
							}
							assertEquals(c.getRoom(0).getChatLog(3).getChat(chatTracker).toString(), chatTracker + " : " + winners);
							
						
					}
				}
			}
		}
	}

}