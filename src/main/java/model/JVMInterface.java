package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface JVMInterface extends Remote
{
	
	public void storeDataDisk() throws RemoteException;
	
	// public Boolean readFilesFromDisk();
	
	
	public Boolean logOut(int userID) throws RemoteException;
	
	// public Boolean notifyUser(int userID) throws RemoteException;
	
	public User getUser(String username) throws RemoteException;
	
	
	
	public Boolean removeChatLog(int roomID, int chatLogID, int userID) throws RemoteException;
	
	public ChatLog getChatLog(int chatLogID, int roomID) throws RemoteException;
	
	public Boolean addUserToRoom(int newUserID, int roomID) throws RemoteException;
	
	public Boolean removeUserFromRoom(int removeID, int userID, int roomID) throws RemoteException;
	
	public Boolean setRoomDescription(int userID, int roomID, String desc) throws RemoteException;
	
	public Boolean setRoomLogo(int userID, int roomID, String logo) throws RemoteException;
	
	public Boolean setRoomType(Boolean type, int userID, int roomID) throws RemoteException;
	
	public Boolean deleteChat(int roomID, int chatLogID, int chatID, int userID) throws RemoteException;
	
	public Boolean giveRole(int userID, int giveID, int roomID, Role r) throws RemoteException;
	
	public Boolean inviteUserToRoom(int userID, int inviteID, int roomID) throws RemoteException;
	
	public Boolean setChatLogLocked(int userID, int chatLogID, int roomID, Boolean locked) throws RemoteException;

	public Boolean addChat(int userID, int chatLogID, int roomID, String message) throws RemoteException;

	public Boolean chatLogReply(String message, int userID, int roomID, int chatLogID, int replyID) throws RemoteException;
	
	public Boolean pinMessage(int chatID, int roomID, int chatLogID, int userID) throws RemoteException;
	
	public List<Chat> getPinnedMessages(int roomID, int chatLogID) throws RemoteException;

	public Room getRoom(int roomID) throws RemoteException;

	String getRole(int roomID, int userID) throws RemoteException;

	ArrayList<String> getOnline(int roomID) throws RemoteException;

	ArrayList<String> getOffline(int roomID) throws RemoteException;

	String getRoomDescription(int roomID) throws RemoteException;

	String getRoomLogo(int roomID) throws RemoteException;

	public RoomList getRl() throws RemoteException;

	public Room createRoom(String des, String logo, Boolean type, User u) throws RemoteException;
	
	
	public Boolean updateUser(User u) throws RemoteException;

	/**
	 * @param ul the ul to set
	 */
	UserList getUl() throws RemoteException;

	Boolean notifyClient(int clientID) throws RemoteException;

	Boolean createDirectMessage(int otherID, int userID) throws RemoteException;

	// User authenticate(String username, String password, JVMClient c) throws RemoteException;

	void notifyObserver(JVMServer o) throws RemoteException;

	void removeObserver(int userID) throws RemoteException;

	// void addObserver(JVMClient o, int userID) throws RemoteException;

	User authenticate(String username, String password, RMIObserver c) throws RemoteException;

	void addObserver(RMIObserver o, int userID) throws RemoteException;

	Boolean createUser(String rn, String un, String password, String data, String link, Boolean status)
			throws RemoteException;

	Boolean addChatLog(String name, int roomID, int userID, Boolean locked) throws RemoteException;

	public void setChatLogName(User u, int chatLogID, int roomID, String text) throws RemoteException;


	public Boolean addGameChatLog(String type, int roomID, int userID, List<Integer> users) throws RemoteException;
	
}
