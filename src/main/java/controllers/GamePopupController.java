package controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.JVMClient;

public class GamePopupController extends BaseController implements Initializable
{

    @FXML
    private Button closeGameButton;

    @FXML
    private Button createGameButton;

    @FXML
    private ListView<String> gameListView;
    
    private String chosen = null;
	
	public GamePopupController(JVMClient client, ViewFactory viewFactory, String fxmlName)
	{
		super(client, viewFactory, fxmlName);
		// TODO Auto-generated constructor stub
	}
	


	    @FXML
	    void OnCloseGameButton(ActionEvent event) {
	    	viewFactory.closeStageFromNode(closeGameButton);
	    }

	    @FXML
	    void OnCreateGameButton(ActionEvent event) throws RemoteException{
	    	if (chosen != null && client.selectedRoomObject != null) {
	    		client.createGameChatLog(chosen, client.selectedRoomObject.getRoomID());
	    		client.initializeChannelData();
	    		viewFactory.closeStageFromNode(closeGameButton);
	    	}
	    }



		@Override
		public void initialize(URL location, ResourceBundle resources)
		{
			client.initializeGameData();
			gameListView.setItems(client.gameList);
			gameListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
	            chosen = newValue;
	        });
			
		}

	


}
