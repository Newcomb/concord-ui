package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Chat;
import model.ChatLog;
import model.JVMClient;
import model.Room;
import model.User;

import java.io.IOException;

public class ViewFactory {

    JVMClient client;
    BorderPane mainFrame;
    BorderPane roomView;
    BorderPane dmView;

    public ViewFactory(JVMClient client) { this.client = client; }

    public void closeStageFromNode(Node node) { getStageForNode(node).close(); }

    public void showLoginWindow() {
    	// reset mainframe in case the user logged out
    	mainFrame = null;
        BaseController loginController = new LoginController(client, this, "/views/login-view.fxml");
        showStage(loginController);
    }

    public void showRoomView() {
        /* Make sure that the main frame exists */
        showMainFrame();
        BaseController roomViewController = new RoomViewController(client, this, "/views/room-view.fxml");
        Parent roomView = getView(roomViewController);
        mainFrame.setCenter(roomView);
    }

    public void showDmView() { 
        /* Make sure that the main frame exists */
        showMainFrame();
        BaseController dmViewController = new DmViewController(client, this, "/views/dm-view.fxml");
        BorderPane dmView = (BorderPane) getView(dmViewController);
        this.dmView = dmView;
        mainFrame.setCenter(dmView);
    }

    public void showUsersList(String type) {
        BaseController usersListViewController =
                new UsersListViewController(client, this, "/views/users-list-view.fxml", type);
        if (!type.equals("Invite")) {
        Parent usersListView = getView(usersListViewController);
        dmView.setCenter(usersListView);
        } else {
        	showStage(usersListViewController);
        }
    }

    public void showChatListView(String view) {
        BaseController chatListViewController = new ChatListViewController(client, this, "/views/chat-list-view.fxml");
        Parent chatListView = getView(chatListViewController);
        if (view == "room") roomView.setCenter(dmView);
        else dmView.setCenter(chatListView);
    }

    public void showExploreView() {
        BaseController exploreViewController = new ExploreViewController(client, this, "/views/explore-view.fxml");
        showStage(exploreViewController);
    }

    public void showRoomNamePopup() {
        BaseController roomNamePopupController =
                new RoomNamePopupController(client, this, "/views/room-name-popup.fxml");
        showStage(roomNamePopupController);
    }
    
    public void showEditUserPopup() {
        BaseController editUserController =
                new EditUserController(client, this, "/views/edit-user-profile.fxml");
        showStage(editUserController);
    }

    public void showProfilePopup(User newValue) {
        BaseController profilePopupController =
                new ProfilePopupController(client, this, "/views/profile-popup.fxml", newValue);
        showStage(profilePopupController);
    }

    private void showMainFrame() {
        /* If main frame is already shown, do nothing */
        if (mainFrame != null) return;
        BaseController mainFrameController =
                new MainFrameController(client, this, "/views/main-frame.fxml");
        mainFrame = (BorderPane) showStage(mainFrameController);
    }

    private Stage getStageForNode(Node node) { return (Stage) node.getScene().getWindow(); }

    private Parent showStage(BaseController controller) {
        Parent parent = getView(controller);
        /* Initialize scene and stage */
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
        return parent;
    }

    private Parent getView(BaseController controller) {
        /* Initialize loader and set the controller */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.fxmlName));
        fxmlLoader.setController(controller);
        /* Load the fxml file and get the parent node */
        Parent parent;
        try { parent = fxmlLoader.load(); }
        catch (IOException e) { e.printStackTrace(); return null; }
        return parent;
    }

	public void showChannelCreationPopup(String string)
	{
		 BaseController channelCreationController =
	                new ChannelCreationController(client, this, "/views/channel-creation-popup.fxml", string);
	        showStage(channelCreationController);
		
	}

	public void showCreateUserView()
	{
		 BaseController createUserController =
	                new CreateUserController(client, this, "/views/create-user.fxml");
	        showStage(createUserController);
		
	}

	public void showChatPopup(Chat c, Room r, ChatLog cl)
	{

		 BaseController chatPopup =
	     new ChatPopup(client, this, "/views/chat-popup.fxml", c, r, cl);
	     showStage(chatPopup);
			
		
	}

	public void showGamePopup()
	{
		BaseController gameChatPopup = 
				new GamePopupController(client, this, "/views/game-popup.fxml");
		showStage(gameChatPopup);
		
	}

}
