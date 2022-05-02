package controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.JVMClient;

public class EditUserController extends BaseController implements Initializable
{

	public EditUserController(JVMClient client, ViewFactory viewFactory, String fxmlName)
	{
		super(client, viewFactory, fxmlName);
		// TODO Auto-generated constructor stub
	}
	

    @FXML
    private CheckBox offlineStatus;

    @FXML
    private CheckBox onlineStatus;

    @FXML
    private Text passwordText;

    @FXML
    private TextField profileDataEditField;

    @FXML
    private Text profileDataText;

    @FXML
    private Text realNameText;

    @FXML
    private Text statusText;

    @FXML
    private Text usernameText;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button backButton;
    

    @FXML
    void OnBackButtonClicked(ActionEvent event) {
    	viewFactory.closeStageFromNode(offlineStatus);
    }

    @FXML
    void OnSubmitButtonClicked(ActionEvent event) throws RemoteException {
    	if(!profileDataEditField.getText().isEmpty()) {
    		client.setProfileData(profileDataEditField.getText());
    	}
    	if ((!onlineStatus.isSelected() && !offlineStatus.isSelected()) && (onlineStatus.isSelected() || offlineStatus.isSelected())) {
    		if (onlineStatus.isSelected() && (client.getU().getStatus() == false)) {
    			client.setStatus(true);
    		} 
    		if (offlineStatus.isSelected() && (client.getU().getStatus() == true)){
    			client.setStatus(false);
    		}
    	}
    	viewFactory.closeStageFromNode(offlineStatus);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		usernameText.setText(client.getU().getUsername());
		realNameText.setText(client.getU().getRealName());
		passwordText.setText(client.getU().getPassword());
		profileDataText.setText(client.getU().getProfileData());
		if (client.getU().getStatus()) {
			statusText.setText("Online");
		} else {
			statusText.setText("Offline");
		}

	}

}
