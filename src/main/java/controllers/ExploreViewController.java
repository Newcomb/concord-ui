package controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.JVMClient;
import model.Room;

public class ExploreViewController extends BaseController implements Initializable{

	Room r;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button cancelButton;
	
    @FXML
    private ListView<Room> roomList;

    public ExploreViewController(JVMClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	try
		{
			client.initializeExploreRooms();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	roomList.setItems(client.exploreRooms);
    	
        roomList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println("Item selected");
            r = newValue;
        });
    
    }

    @FXML
    void onAddButtonClicked(ActionEvent event) throws RemoteException {
    	client.addRoom(r.getRoomID());
    	client.initializeRoomData();
        viewFactory.closeStageFromNode(roomList);
    }

    @FXML
    void onCancelButtonClicked(ActionEvent event) {
        viewFactory.closeStageFromNode(roomList);
    }
}
