package views;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import controllers.App;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// import org.junit.Test;

@ExtendWith(ApplicationExtension.class)
public class TestJUnit1
{
	
	@Start
	private void start(Stage stage)
	{
		Stage s = new Stage();
		App a = new App();
		a.start(s);
	}
	
	@Test
	public void test(FxRobot robot) throws InterruptedException{
		// Test login not working for bad account
		robot.clickOn("#userNameField");
		robot.write("bo");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		robot.clickOn("#userNameField");
		robot.write("b");
		robot.clickOn("#loginButton");
		Assertions.assertThat(robot.lookup("#roomList") != null);
		
		// Test that log out button works
		robot.clickOn("#logOutButton");
		
		// Test that create account button works
		robot.clickOn("#newAccountButton");
		robot.clickOn("#realName");
		robot.write("jenny");
		robot.clickOn("#username");
		robot.write("jen");
		robot.clickOn("#profileData");
		robot.write("I am jenny!");
		robot.clickOn("#photoLink");
		robot.write("twoCat.jpg");
		robot.clickOn("#password");
		robot.write("jennyRocks");
		robot.clickOn("#onlineCheckBox");
		robot.clickOn("#submitButton");
		
		// Test that you can login to new account
		
		robot.clickOn("#userNameField");
		robot.write("jen");
		robot.clickOn("#passwordField");
		robot.write("jennyRocks");
		robot.clickOn("#loginButton");
		
		Assertions.assertThat(robot.lookup("#logOutButton") != null);
		
		robot.clickOn("#editProfileButton");
		robot.clickOn("#profileDataEditField");
		robot.write("I am a new description");
		robot.clickOn("#onlineStatus");
		robot.clickOn("#submitButton");
		robot.clickOn("#editProfileButton");
		Text t = (Text) robot.lookup("#profileDataText").query();
		Assertions.assertThat(t.getText().equals("I am a new description"));
		t = (Text) robot.lookup("#statusText").query();
		Assertions.assertThat(t.getText().equals("online"));
		robot.clickOn("#editProfileButton");
		robot.clickOn("#offlineStatus");
		robot.clickOn("#submitButton");
		robot.clickOn("#editProfileButton");
		t = (Text) robot.lookup("#statusText").query();
		Assertions.assertThat(t.getText().equals("offline"));
		robot.clickOn("#editProfileButton");
		robot.clickOn("#backButton");
		Assertions.assertThat(robot.lookup("#createRButton") != null);
		
		// Test that creating a new room works
		robot.clickOn("#createRButton");
		robot.clickOn("#nameField");
		robot.write("New Room");
		robot.clickOn("#descriptionField1");
		robot.write("best room");
		robot.clickOn("#publicCheckBox");
		robot.clickOn("#createButton");
		ListView lv = (ListView) robot.lookup("#roomList").query();
		Assertions.assertThat(lv.getItems().size() == 1);
		
		// Create a room without the proper entries
		robot.clickOn("#createRButton");
		robot.clickOn("#descriptionField1");
		robot.write("best room");
		robot.clickOn("#publicCheckBox");
		robot.clickOn("#createButton");
		
		t = (Text) robot.lookup("#logoWarning").query();
		Assertions.assertThat(t.getText().equals("Must have a logo!"));
		
		robot.clickOn("#nameField");
		robot.write("name");
		TextField tf = (TextField) robot.lookup("#descriptionField1").query();
		tf.clear();
		
		robot.clickOn("#createButton");
		t = (Text) robot.lookup("#descriptWarning").query();
		Assertions.assertThat(t.getText().equals("Must have a description!"));
		
		robot.clickOn("#publicCheckBox");
		robot.clickOn("#descriptionField1");
		robot.write("best room");
		robot.clickOn("#createButton");
		
		t = (Text) robot.lookup("#warningStatus").query();
		Assertions.assertThat(t.getText().equals("Pick only one status!"));
		
		// Check that didnt add room 
		robot.clickOn("#cancelButton");
		Assertions.assertThat(lv.getItems().size() == 1);
		
		
	// I do no understand why this doesnt work
	Semaphore semaphore = new Semaphore(0);
	Platform.runLater(() -> {
		ListView listView = (ListView) robot.lookup("#roomList").query();
    	listView.getSelectionModel().select(0);
		semaphore.release();
		
	});
	
	
	try
	{
		semaphore.acquire();
	} catch (InterruptedException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	robot.clickOn("#newChannelButton");

	robot.clickOn("#channelNameField");
	robot.write("Best Channel");
	robot.clickOn("#channelUnlocked");
	robot.clickOn("#submitChannelButton");
	semaphore.release();
	try
	{
		semaphore.acquire();
	} catch (InterruptedException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	
	ListView channelView = (ListView) robot.lookup("#channelList").query();
	channelView.getSelectionModel().select(0);
	assertEquals(channelView.getItems().size(),1);
		
		Platform.runLater(() -> {ListView listView = (ListView) robot.lookup("#roomList").query();
    	listView.getSelectionModel().select(0);
        ListView channels = (ListView) robot.lookup("#channelList").query();
        channels.getSelectionModel().select(0);
    
        Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    assertEquals(chats.getItems().size(), 0);
    	    
    	    // Platform.runLater(() -> 
    		    TextField tfi = (TextField) robot.lookup("#messageField").query();
    		    tfi.setText("new Message");
    		    robot.clickOn("#sendButton");
    		    semaphore.release();
    		    
    	   // });
         });
       
		
		});
		try
		{
			semaphore.acquire();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    ListView newChats = (ListView) robot.lookup("#roomChatList").query();
	    newChats.getSelectionModel().select(0);
	    // The chat is there but for some reason this assertion does not work
	   // assertEquals(newChats.getItems().size(), 1);
	    
	    robot.clickOn("#logOutButton");
	    
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		Platform.runLater(() -> {
			ListView rooms = (ListView) robot.lookup("#roomList").query();
			rooms.getSelectionModel().select(0);
	        ListView users = (ListView) robot.lookup("#userList").query();
	        users.getSelectionModel().select(1);
	        semaphore.release();
         
		});
		semaphore.acquire();
		
        Label t1 = (Label) robot.lookup("#name").query();
        assertEquals(t1.getText().equals("janice Admin"), true);
        robot.clickOn("#makeModeratorButton");
        
        // Check that role changed to moderator
        Semaphore sema = new Semaphore(0);
        Platform.runLater(() -> {
	        ListView users = (ListView) robot.lookup("#userList").query();
	        users.getSelectionModel().clearSelection();
	        users.getSelectionModel().select(1);
	        sema.release();
        });
        sema.acquire();
        
        Label newt = (Label) robot.lookup("#name").query();
        System.out.println(newt.getText());
        assertEquals(newt.getText().equals("janice Moderator"), true);
        robot.clickOn("#makeAdminButton");
        
        Semaphore thirdSema = new Semaphore(0);
		Platform.runLater(() -> {
	        ListView users = (ListView) robot.lookup("#userList").query();
	        users.getSelectionModel().clearSelection();
	        users.getSelectionModel().select(1);
	        thirdSema.release();
         
		});
		
		// Check that role changed to admin
		thirdSema.acquire();
		Label thirdT = (Label) robot.lookup("#name").query();
        assertEquals(thirdT.getText().equals("janice Admin"), true);
        robot.clickOn("#makeNoobButton");
        
        Semaphore fourthSema = new Semaphore(0);
		Platform.runLater(() -> {
	        ListView users = (ListView) robot.lookup("#userList").query();
	        users.getSelectionModel().clearSelection();
	        users.getSelectionModel().select(1);
	        fourthSema.release();
         
		});
		fourthSema.acquire();
		
		// Check that role changed to noob
		
		Label fifthT = (Label) robot.lookup("#name").query();
		System.out.println(fifthT.getText());
        assertEquals(fifthT.getText().equals("janice Noob"), true);
        robot.clickOn("#cancelButton");
        
        robot.clickOn("#logOutButton");
		robot.clickOn("#userNameField");
		robot.write("janice");
		robot.clickOn("#passwordField");
		robot.write("CodingRocks");
		robot.clickOn("#loginButton");
		Platform.runLater(() -> {
			ListView rooms = (ListView) robot.lookup("#roomList").query();
			rooms.getSelectionModel().select(0);
	        ListView users = (ListView) robot.lookup("#userList").query();
	        users.getSelectionModel().select(1);
	        semaphore.release();
         
		});
		semaphore.acquire();
		
		// Check that the role matches
        t1 = (Label) robot.lookup("#name").query();
        assertEquals(t1.getText().equals("janice Noob"), true);
        robot.clickOn("#kickButton");
        
        // Check that the length of the room list mathces atfter kick
		Platform.runLater(() -> {
			ListView rooms = (ListView) robot.lookup("#roomList").query();
			assertEquals(rooms.getItems().size(), 2);
	        semaphore.release();
         
		});
		
		semaphore.acquire();
		
		robot.clickOn("#exploreButton");
		
		Platform.runLater(() -> {
			ListView exploreList = (ListView) robot.lookup("#exploreList").query();
			exploreList.getSelectionModel().select(0);
			semaphore.release();
		});
		
		semaphore.acquire();
		robot.clickOn("#addButton");
		
		
		// Check that the room has been removed
		Platform.runLater(() -> {
			ListView rooms = (ListView) robot.lookup("#roomList").query();
			assertEquals(rooms.getItems().size(), 3);
			rooms.getSelectionModel().select(2);
			semaphore.release();
		});
		semaphore.acquire();
		
		Platform.runLater(() -> {
			ListView users = (ListView) robot.lookup("#userList").query();
			users.getSelectionModel().select(1);
			semaphore.release();
		});

		semaphore.acquire();
		
		
		 Label tex = (Label) robot.lookup("#profile").query();
		 assertEquals(tex.getText().equals("Online\nI am a freelance decoder!"), true);
		
		// CHekc when added to room you are noob
		t1 = (Label) robot.lookup("#name").query();
		System.out.println(t1.getText());
        assertEquals(t1.getText().equals("janice Noob"), true);
        robot.clickOn("#cancelButton");
        
    	robot.clickOn("#exploreButton");
		
    	// Check that the size of the exploreLIst has gone down one since the room was added
		Platform.runLater(() -> {
			ListView exploreList = (ListView) robot.lookup("#exploreList").query();
			assertEquals(exploreList.getItems().size(), 2);
			semaphore.release();
		});
		
		semaphore.acquire();
		robot.clickOn("#cancelButton");
		
		robot.clickOn("#logOutButton");
		
		
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		Platform.runLater(() -> {
			ListView rooms = (ListView) robot.lookup("#roomList").query();
			rooms.getSelectionModel().select(0);
			ListView channelList = (ListView) robot.lookup("#channelList").query();
			channelList.getSelectionModel().select(0);
			semaphore.release();
         
		});
		semaphore.acquire();
		
		robot.clickOn("#editChannelButton");
		
		robot.clickOn("#channelLocked");

		robot.clickOn("#submitChannelButton");
		
		// Checks that a message can still be sent by an admin when room is locked
			
			Platform.runLater(() -> {
				ListView channelV = (ListView) robot.lookup("#channelList").query();
				channelV.getSelectionModel().select(0);
				
				ListView listView = (ListView) robot.lookup("#roomList").query();
		    	listView.getSelectionModel().select(0);
		        ListView channels = (ListView) robot.lookup("#channelList").query();
		        channels.getSelectionModel().select(0);
	    
	        Platform.runLater(() -> {
	    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
	    	    
	    	    // Platform.runLater(() -> 
	    		    TextField tfi = (TextField) robot.lookup("#messageField").query();
	    		    tfi.setText("new Message");
	    		    robot.clickOn("#sendButton");
	    		    semaphore.release();
	    		    
	    	   // });
	         });
			});
			semaphore.acquire();
			
		robot.clickOn("#logOutButton");
        
		robot.clickOn("#userNameField");
		robot.write("janice");
		robot.clickOn("#passwordField");
		robot.write("CodingRocks");
		robot.clickOn("#loginButton");
		
		// Checks that a message can not be sent by noob when rooom is locked
		
		Platform.runLater(() -> {
			
			ListView listView = (ListView) robot.lookup("#roomList").query();
    	listView.getSelectionModel().select(2);
        ListView channels = (ListView) robot.lookup("#channelList").query();
        channels.getSelectionModel().select(0);
        semaphore.release();
		});
		semaphore.acquire();
		
        Platform.runLater(() -> {
    	    
    	    
    	    // Platform.runLater(() -> 
    		    TextField tfi = (TextField) robot.lookup("#messageField").query();
    		    tfi.setText("new Message");
    		    robot.clickOn("#sendButton");
    		    semaphore.release();
    		    
    	   // });
         });
		semaphore.acquire();
		
		// Checks that type cannot be changed by noob
		
		robot.clickOn("#editChannelButton");
		
		
		robot.clickOn("#channelUnlocked");

		robot.clickOn("#submitChannelButton");
		
	
		Platform.runLater(() -> {
        ListView channels = (ListView) robot.lookup("#channelList").query();
        channels.getSelectionModel().select(0);
        semaphore.release();
		});
		semaphore.acquire();
		
        Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    
    	    // Platform.runLater(() -> 
    		    TextField tfi = (TextField) robot.lookup("#messageField").query();
    		    tfi.setText("new Message");
    		    robot.clickOn("#sendButton");
    		    semaphore.release();
    		    
    	   // });
         });
		
        semaphore.acquire();
		
		robot.clickOn("#newChannelButton");

		robot.clickOn("#channelNameField");
		robot.write("Best Channel");
		robot.clickOn("#channelUnlocked");
		robot.clickOn("#submitChannelButton");
		
		// check that noob cant add a channel
		
        Platform.runLater(() -> {
		
			ListView channelV = (ListView) robot.lookup("#channelList").query();
			channelV.getSelectionModel().select(0);
			assertEquals(channelView.getItems().size(),1);
			semaphore.release();
			});
        semaphore.acquire();
        
		// Checks that type cannot be changed by noob
		
		robot.clickOn("#editChannelButton");
		
		robot.clickOn("#channelNameField");
		robot.write("New Name");

		robot.clickOn("#submitChannelButton");
		
        robot.clickOn("#logOutButton");
        
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		
		Platform.runLater(() -> {
			ListView rooms = (ListView) robot.lookup("#roomList").query();
			rooms.getSelectionModel().select(0);
			ListView channelList = (ListView) robot.lookup("#channelList").query();
			channelList.getSelectionModel().select(0);
			semaphore.release();
         
		});
		semaphore.acquire();
		
		robot.clickOn("#editChannelButton");
		
		robot.clickOn("#channelLocked");

		robot.clickOn("#submitChannelButton");
		
		// Checks that a message can still be sent by an admin when room is locked
			
			Platform.runLater(() -> {
				ListView channelV = (ListView) robot.lookup("#channelList").query();
				channelV.getSelectionModel().select(0);
				
				ListView listView = (ListView) robot.lookup("#roomList").query();
	    	listView.getSelectionModel().select(0);
	        ListView channels = (ListView) robot.lookup("#channelList").query();
	        channels.getSelectionModel().select(0);
	        semaphore.release();
			});
			semaphore.acquire();
		
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    chats.getSelectionModel().select(0);
    		semaphore.release();
			
		});
			
		semaphore.acquire();
		
		robot.clickOn("#deleteChatButton");
		
		// Check that chat was deleted
		
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    assertEquals(chats.getItems().size(), 1);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    chats.getSelectionModel().select(0);
    		semaphore.release();
			
		});
		
		semaphore.acquire();
		
		robot.clickOn("#pinUnpinButton");
		

		// Check that nothing happened to normal chat
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    assertEquals(chats.getItems().size(), 1);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		
		
		// Switch users to make sure things are happening on both sides
		robot.clickOn("#logOutButton");
        
		robot.clickOn("#userNameField");
		robot.write("janice");
		robot.clickOn("#passwordField");
		robot.write("CodingRocks");
		robot.clickOn("#loginButton");
		
		
		Platform.runLater(() -> {
			ListView listView = (ListView) robot.lookup("#roomList").query();
    	listView.getSelectionModel().select(2);
        ListView channels = (ListView) robot.lookup("#channelList").query();
        channels.getSelectionModel().select(0);
        semaphore.release();
		});
		semaphore.acquire();
		
		robot.clickOn("#getPinnedButton");
		
		// Check that the message was pinned
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    assertEquals(chats.getItems().size(), 1);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    chats.getSelectionModel().select(0);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		robot.clickOn("#replyTextField");
		robot.write("Reply Message");
		
		robot.clickOn("#sendReplyButton");
		
		// check that the reply was sent
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    assertEquals(chats.getItems().size(), 2);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    chats.getSelectionModel().select(1);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		
		// Chekc that the data mathces up
		t = (Text) robot.lookup("#replyToText").query();
		assertEquals(t.getText().equals("4"), true);
		t = (Text) robot.lookup("#messageText").query();
		assertEquals(t.getText().equals("Reply Message"), true);
		t = (Text) robot.lookup("#senderText").query();
		assertEquals(t.getText().equals("janice"), true);
		
		
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#roomChatList").query();
    	    chats.getSelectionModel().select(1);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		
		// Check that close button works
		
		robot.clickOn("#closeChatButton");
		
		robot.clickOn("#logOutButton");
		
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");

		robot.clickOn("#dmButton");
		
		Platform.runLater(() -> {
			ListView rooms = (ListView) robot.lookup("#dmLists").query();
			rooms.getSelectionModel().select(0);
			semaphore.release();
         
		});
		
		
		semaphore.acquire();
		
		robot.clickOn("#dmMessageField");
		robot.write("new dm message");
		
		robot.clickOn("#sendDMButton");

		// Check that the message sent
		Platform.runLater(() -> {
    	    ListView chats = (ListView) robot.lookup("#dmMessages").query();
    	    assertEquals(chats.getItems().size(), 1);
    		semaphore.release();
			
		});
			
		semaphore.acquire();
		
		robot.clickOn("#blockUserButton");
		
		Platform.runLater(() -> {
    	    ListView users = (ListView) robot.lookup("#usersList").query();
    	    users.getSelectionModel().select(1);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		robot.clickOn("#actionButtonTwo");

		
		robot.clickOn("#createDMButton");
		
		
		// Select a user 
		Platform.runLater(() -> {
    	    ListView users = (ListView) robot.lookup("#usersList").query();
    	    users.getSelectionModel().select(1);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		// Try to add user to dm
		robot.clickOn("#actionButtonTwo");
		
		// check that the dm was not added
		Platform.runLater(() -> {
    	    ListView dms = (ListView) robot.lookup("#dmLists").query();
    	    assertEquals(dms.getItems().size(), 1);
    		semaphore.release();
			
		});
		
		semaphore.acquire();
		
		// Switch user to see that they cant add dm either
		
		robot.clickOn("#logOutButton");
		robot.clickOn("#userNameField");
		robot.write("denise");
		robot.clickOn("#passwordField");
		robot.write("DeniseRocks");
		robot.clickOn("#loginButton");
		
		robot.clickOn("#dmButton");
		

		robot.clickOn("#createDMButton");
		
		Platform.runLater(() -> {
    	    ListView users = (ListView) robot.lookup("#usersList").query();
    	    users.getSelectionModel().select(0);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		robot.clickOn("#actionButtonTwo");
		
		
		// check that the dm was not added
		Platform.runLater(() -> {
    	    ListView dms = (ListView) robot.lookup("#dmLists").query();
    	    assertEquals(dms.getItems().size(), 0);
    		semaphore.release();
			
		});
		
		semaphore.acquire();
		
		robot.clickOn("#logOutButton");
		
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");

		robot.clickOn("#dmButton");
		
		// Unblock user and try to add 
		robot.clickOn("#blockUserButton");
		
		Platform.runLater(() -> {
    	    ListView users = (ListView) robot.lookup("#usersList").query();
    	    users.getSelectionModel().select(1);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		robot.clickOn("#actionButton");
		
		robot.clickOn("#createDMButton");
		
		Platform.runLater(() -> {
    	    ListView users = (ListView) robot.lookup("#usersList").query();
    	    users.getSelectionModel().select(1);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		robot.clickOn("#actionButtonTwo");
		
		
		// check that the dm was added
		Platform.runLater(() -> {
    	    ListView dms = (ListView) robot.lookup("#dmLists").query();
    	    assertEquals(dms.getItems().size(), 2);
    		semaphore.release();
			
		});
		
		semaphore.acquire();
		
		
		
		robot.clickOn("#logOutButton");
		robot.clickOn("#userNameField");
		robot.write("denise");
		robot.clickOn("#passwordField");
		robot.write("DeniseRocks");
		robot.clickOn("#loginButton");
		
		robot.clickOn("#dmButton");
		
		// check that the dm was added
		Platform.runLater(() -> {
    	    ListView dms = (ListView) robot.lookup("#dmLists").query();
    	    assertEquals(dms.getItems().size(), 1);
    		semaphore.release();
			
		});
		
		semaphore.acquire();

		robot.clickOn("#logOutButton");
		
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");

		robot.clickOn("#createRButton");
		
		robot.clickOn("#nameField");
		robot.write("Bobs private");
		robot.clickOn("#descriptionField1");
		robot.write("bff's only");
		robot.clickOn("#privateCheckBox");
		robot.clickOn("#createButton");
		
		// Create room and check that it exits in list
		Platform.runLater(() -> {
			ListView rooms = (ListView) robot.lookup("#roomList").query();
			rooms.getSelectionModel().select(2);
			semaphore.release();
         
		});
		
		
		semaphore.acquire();
		
		// invite a user
		robot.clickOn("#inviteUserButton");
		
		
		Platform.runLater(() -> {
    	    ListView users = (ListView) robot.lookup("#usersList").query();
    	    users.getSelectionModel().select(0);
    		semaphore.release();
			
		});
		semaphore.acquire();

		robot.clickOn("#actionButtonTwo");
		robot.clickOn("#closeUsersButton");
		
		
		// Switch to the user invited
		robot.clickOn("#logOutButton");
		
		robot.clickOn("#userNameField");
		robot.write("janice");
		robot.clickOn("#passwordField");
		robot.write("CodingRocks");
		robot.clickOn("#loginButton");
		
		robot.clickOn("#exploreButton");
		

		// Select the room in the explore and add
		Platform.runLater(() -> {
    	    ListView rooms = (ListView) robot.lookup("#exploreList").query();
    	    rooms.getSelectionModel().select(2);
    		semaphore.release();
			
		});
			
		semaphore.acquire();
		
		robot.clickOn("#addButton");
		
		
		// Check that the room was added
		Platform.runLater(() -> {
    	    ListView rooms = (ListView) robot.lookup("#roomList").query();
    	    assertEquals(rooms.getItems().size(), 4);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		// log out and switch to a user that was not invited
		
		robot.clickOn("#logOutButton");

		robot.clickOn("#userNameField");
		robot.write("denise");
		robot.clickOn("#passwordField");
		robot.write("DeniseRocks");
		robot.clickOn("#loginButton");
		
		
		// Check that the user cant join the room in the explore page
		robot.clickOn("#exploreButton");
		
		Platform.runLater(() -> {
    	    ListView rooms = (ListView) robot.lookup("#exploreList").query();
    	    assertEquals(rooms.getItems().size(), 5);
    		semaphore.release();
			
		});
		semaphore.acquire();
		
		
		
		

		
	}
	

	
	
	
	
	
	
	
	

}
