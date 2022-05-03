package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.JVMClient;
import model.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class DmViewController extends BaseController implements Initializable {

    @FXML
    private ListView<Room> dmLists;

    @FXML
    void OnCreateDMButtonClicked(ActionEvent event) {
        viewFactory.showUsersList("DM");
    }
    
    @FXML
    void OnBlockUserButtonClicked(ActionEvent event) {
        viewFactory.showUsersList("Block");
    }

    public DmViewController(JVMClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dmLists.setItems(client.dms);

        dmLists.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
        	if (newValue != null) {
        	client.selectedRoomObject = newValue;
        	
        	if (client.selectedRoomObject != null) {
        		client.selectedChatLogObject = client.selectedRoomObject.getChatLog(0);
        	}
        	}
            viewFactory.showChatListView("dm");
        });
    }
}