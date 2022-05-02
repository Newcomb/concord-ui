package controllers;

import model.JVMClient;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CreateUserController extends BaseController
{

	public CreateUserController(JVMClient client, ViewFactory viewFactory, String fxmlName)
	{
		super(client, viewFactory, fxmlName);
		// TODO Auto-generated constructor stub
	}
	

    @FXML
    private Text dataWarning;

    @FXML
    private CheckBox offlineCheckBox;

    @FXML
    private CheckBox onlineCheckBox;

    @FXML
    private TextField password;

    @FXML
    private Text passwordWarning;

    @FXML
    private TextField photoLink;

    @FXML
    private Text photoWarning;

    @FXML
    private TextField profileData;

    @FXML
    private TextField realName;

    @FXML
    private Text realNameWarning;

    @FXML
    private Text statusWarning;

    @FXML
    private TextField username;
    
    @FXML
    private Text usernameWarning;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button cancelButton;

    @FXML
    void OnSubmitButtonClicked(ActionEvent event) throws RemoteException {
    	if (!realName.getText().isEmpty()) {
    		if (!username.getText().isEmpty()) {
    			if(!password.getText().isEmpty()) {
    				if ((offlineCheckBox.isSelected() && onlineCheckBox.isSelected()) || (!offlineCheckBox.isSelected() && !onlineCheckBox.isSelected())) {
    					statusWarning.setText("Pick one status!");
    				} else {
    					if (offlineCheckBox.isSelected()) {
    						client.createUser(realName.getText(), username.getText(), password.getText(), profileData.getText(), photoLink.getText(), false);
    					} else {
    						client.createUser(realName.getText(), username.getText(), password.getText(), profileData.getText(), photoLink.getText(), true);
    					}
    					viewFactory.showLoginWindow();
    			    	viewFactory.closeStageFromNode(dataWarning);			
    				}
    			} else {
    			passwordWarning.setText("You must have a password!");
    			}
    		} else {
    		usernameWarning.setText("You must have a username!");
    		}
    	} else {
    	realNameWarning.setText("You must have a real name!");
    	}
    }
    
    @FXML
    void OnCancelButtonClicked(ActionEvent event) {
    	viewFactory.showLoginWindow();
    	viewFactory.closeStageFromNode(dataWarning);
    }

}
