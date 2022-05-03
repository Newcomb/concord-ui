package views;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import controllers.App;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ExtendWith(ApplicationExtension.class)
class fourthViewTest
{

	@Start
	private void start(Stage stage)
	{
		Stage s = new Stage();
		App a = new App();
		a.start(s);
	}
	
	
		// tests being able to switch users between noob, moderator, and admin if user is admin
		@Test
		public void chatPopupFunctionality(FxRobot robot) throws InterruptedException {
			Platform.setImplicitExit(false);
			robot.clickOn("#userNameField");
			robot.write("bob");
			robot.clickOn("#passwordField");
			robot.write("ILoveDogs");
			robot.clickOn("#loginButton");
			
			Semaphore semaphore = new Semaphore(0);
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
					ListView channelView = (ListView) robot.lookup("#channelList").query();
					channelView.getSelectionModel().select(0);
					
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
				ListView channelView = (ListView) robot.lookup("#channelList").query();
				channelView.getSelectionModel().select(0);
				
				ListView listView = (ListView) robot.lookup("#roomList").query();
	    	listView.getSelectionModel().select(0);
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
			Text t = (Text) robot.lookup("#replyToText").query();
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
			

			

		}

	
	
	
	
	
	
	
	

}
