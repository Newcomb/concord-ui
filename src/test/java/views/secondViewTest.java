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
class secondViewTest
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
		public void profilePopupTest(FxRobot robot) throws InterruptedException {
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
		        ListView users = (ListView) robot.lookup("#userList").query();
		        users.getSelectionModel().select(1);
		        semaphore.release();
	         
			});
			semaphore.acquire();
			
	        Label t = (Label) robot.lookup("#name").query();
	        assertEquals(t.getText().equals("janice Admin"), true);
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
	        t = (Label) robot.lookup("#name").query();
	        assertEquals(fifthT.getText().equals("janice Noob"), true);
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
			
			
			// CHekc when added to room you are noob
			t = (Label) robot.lookup("#name").query();
			System.out.println(fifthT.getText());
	        assertEquals(fifthT.getText().equals("janice Noob"), true);
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
	        

		}

	
	
	
	
	
	
	
	

}
