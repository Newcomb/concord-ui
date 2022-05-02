package views;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import controllers.App;
import javafx.application.Platform;
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
	
	// @Test
	public void testLogin(FxRobot robot) {
		// Check that login works for users
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		Assertions.assertThat(robot.lookup("#roomList") != null);
	}
	
	// @Test
	public void testIncorrectLogIn(FxRobot robot) {
		robot.clickOn("#userNameField");
		robot.write("bo");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		// Check that did not log in so still on login page
		Assertions.assertThat(robot.lookup("#loginButton") != null);
		// Follow with a correct login
		TextField tf = (TextField) robot.lookup("#userNameField").query();
		tf.clear();
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#loginButton");
		Assertions.assertThat(robot.lookup("#roomList") != null);
	}
	
	// @Test
	public void testCreateNewAccount(FxRobot robot) {
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
		// Check that taken back to login page
		Assertions.assertThat(robot.lookup("#loginButton") != null);
		// Test login to account just created
		robot.clickOn("#userNameField");
		robot.write("jen");
		robot.clickOn("#passwordField");
		robot.write("jennyRocks");
		robot.clickOn("#loginButton");
		// Check that succesfully logged in
		Assertions.assertThat(robot.lookup("#roomList") != null);
	}
	
	// Tests that all functionality of the edit profile popup is working
	// @Test
	public void editProfileTest(FxRobot robot) {
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		Assertions.assertThat(robot.lookup("#roomList") != null);
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
		
	}
	
	// @Test
	public void createRoomTest(FxRobot robot) {
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		// robot.clickOn("#editProfileButton");
		robot.clickOn("#createRButton");
		// robot.clickOn("#createRButton");
		robot.clickOn("#nameField");
		robot.write("New Room");
		robot.clickOn("#descriptionField1");
		robot.write("best room");
		robot.clickOn("#publicCheckBox");
		robot.clickOn("#createButton");
		ListView lv = (ListView) robot.lookup("#roomList").query();
		Assertions.assertThat(lv.getItems().get(2).toString().equals("best room"));
	}
	
	// @Test
	public void createBadRoomTest(FxRobot robot) {
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		// robot.clickOn("#editProfileButton");
		robot.clickOn("#createRButton");
		// robot.clickOn("#createRButton");
		robot.clickOn("#descriptionField1");
		robot.write("best room");
		robot.clickOn("#publicCheckBox");
		robot.clickOn("#createButton");
		Text t = (Text) robot.lookup("#logoWarning").query();
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
		
		
	}
	
	
	// Test that room selection works and that chats are loaded in 
	// @Test
	public void roomSelectionTest(FxRobot robot) {
		robot.clickOn("#userNameField");
		robot.write("bob");
		robot.clickOn("#passwordField");
		robot.write("ILoveDogs");
		robot.clickOn("#loginButton");
		Platform.runLater(() -> {ListView listView = (ListView) robot.lookup("#roomList").query();
        	listView.getSelectionModel().select(0);
	        ListView channels = (ListView) robot.lookup("#channelList").query();
	        channels.getSelectionModel().select(0);
        
	        Platform.runLater(() -> {
	    	    ListView chats = (ListView) robot.lookup("#chatList").query();
	    	    Assertions.assertThat(chats.getItems().size() == 1);
	    	    
	    	    Platform.runLater(() -> {
	    		    robot.clickOn("#messageField");
	    		    robot.write("new message");
	    		    robot.clickOn("#sendButton");
	    	    });
	        });
         
	});
	}

		// @Test
		public void userTest(FxRobot robot) {
			Platform.setImplicitExit(false);
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
		        Text t = (Text) robot.lookup("#name").query();
		        Assertions.assertThat(t.getText().equals("janice"));
		        users.getSelectionModel().select(1);
	         
		});

		}
		
		@Test
		public void exploreRoomTest(FxRobot robot) {
			robot.clickOn("#userNameField");
			robot.write("bob");
			robot.clickOn("#passwordField");
			robot.write("ILoveDogs");
			robot.clickOn("#loginButton");
			robot.clickOn("#exploreButton");
			ListView rooms = (ListView) robot.lookup("#roomList");
			rooms.getSelectionModel().select(0);
			robot.clickOn("#addButton");
		}
		

	
	
	
	
	
	
	
	

}
