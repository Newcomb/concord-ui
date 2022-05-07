package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.JVMClient;
import model.User;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class UsersListViewController extends BaseController implements Initializable {
	
	public User current;
	
	public String type;
    
    @FXML
    private Button actionButton;
    
    @FXML
    private Button actionButtonTwo;
    
    @FXML
    private Button closeUsersButton;

    @FXML
    private ListView<User> usersList;

    @FXML
    void OnActionButtonClicked(ActionEvent event) throws RemoteException {
    	if (type.equals("Block") && current != null) {
    		client.unblockUser(current.getUserID());
    	}
    
    }
    
    @FXML
    void OnActionButtonTwoClicked(ActionEvent event) throws RemoteException {
    	if (type.equals("Block") && current != null) {
    		client.blockUser(current.getUserID());
    	}
    	if (type.equals("DM") && current != null) {
    		client.createDirectMessage(current.getUserID());
    		client.initializeDMData();
    	}
    	if (type.equals("Invite")) {
    		client.inviteUserToRoom(current.getUserID(), client.selectedRoomObject.getRoomID());
    	}
    }


    @FXML
    void OnCloseButtonClicked(ActionEvent event) throws RemoteException{
    	if (type.equals("Invite") && current != null) {
    		client.initializeUsersData();
    	}
    	viewFactory.closeStageFromNode(actionButton);
    }

    public UsersListViewController(JVMClient client, ViewFactory viewFactory, String fxmlName, String type) {
        super(client, viewFactory, fxmlName);
        this.type = type;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	try
		{
    		if(type.equals("Invite")) {
    			client.initialInviteUsersData();
    		} else {
    			client.initialAllUsersData();
    		}
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        usersList.setItems(client.userNames);
        closeUsersButton.setOpacity(0);
        closeUsersButton.setDisable(true);
        
        if (type.equals("DM")) {
            actionButton.setOpacity(0);
            actionButton.setDisable(true);
        	actionButtonTwo.setText("createDM");
        }
        
        if (type.equals("Block")) {
        	actionButton.setText("Unblock");
        	actionButtonTwo.setText("Block");
        }
        
        if (type.equals("Invite")) {
            actionButton.setOpacity(0);
            actionButton.setDisable(true);
        	actionButtonTwo.setText("Invite");
            closeUsersButton.setOpacity(100);
            closeUsersButton.setDisable(false);
        }
        
        usersList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
        	current = newValue;
        });
        
    }
}
