package controllers;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.JVMClient;

public class RoomNamePopupController extends BaseController {
	

    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField descriptionField1;
    
    @FXML
    private CheckBox privateCheckBox;

    @FXML
    private CheckBox publicCheckBox;
    
    @FXML
    private Text warningStatus;

    @FXML
    private Text logoWarning;
    
    @FXML
    private Text descriptWarning;
    
    @FXML
    void onCancelButtonClicked(ActionEvent event) { viewFactory.closeStageFromNode(nameField); }

    @FXML
    void onCreateButtonClicked(ActionEvent event) throws RemoteException {
    	if (!nameField.getText().trim().isEmpty()) {
	    	if (!descriptionField1.getText().trim().isEmpty()) {
		    	if ((privateCheckBox.isSelected() && publicCheckBox.isSelected()) || (!privateCheckBox.isSelected() && !publicCheckBox.isSelected())) {
		    		warningStatus.setText("Pick only one status!");
		    	}
		    	else {
		    		if(privateCheckBox.isSelected())
		    		{
		    			client.createRoom(descriptionField1.getText(),nameField.getText(), false);
		    		} else {
		    			client.createRoom(descriptionField1.getText(), nameField.getText(),true);
		    		}
		    		warningStatus.setText("");
		    	client.initializeRoomData();
		        viewFactory.closeStageFromNode(nameField);
		    	}
	    	}
	    	else {
	    		descriptWarning.setText("Must have a description!");
	    	}
    	} else {
    		logoWarning.setText("Must have a logo!");
    	}
    }

    public RoomNamePopupController(JVMClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }
}
 