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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ExtendWith(ApplicationExtension.class)
class thirdViewTest
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
		public void editChannelTest(FxRobot robot) throws InterruptedException {
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
				ListView channelView = (ListView) robot.lookup("#channelList").query();
				channelView.getSelectionModel().select(0);
				
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
			
			// Checks that type cannot be changed by noob
			
			robot.clickOn("#editChannelButton");
			
			robot.clickOn("#channelUnlocked");

			robot.clickOn("#submitChannelButton");
			
		
			Platform.runLater(() -> {
				ListView channelView = (ListView) robot.lookup("#channelList").query();
				channelView.getSelectionModel().select(0);
				
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
			
			
			robot.clickOn("#newChannelButton");

			robot.clickOn("#channelNameField");
			robot.write("Best Channel");
			robot.clickOn("#channelUnlocked");
			robot.clickOn("#submitChannelButton");
			
			// check that noob cant add a channel
			
	        Platform.runLater(() -> {
			
				ListView channelView = (ListView) robot.lookup("#channelList").query();
				channelView.getSelectionModel().select(0);
				assertEquals(channelView.getItems().size(),1);
				semaphore.release();
				});
	        semaphore.acquire();
	        
			// Checks that type cannot be changed by noob
			
			robot.clickOn("#editChannelButton");
			
			robot.clickOn("#channelNameField");
			robot.write("New Name");

			robot.clickOn("#submitChannelButton");
			

		}

	
	
	
	
	
	
	
	

}
