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
class sixthViewTest
{

	@Start
	private void start(Stage stage)
	{
		Stage s = new Stage();
		App a = new App();
		a.start(s);
	}
	
	
		// Test the invite user functionality
		@Test
		public void invitePopupFunctionality(FxRobot robot) throws InterruptedException {
			Platform.setImplicitExit(false);
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
			Semaphore semaphore = new Semaphore(0);
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
