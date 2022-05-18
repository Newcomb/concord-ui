package controllers;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Chat;
import model.ChatLog;
import model.JVMClient;
import model.User;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class RoomViewController extends BaseController implements Initializable {
	

    @FXML
    private ListView<ChatLog> channelList;

    @FXML
    private ListView<Chat> roomChatList;

    @FXML
    private ListView<User> userList;

    @FXML
    private Text roomName;
    
    @FXML
    private Text textWarning;
    
    @FXML
    private TextField messageField;
    
    @FXML
    private Button editChannelButton;
    
    @FXML
    private Button newGameButton;

    @FXML
    private Button inviteUserButton;
    
    @FXML
    private Button sendButton;

    @FXML
    private Button newChannelButton;
    
    @FXML
    private Button getPinnedButton;
    

    public RoomViewController(JVMClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    } 
    
    @FXML
    void OnCreateChannelButtonClicked(ActionEvent event) {
    	if (client.selectedRoomObject != null) {
    	viewFactory.showChannelCreationPopup("Create");
    	} 
    }
    
    @FXML
    void OnNewGameButtonClicked(ActionEvent event) {
    	if (client.selectedRoomObject != null) {
    		viewFactory.showGamePopup();
    	}
    }
    

    @FXML
    void OnInviteUserButtonClicked(ActionEvent event) {
    	if (client.selectedRoomObject != null ) {
    		viewFactory.showUsersList("Invite");
    	}
    }
    
    @FXML
    void OnClickGetPinnedButton(ActionEvent event) throws RemoteException {
    	if (client.selectedChatLogObject != null) {
    		client.initializePinnedChatData();
    	}
    }

    @FXML
    void OnEditChannelButtonClicked(ActionEvent event) {
    	if (client.selectedChatLogObject != null) {
    	viewFactory.showChannelCreationPopup("Edit");
    	}
    }
    
    @FXML
    void OnSendButtonClicked(ActionEvent event) throws RemoteException {
    	if (client.selectedRoomObject == null) {
    		textWarning.setText("Select a room first!");
    	}
    	else if (client.selectedChatLogObject == null) {
    		textWarning.setText("Select a channel first!");
    	}
    	else if (messageField.getText().isEmpty()) {
    		textWarning.setText("Must enter a message");
    	} else {
    		client.addChat(client.selectedChatLogObject.getChatLogID(), client.selectedRoomObject.getRoomID(), messageField.getText());
    		messageField.clear();
    		client.initializeChatData();
    		// chatList.setItems(client.chats);
    	}

    	
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* After the controller is bound to the view, set the list values */
    	try
		{
			client.initializeChannelData();
		} catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try
		{
			client.initializeUsersData();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        channelList.setItems(client.channelNames);

        //TODO: Figure out how to render a view inside each cell
        userList.setItems(client.userNames);

        userList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
        	if (newValue != null) {
            viewFactory.showProfilePopup(newValue);
        	}
        });
        
        channelList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
        	if (newValue != null) {
        	client.selectedChatLogObject = newValue;
        	client.initializeChatData();
        	roomChatList.setItems(client.chats);
        	}
        });
        
        
        roomChatList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
        	if (newValue != null) {
        		client.selectedChatObject = newValue;
            // not working at the moment
        		viewFactory.showChatPopup(newValue, client.selectedRoomObject, client.selectedChatLogObject);
        	}
        });
        
        if (client.selectedRoomObject == null) {
        roomName.setText("Choose Room");
        }
        else {
        	roomName.setText(client.selectedRoomObject.getLogo());
        	client.selectedChatLogObject = null;
        }
        // Bindings.bindBidirectional(client.selectedRoom, roomName.textProperty());
    }
}