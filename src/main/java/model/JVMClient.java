package model;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.server.UnicastRemoteObject;

public class JVMClient extends UnicastRemoteObject implements Serializable, RMIObserver
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6991295632242363159L;
	/**
	 * 
	 */
	User u;
	public RoomList rl;
	String look = "Concord";
	JVMInterface serv;
	public Room selectedRoomObject = null;
	public ChatLog selectedChatLogObject = null;
	public Chat selectedChatObject = null;
	public ObservableList<Room> roomNames = FXCollections.observableArrayList();
    public ObservableList<ChatLog> channelNames = FXCollections.observableArrayList();
    public ObservableList<User> userNames = FXCollections.observableArrayList();
    public ObservableList<Chat> chats = FXCollections.observableArrayList();
    public ObservableList<Room> dms = FXCollections.observableArrayList();
    public ObservableList<String> users = FXCollections.observableArrayList();
	public ObservableList<Room> exploreRooms = FXCollections.observableArrayList();
	public JVMClient() throws RemoteException{
		try
		{	
			Registry registry = LocateRegistry.getRegistry("localhost");
			serv = (JVMInterface) registry.lookup("Concord");
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setSelectedRoomObject(Room r) { this.selectedRoomObject = r; }
	
    public void addRoom(Room r) { this.roomNames.add(r); }
    
	 
	// Authenticates the user and sends back necessary information
	public Boolean authenticate(String username, String password) throws RemoteException
	{
		u = serv.authenticate(username, password, this);
		if (u != null) {
			rl = serv.getRl();
			initializeRoomData();
			return true;
		}
		return false;
	}
	
	public void initializeChannelData() throws RemoteException {
        List<ChatLog> channelNames = new ArrayList<>() {{
        	if (selectedRoomObject != null) {

			for (Integer channelID : rl.getRoom(selectedRoomObject.getRoomID()).getChatLogs().keySet()) {
				add(rl.getRoom(selectedRoomObject.getRoomID()).getChatLog(channelID));
			}
        	}
        }};
        
        this.channelNames.clear();
        this.channelNames.addAll(channelNames); 
		
		
	}
	
	public void initializeExploreRooms() throws RemoteException {
		List<Room> rooms = new ArrayList<>() {{
		Room r;
		for (Integer roomID : serv.getRl().getRooms().keySet()) {
			r = serv.getRl().getRoom(roomID);
			if (!r.getUserTable().containsKey(u.getUserID()) && (r.getInviteStatus(u.getUserID()) || r.getType())) {
				add(r);
			}
		}
		}};
		this.exploreRooms.clear();
		this.exploreRooms.addAll(rooms);
	}
	
	public void initializeUsersData() throws RemoteException {
		
        List<User> userNames = new ArrayList<>() {{
        	if (selectedRoomObject != null) {
        		System.out.println(selectedRoomObject.toString());
        		selectedRoomObject = serv.getRoom(selectedRoomObject.getRoomID());
			for (Integer userID : selectedRoomObject.getUserTable().keySet()) {
				add(serv.getUl().getUser(userID));
			}
        	
        	}
        }};
        this.userNames.clear();
        this.userNames.addAll(userNames);
	}
	
	public void initialAllUsersData() throws RemoteException
	{
        List<User> userNames = new ArrayList<>() {{

			for (Integer userID : serv.getUl().getUsers().keySet()) {
				if (userID != u.getUserID()) {
					add(serv.getUl().getUser(userID));
				}
			}
        	
        	
        }};
        this.userNames.clear();
        this.userNames.addAll(userNames);
		
	}
	
	public void initializeChatData() {
		List<Chat> chatData = new ArrayList<>() {{
		if (selectedRoomObject != null) {

			    	Set<Integer> chatSet = rl.getRoom(selectedRoomObject.getRoomID()).getChatLog(selectedChatLogObject.getChatLogID()).getChat().keySet();
			    	List<Integer> arr = new ArrayList<>(chatSet);
			    	Collections.sort(arr);
					for (Integer chatID : arr) {
						if (!rl.getRoom(selectedRoomObject.getRoomID()).getChatLog(selectedChatLogObject.getChatLogID()).getChat(chatID).deleted) {
							add(rl.getRoom(selectedRoomObject.getRoomID()).getChatLog(selectedChatLogObject.getChatLogID()).getChat(chatID));
						}
					
				
			}
		}
    	// }
    	
		}};
		this.chats.clear();
		this.chats.setAll(chatData);
	}
	
	public void initializePinnedChatData() throws RemoteException
	{
		List<Chat> chatData = new ArrayList<>() {{
		if (selectedRoomObject != null) {

			    	Set<Integer> chatSet = serv.getRl().getRoom(selectedRoomObject.getRoomID()).getChatLog(selectedChatLogObject.getChatLogID()).getChat().keySet();
			    	List<Integer> arr = new ArrayList<>(chatSet);
			    	Collections.sort(arr);
					for (Integer chatID : arr) {
						if (!serv.getRl().getRoom(selectedRoomObject.getRoomID()).getChatLog(selectedChatLogObject.getChatLogID()).getChat(chatID).deleted && serv.getRl().getRoom(selectedRoomObject.getRoomID()).getChatLog(selectedChatLogObject.getChatLogID()).getChat(chatID).getPinned()) {
							add(serv.getRl().getRoom(selectedRoomObject.getRoomID()).getChatLog(selectedChatLogObject.getChatLogID()).getChat(chatID));
						}
					
				
			}
		}
    	// }
    	
		}};
		this.chats.clear();
		this.chats.setAll(chatData);
		
	}
	
    public void initializeRoomData() throws RemoteException {
    	System.out.println(u.getRooms());
        List<Room> roomNames = new ArrayList<>() {{
        	for (Integer roomID : u.getRooms())
        	{
        		// System.out.println(roomID);
        		add(rl.getRoom(roomID));
        	}
        }};
        this.roomNames.clear();
        this.roomNames.addAll(roomNames);

    }
    
    public void initializeDMData() throws RemoteException {

        List<Room> dms = new ArrayList<>() {{
        	for (Integer roomID : u.getDirectMessages())
        	{
        		
        			if (serv.getRl().getRoom(roomID) != null) {
        				add(serv.getRl().getRoom(roomID));
        			}
        		
        	}
        }};
        this.dms.clear();
        this.dms.addAll(dms);
    }
	
	// Logs user out and set u to null
	public Boolean logOut() throws RemoteException
	{
		if (u != null) {
			if (serv.logOut(u.getUserID()))
			{
				u = null;
	    		selectedChatLogObject = null;
	    		selectedChatObject = null;
	    		selectedRoomObject = null;
				return true;
			}
		}
		return false;
	}
	
	
	public Boolean createUser(String rn, String un, String password, String data, String link, Boolean status) throws RemoteException
	{
		return serv.createUser(rn, un, password, data, link, status);
	}
	
	
	public Boolean setChatLogName(int chatLogID, int roomID, String text) throws RemoteException
	{
		// TODO Auto-generated method stub
		if (u != null) {
			serv.setChatLogName(u, chatLogID, roomID, text);
			return true;
		}
		return false;
		
	}
	
	// Notifies the server that the user needs to be updated server side
	public Boolean notifyServer() throws RemoteException
	{
		if (u != null) {
			serv.updateUser(u);
			return true;
		}
		return false;
	}
	
	// Checks if the user is succesfully logged in
	public Boolean checkLogInStatus() throws RemoteException
	{
		if (u != null) {
			return true;
		}
		return false;
	}
	
	// Returns all rooms
	public RoomList getAllRooms() throws RemoteException
	{
		if (u != null)
		{
			return serv.getRl();
		}
		return null;
	}
	
	// Creates a direct message between two users
	public Boolean createDirectMessage(int otherID) throws RemoteException
	{
		if (u != null) {
			if (serv.createDirectMessage(otherID, u.getUserID()))
			{
				// u = serv.getUser(u.getUsername());
				u = serv.getUl().getUser(u.getUserID());
				rl = serv.getRl();
				return true;
			}
		}
		return false;
	}
	
	// Creates a new room 
	public Boolean createRoom(String des, String logo, Boolean type) throws RemoteException
	{
		if (u != null) {
			if (serv.createRoom(des, logo, type, u)) {
				u = serv.getUl().getUser(u.getUserID());
				rl = serv.getRl();
				return true;
			}
		}
		return false;
	}
	
	// Will need to notify the server that the room was added to the user
	public Boolean addRoom(int roomID) throws RemoteException
	{
		if (u != null)
		{
			System.out.println("one");
			if (!u.getRooms().contains(roomID)) {
				System.out.println("one");
			u.addRoom(roomID, serv.getRl());
			notifyServer();
			serv.addUserToRoom(u.getUserID(), roomID);
			}
			return true;
		}
		return false;
		// notify server now to pull the user and update
	}
	
	// Removes user from room
	public void removeUserRoom(int roomID)
	{
		u.removeRoom(roomID);
	}

	// Creates a new chat log in a specific room with a name
	public Boolean createChatLog(String name, int roomID, Boolean locked) throws RemoteException
	{
		if (u != null)
		{
			return serv.addChatLog(name, roomID, u.getUserID(), locked);
		}
		return false;
	}
	
	// Removes a chat log that is no longer needed
	public Boolean removeChatLog(int roomID, int chatLogID) throws RemoteException
	{
		if (u != null)
		{
			return serv.removeChatLog(roomID, chatLogID, u.getUserID());
		}
		return false;
	}
	
	// returns a chatlog
	public ChatLog getChatLog(int chatLogID, int roomID) throws RemoteException
	{
		if (u != null) {
			return serv.getChatLog(chatLogID, roomID);
		}
		return null;
	}
	
	
	// deletes a chat within a chatlog within a room
	public Boolean deleteChat(int roomID, int chatLogID, int chatID) throws RemoteException
	{
		if (u != null) {
			return serv.deleteChat(roomID, chatLogID, chatID, u.getUserID());
		}
		return false;
	}
	
	// Adds a user to room
	public Boolean addUserToRoom(int newUserID, int roomID) throws RemoteException
	{
		if (u != null) {
			if(serv.addUserToRoom(newUserID, roomID)) {
				// addRoom(roomID);
				return true;
			}
		}
		return false;
	}
	
	// removes user from room
	public Boolean removeUserFromRoom(int removeID, int roomID) throws RemoteException
	{
		if (u != null)
		{
			if (serv.removeUserFromRoom(removeID, u.getUserID(), roomID)){
				u.removeRoom(roomID);
				return true;
			}
		}
		return false;
	}
	
	
	// Sets specific rooms description
	public Boolean setRoomDescription(int roomID, String desc) throws RemoteException
	{
		if (u != null) {
			return serv.setRoomDescription(u.getUserID(), roomID, desc);
		}
		System.out.println("here");
		return false;
	}
	
	
	// Sets specific rooms logo
	public Boolean setRoomLogo(int roomID, String logo) throws RemoteException
	{
		if (u != null) {
			return serv.setRoomLogo(u.getUserID(), roomID, logo);
		}
		return false;
	}
	
	// Set specific rooms type
	public Boolean setRoomType(Boolean type, int roomID) throws RemoteException
	{
		if (u != null) {
			return serv.setRoomType(type, u.getUserID(), roomID);
		}
		return false;
	}
	
	// Sets specific rooms role
	public Boolean giveRole(int giveID, int roomID, Role r) throws RemoteException
	{
		if (u != null) {
			return serv.giveRole(u.getUserID(), giveID, roomID, r);
		}
		return false;
	}
	
	
	// adds a user to invite list for a spcific room
	public Boolean inviteUserToRoom(int inviteID, int roomID) throws RemoteException
	{
		if (u != null) {
			return serv.inviteUserToRoom(u.getUserID(), inviteID, roomID);
		}
		return false;
	}
	
	public Boolean setChatLogLocked(int chatLogID, int roomID, Boolean locked) throws RemoteException
	{
		if (u != null) {
			return serv.setChatLogLocked(u.getUserID(), chatLogID, roomID, locked);
		}
		return false;
	}
	
	public Boolean addChat(int chatLogID, int roomID, String message) throws RemoteException
	{
		if (u != null) {
			return serv.addChat(u.getUserID(), chatLogID, roomID, message);
		}
		return false;
	}
	
	public Boolean chatLogReply(String message, int roomID, int chatLogID, int replyID) throws RemoteException
	{
		if (u != null) {
			return serv.chatLogReply(message, u.getUserID(), roomID, chatLogID, replyID);
		}
		return false;
	}
	
	public Boolean pinMessage(int chatID, int roomID, int chatlogID) throws RemoteException
	{
		if (u != null) {
			return serv.pinMessage(chatID, roomID, chatlogID, u.getUserID());
		}
		return false;
	}
	
	public List<Chat> getPinnedMessages(int roomID, int chatLogID) throws RemoteException
	{
		if (u != null) {
			return serv.getPinnedMessages(roomID, chatLogID);
		}
		return null;
	}
	
	public Room getRoom(int roomID) throws RemoteException
	{
		if (u != null) {
			return serv.getRoom(roomID);
		}
		return null;
	}
	
	public String getRole(int roomID, int userID) throws RemoteException
	{
		if (u != null) {
			return serv.getRole(roomID, userID);
		}
		return null;
	}
	
	public ArrayList<String> getOnline(int roomID) throws RemoteException
	{
		if (u != null) {
			return serv.getOnline(roomID);
		}
		return null;
	}
	
	public ArrayList<String> getOffline(int roomID) throws RemoteException
	{
		if (u != null) {
			return serv.getOffline(roomID);
		}
		return null;
	}
	
	public String getRoomLogo(int roomID) throws RemoteException
	{
		if (u != null) {
			return serv.getRoom(roomID).getLogo();
		}
		return null;
	}
	
	public String getRoomDescription(int roomID) throws RemoteException
	{
		if (u != null) {
			return serv.getRoom(roomID).getDescription();
		}
		return null;
	}
	
	
	
	// Change profile pic link and tell server to get updated version
	public Boolean setProfilePic(String path) throws RemoteException
	{
		if (u != null) {
			u.setProfilePic(path);
			notifyServer();
			return true;
		}
		return false;
	}
	
	
	// Change profile data and tell server to get updated version
	public Boolean setProfileData(String data) throws RemoteException
	{
		if (u != null)
		{
			u.setProfileData(data);
			notifyServer();
			return true;
		}
		return false;
	}
	
	
	// Change status of user and tell server to get updated version
	public Boolean setStatus(Boolean stat) throws RemoteException
	{
		if (u != null)
		{
			u.setStatus(stat);
			notifyServer();
			return true;
		}
		return false;
	}
	
	// Blocks user and notifies server to get updated version of user
	public Boolean blockUser(int blockID) throws RemoteException
	{
		if(u != null)
		{
			u.addBlocked(blockID);
			notifyServer();
			return true;
		}
		return false;
	}
	
	// Unblocks user and notifies server to get updated version of user
	public Boolean unblockUser(int blockID) throws RemoteException
	{
		if (u != null)
		{
			if (u.removeBlocked(blockID)) {
			notifyServer();
			return true;
			}
			return false;
		}
		return true;
	}
	
	
	
	// Get user rooms
	public List<Integer> getUserRooms() {
		return u.getRooms();
	}
	
	
	// Still havent done the direct messages yet
	
	
	public UserList getUserList() throws RemoteException
	{
		if (u != null) {
			return serv.getUl();
			
		}
		return null;
	}
	
	public Boolean updateRl() throws RemoteException
	{
		if (u != null) {
			rl = serv.getRl();
			if (selectedRoomObject != null) {
				selectedRoomObject = rl.getRoom(selectedRoomObject.getRoomID());
				if (selectedChatLogObject != null) {
					selectedChatLogObject = selectedRoomObject.getChatLog(selectedChatLogObject.getChatLogID());
					Platform.runLater(() -> initializeChatData());
				}
			}
			return true;
		}
		return false;
	}
	
	// Uses auth to get new versin of user
	public Boolean updateU() throws RemoteException
	{
		if (u != null) {
			u = serv.authenticate(u.getUsername(), u.getPassword(), this);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the u
	 */
	public User getU()
	{
		return u;
	}

	/**
	 * @param u the u to set
	 */
	public void setU(User u)
	{
		this.u = u;
	}

	/**
	 * @return the lookup
	 */
	public String getLookup()
	{
		return look;
	}

	/**
	 * @param lookup the lookup to set
	 */
	public void setLookup(String lookup)
	{
		this.look = lookup;
	}

	/**
	 * @return the conn
	 */
	public JVMInterface getConn()
	{
		return serv;
	}

	/**
	 * @param conn the conn to set
	 */
	public void setConn(JVMInterface conn)
	{
		this.serv = conn;
	}

	/**
	 * @return the rl
	 */
	public RoomList getRl()
	{
		return rl;
	}

	/**
	 * @param rl the rl to set
	 */
	public void setRl(RoomList rl) throws RemoteException
	{
		this.rl = rl;
	}




	

}
