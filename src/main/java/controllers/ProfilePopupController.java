package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Admin;
import model.JVMClient;
import model.Moderator;
import model.Noob;
import model.User;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ProfilePopupController extends BaseController implements Initializable {
    @FXML
    private Label name;

    @FXML
    private Label profile;
    
    @FXML
    private Button makeAdminButton;

    @FXML
    private Button makeModeratorButton;
    
    @FXML
    private Button changeGameStatusButton;
    
    @FXML
    private Button makeNoobButton;
    
    @FXML
    private Button kickButton;
    
    @FXML
    private Button cancelButton;


    User userName;

    public ProfilePopupController(JVMClient client, ViewFactory viewFactory, String fxmlName, User newValue) {
        super(client, viewFactory, fxmlName);
        this.userName = newValue;
    }
    
    
    @FXML
    void OnChangeGameStatusButtonClicked(ActionEvent event) throws RemoteException {
    	client.changeGameStatus(client.selectedRoomObject.getRoomID());
    	viewFactory.closeStageFromNode(makeModeratorButton);
    }

    @FXML
    void OnMakeModeratorButtonClicked(ActionEvent event) throws RemoteException {
    	client.giveRole(userName.getUserID(), client.selectedRoomObject.getRoomID(), new Moderator());
    	viewFactory.closeStageFromNode(makeModeratorButton);
    }

    @FXML
    void OnMakeAdminButtonClicked(ActionEvent event) throws RemoteException {
    	client.giveRole(userName.getUserID(), client.selectedRoomObject.getRoomID(), new Admin());
    	viewFactory.closeStageFromNode(makeModeratorButton);
    	
    }
    
    @FXML
    void OnCloseButtonClicked(ActionEvent event) {
    	viewFactory.closeStageFromNode(makeModeratorButton);
    }
    

    @FXML
    void OnKickButtonClicked(ActionEvent event) throws RemoteException {
    	client.removeUserFromRoom(userName.getUserID(), client.selectedRoomObject.getRoomID());
    	if (userName.getUserID() == client.getU().getUserID()){
    		client.initializeRoomData();
    	}
    	client.initializeUsersData();
    	
    	viewFactory.closeStageFromNode(makeModeratorButton);
    }
    

    @FXML
    void OnClickMakeNoobButton(ActionEvent event) throws RemoteException {
    	client.giveRole(userName.getUserID(), client.selectedRoomObject.getRoomID(), new Noob());
    	viewFactory.closeStageFromNode(makeModeratorButton);
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
		{
			name.setText(userName.getUsername() + " " + client.getRole(client.selectedRoomObject.getRoomID(), userName.getUserID()));
		} catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try
		{
        	String status;
        	if (client.getUserList().getUser(userName.getUserID()).getStatus()) {
        		status = "Online";
        	} else {
        		status = "Offline";
        	}
        	String gameStatus;
        	if (client.getUserList().getUser(userName.getUserID()).getGameStatus().get(client.selectedRoomObject.getRoomID())) {
        		gameStatus = "Wants to Game";
        	} else {
        		gameStatus = "Does not want to game";
        	}
			profile.setText("Status: " + status + "\n\n" + client.getUserList().getUser(userName.getUserID()).getProfileData() + "\n\n" + "Game Status: " + gameStatus);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	if (client.getU().getUserID() == userName.getUserID()) {
    		makeAdminButton.setDisable(true);
    		makeModeratorButton.setDisable(true);
    		makeNoobButton.setDisable(true);
   
    	}
    	if (client.getU().getUserID() != userName.getUserID()) {
    		changeGameStatusButton.setDisable(true);
    	}
    }
    
}
