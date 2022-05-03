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
class fifthViewTest
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

			robot.clickOn("#dmButton");
			
			Semaphore semaphore = new Semaphore(0);
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
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			

			

		}

	
	
	
	
	
	
	
	

}
