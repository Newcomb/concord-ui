package controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Chat;
import model.ChatLog;
import model.JVMClient;
import model.Room;

public class ChatPopup extends BaseController implements Initializable
{
	public Chat c;
	public Room r;
	public ChatLog cl;

	public ChatPopup(JVMClient client, ViewFactory viewFactory, String fxmlName, Chat c, Room r, ChatLog cl)
	{
		super(client, viewFactory, fxmlName);
		// TODO Auto-generated constructor stub
		this.c = c;
	}
	
	  @FXML
	    private Button closeChatButton;

	    @FXML
	    private Text messageText;

	    @FXML
	    private Button pinUnpinButton;

	    @FXML
	    private Text replyToText;

	    @FXML
	    private Text senderText;

	    
	    @FXML
	    private Button deleteChatButton;
	    
	    @FXML
	    private TextField replyTextField;
	    
	    @FXML
	    private Button sendReplyButton;

    @FXML
    void OnCloseButtonClicked(ActionEvent event) {
    	viewFactory.closeStageFromNode(closeChatButton);
    }

    @FXML
    void OnPinUnpinButtonClicked(ActionEvent event) throws RemoteException {
    	client.pinMessage(c.getChatID(), client.selectedRoomObject.getRoomID(), client.selectedChatLogObject.getChatLogID());
    	client.initializeChatData();
    	viewFactory.closeStageFromNode(closeChatButton);
    }
    
    @FXML
    void OnDeleteButtonClicked(ActionEvent event) throws RemoteException {
    	client.deleteChat(client.selectedRoomObject.getRoomID(), client.selectedChatLogObject.getChatLogID(), client.selectedChatObject.getChatID());
    	client.initializeChatData();
    	viewFactory.closeStageFromNode(closeChatButton);


    }
    
    
    @FXML
    void OnSendReplyButtonClicked(ActionEvent event) throws RemoteException {
    	if (!replyTextField.getText().isEmpty()) {
    	client.chatLogReply(replyTextField.getText(),client.selectedRoomObject.getRoomID(), client.selectedChatLogObject.getChatLogID(), client.selectedChatObject.getChatID());
    
    	}
    	viewFactory.closeStageFromNode(closeChatButton);
    }


	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		messageText.setText(c.getMessage());
		if (c.getReplyToID() != -1) {
			replyToText.setText(Integer.toString(c.getReplyToID()));
		} else {
			replyToText.setText("None");
		}
		
		try
		{
			senderText.setText(client.getUserList().getUser(c.getSenderID()).toString());
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
