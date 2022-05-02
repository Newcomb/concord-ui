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
    }


    @FXML
    void OnCloseButtonClicked(ActionEvent event) {
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
			client.initialAllUsersData();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        usersList.setItems(client.userNames);
        
        if (type.equals("DM")) {
        	actionButtonTwo.setText("createDM");
        }
        
        if (type.equals("Block")) {
        	actionButton.setText("Unblock");
        	actionButtonTwo.setText("Block");
        }
        
        usersList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
        	current = newValue;
        });
        
    }
}
