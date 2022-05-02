package controllers;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.JVMClient;

public class ChannelCreationController extends BaseController
{
	
	String type;
	
    @FXML
    private CheckBox channelLocked;

    @FXML
    private TextField channelNameField;

    @FXML
    private CheckBox channelUnlocked;

    @FXML
    private Text nameWarning;

    @FXML
    private Text statusWarning;
    



    @FXML
    void onCancelButtonClicked(ActionEvent event) {
    	viewFactory.closeStageFromNode(channelNameField); 
    }

    @FXML
    void onCreateButtonClicked(ActionEvent event) throws RemoteException {
    	if (type.equals("Create")) {
	    	if (!channelNameField.getText().trim().isEmpty()) {
		    	if ((channelLocked.isSelected() && channelUnlocked.isSelected()) || (!channelLocked.isSelected() && !channelUnlocked.isSelected())) {
		    		statusWarning.setText("Pick one status!");
		    	}
		    	else {
		    		if(channelLocked.isSelected())
		    		{
		    			client.createChatLog(channelNameField.getText(), client.selectedRoomObject.getRoomID(), true);
		    		} else {
		    			client.createChatLog(channelNameField.getText(), client.selectedRoomObject.getRoomID(), false);
		    		}
		    		statusWarning.setText("");
		    	// client.initializeRoomData();
		    	client.initializeChannelData();
		    	client.initializeUsersData();
		        viewFactory.closeStageFromNode(channelNameField);
		    	}
	    	}
	    	else {
	    		nameWarning.setText("Must have a name!");
	    	}
    	} else {
    		if ((channelLocked.isSelected() && channelUnlocked.isSelected())) {
	    		statusWarning.setText("Pick one status!");
	    	}
    		else if (channelLocked.isSelected()) {
    			client.setChatLogLocked(client.selectedChatLogObject.getChatLogID(), client.selectedRoomObject.getRoomID(), true);
		    	client.initializeChannelData();
		    	client.initializeUsersData();
		        viewFactory.closeStageFromNode(channelNameField);
    		} else if (channelUnlocked.isSelected()) {
    			client.setChatLogLocked(client.selectedChatLogObject.getChatLogID(), client.selectedRoomObject.getRoomID(), false);
		    	client.initializeChannelData();
		    	client.initializeUsersData();
		        viewFactory.closeStageFromNode(channelNameField);
    		}
    		if (!channelNameField.getText().isEmpty()) {
    			client.setChatLogName(client.selectedRoomObject.getRoomID(), client.selectedChatLogObject.getChatLogID(), channelNameField.getText());
		    	client.initializeChannelData();
		    	client.initializeUsersData();
		        viewFactory.closeStageFromNode(channelNameField);
    		}
    		
    	}
    }

	public ChannelCreationController(JVMClient client, ViewFactory viewFactory, String fxmlName, String string)
	{
		super(client, viewFactory, fxmlName);
		// TODO Auto-generated constructor stub
		type = string;

	}

	
	
}
