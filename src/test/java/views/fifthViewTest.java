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
	    	    chats.getSelectionModel().select(0);
	    		semaphore.release();
				
			});
				
			semaphore.acquire();
			
			

			

			

		}

	
	
	
	
	
	
	
	

}
