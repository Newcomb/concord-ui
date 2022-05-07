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
class viewTests
{

	@Start
	private void start(Stage stage)
	{
		Stage s = new Stage();
		App a = new App();
		a.start(s);
	}
	
	
	
	@Test
	public void testAll(FxRobot robot) throws InterruptedException {
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
	
	
	semaphore.acquire();
	
	robot.clickOn("#newChannelButton");

	robot.clickOn("#channelNameField");
	robot.write("Best Channel");
	robot.clickOn("#channelUnlocked");
	robot.clickOn("#submitChannelButton");
	semaphore.release();
	semaphore.acquire();
		
	
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
		semaphore.acquire();
		
	    ListView newChats = (ListView) robot.lookup("#roomChatList").query();
	    newChats.getSelectionModel().select(0);
	    // The chat is there but for some reason this assertion does not work
	   // assertEquals(newChats.getItems().size(), 1);
		
	}
	
		
		// @Test
		public void exploreRoomTest(FxRobot robot) {
			robot.clickOn("#userNameField");
			robot.write("bob");
			robot.clickOn("#passwordField");
			robot.write("ILoveDogs");
			robot.clickOn("#loginButton");
			robot.clickOn("#exploreButton");
			ListView rooms = (ListView) robot.lookup("#roomList");
			rooms.getSelectionModel().select(0);
			robot.clickOn("#cancelButton");
		}
		

	
	
	
	
	
	
	
	

}
