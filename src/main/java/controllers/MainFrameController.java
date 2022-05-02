package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.JVMClient;
import model.Room;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class MainFrameController extends BaseController implements Initializable {


    @FXML
    private Button createRButton;

    @FXML
    private Button dmButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button exploreButton;

    @FXML
    private Button logOutButton;
	
	@FXML
    private ListView<Room> roomList;

    @FXML
    private Label tempRoomName;
    

    

    @FXML
    void OnEditProfileClickedButton(ActionEvent event) {
    	viewFactory.showEditUserPopup();
    }

    @FXML
    void OnCreateRoomButtonClicked(ActionEvent event) { viewFactory.showRoomNamePopup(); }
    
    
    @FXML 
    void OnLogOutButtonClicked(ActionEvent event) throws RemoteException {
    	if (client.logOut()) {
    		viewFactory.showLoginWindow();
    		viewFactory.closeStageFromNode(roomList);

    	}
    }

    @FXML
    void onDMButtonClicked(ActionEvent event) {
    	client.initializeDMData();
        viewFactory.showDmView();
        /*TODO: Clear selection */
    }

    @FXML
    void onExploreButtonClicked(ActionEvent event) {
        viewFactory.showExploreView();
    }

    public MainFrameController(JVMClient client, ViewFactory viewFactory, String fxmlName) {
        super(client, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roomList.setItems(client.roomNames);
        roomList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println("Item selected");
            client.setSelectedRoomObject(newValue);
            viewFactory.showRoomView();
        });
    }
}
