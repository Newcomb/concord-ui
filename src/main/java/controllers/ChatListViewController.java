package controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Chat;
import model.JVMClient;

public class ChatListViewController extends BaseController implements Initializable{
	

    @FXML
    private TextField dmMessageField;
    
    @FXML
    private Button sendDMButton;

    @FXML
    private ListView<Chat> dmMessages;

    @FXML
    void OnClickSendMessage(ActionEvent event) throws RemoteException {
    	if (dmMessageField.getText().isEmpty()) {
    		// textWarning.setText("Must enter a message");
    	} else {
    		client.addChat(client.selectedChatLogObject.getChatLogID(), client.selectedRoomObject.getRoomID(), dmMessageField.getText());
    		dmMessageField.clear();
    		client.initializeChatData();
    		dmMessages.setItems(client.chats);
    	}

    	

    }

    public ChatListViewController(JVMClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	if (client.selectedRoomObject != null) {
    		if(client.selectedChatLogObject != null) {
    	client.initializeChatData();
    	dmMessages.setItems(client.chats);
    		}
    	}
    }
}
