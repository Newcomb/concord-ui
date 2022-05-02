package controllers;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.JVMClient;
import model.User;

public class LoginController extends BaseController {

    @FXML
    private Label errorMsg;
    
    @FXML
    private Button loginButton;

    @FXML
    private Button newAccountButton;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    void onLoginButtonClicked(ActionEvent event) {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        Boolean canLogin;
		try
		{
			canLogin = this.client.authenticate(userName, password);
			if (canLogin) {
				System.out.println("Here");
		        viewFactory.showRoomView();
		        viewFactory.closeStageFromNode(passwordField);
				} 
		} catch (RemoteException e)
		{
			errorMsg.setText("Login failed. Attempt again!");
		}
        // if (!canLogin) {
            // errorMsg.setText("Login failed. Attempt again!");
            // return;
        // }
    }
    
    
    
    @FXML
    void onCreateNewClicked(ActionEvent event) {
    	viewFactory.showCreateUserView();
    	viewFactory.closeStageFromNode(errorMsg);
    }

    public LoginController(JVMClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }
}
