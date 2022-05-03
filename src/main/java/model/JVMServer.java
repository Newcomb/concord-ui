package model;

import java.beans.XMLDecoder;

import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
// import java.net.MalformedURLException;
// import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class JVMServer 
			extends UnicastRemoteObject 
			implements JVMInterface
{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public static final String SERIALIZED_FILE_NAME="Server.xml";
	

	HashMap<Integer, RMIObserver> clients = new HashMap<>();
	UserList ul = new UserList();
	public RoomList rl = new RoomList();
	
	// Try to load in XML
	private static JVMServer server = JVMServer.readFromDisk();

	public JVMServer() throws RemoteException
	{
		
	}
	
	// If no file exists then start new server and write file within it when other functions are called
	public static JVMServer startServer() throws RemoteException {
		if (server == null) {
			server = new JVMServer();
		}
		return server;
	}
	
	
	// Stores data to an XML file
	public void storeDataDisk() throws RemoteException
	{
		HashMap<Integer, RMIObserver> copy = clients;
		clients = new HashMap<>();
		XMLEncoder encoder=null;
		try{
		encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Server.xml")));
		}catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File Server.xml");
		}
		encoder.writeObject(this);
		encoder.close();
		
		clients = copy;
	}
	
	// Decodes data from xml file
	public static JVMServer readFromDisk() {
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File Server.xml not found");
		}
		if (decoder != null)
		{
			JVMServer serv=(JVMServer)decoder.readObject();
			// XML reverses ArrayLists for some reason so I had to manually go through and reverse them
			for (int key: serv.ul.getUsers().keySet())
			{
				Collections.sort(serv.ul.getUsers().get(key).getRooms());
				Collections.reverse(serv.ul.getUsers().get(key).getBlocked());
				Collections.reverse(serv.ul.getUsers().get(key).getDirectMessages());
				
			}
			return serv;
		} else {
			return null;
		}
		
	}
	
	// ALlows for comparison between JVMServer instances
	public Boolean equals(JVMServer that) {
		if(ul.getUsers().size() != that.ul.getUsers().size())
		{
			System.out.println("User list size");
			return false;
		}
		
		if ( !ul.equals(that.ul)) {
			System.out.println("User list");
			return false;}
		
		if ( !rl.equals(that.rl)) {
			System.out.println("r list");
			return false;}
		
		for (int key: ul.getUsers().keySet()) {
			if ( !that.ul.getUser(key).equals(ul.getUser(key))) {
				System.out.println("User");
				return false;}
		}
		
		for (int key: rl.getRooms().keySet()) {
			if ( !that.rl.getRoom(key).equals(rl.getRoom(key))) {
				return false;}
		}
		
		
		for (int key: rl.getRooms().keySet())
		{
			for (int chatKey: rl.getRoom(key).getChatLogs().keySet()) {
				if ( !that.rl.getRoom(key).getChatLog(chatKey).equals(rl.getRoom(key).getChatLog(chatKey))) {
					return false;}
			}
		}
		return true;
	}
	

	//  Allows client to auth and recieve a user
	@Override
	public User authenticate(String username, String password, RMIObserver c) throws RemoteException
	{
		User u = ul.getUser(ul.grabUserByUsername(username));
		if (u != null)
		{
			if (u.getPassword().equals(password))
			{
				addObserver(c, u.getUserID());
				return u;
			}
		}
		storeDataDisk();
		return null;
	}
	
	@Override
	public Boolean createUser(String rn, String un, String password, String data, String link, Boolean status) throws RemoteException
	{
		Boolean flag = false;
		for (Integer key : ul.getUsers().keySet()) {
			if (ul.getUser(key).getUsername().equals(un)) {
				flag = true;
			}
		}
		if (!flag) {
			ul.addUser(new User(un, rn, link, status, data, password));
			return !flag;
		}
		return flag;
	}
	
	// Allows client to log out
	@Override
	public Boolean logOut(int userID) throws RemoteException
	{
		if(clients.containsKey(userID))
		{
			clients.remove(userID);
			return true;
		}
		storeDataDisk();
		return false;
	}
	
	
	// Returns user based on username
	@Override 
	public User getUser(String username) throws RemoteException
	{
		for (int key: ul.getUsers().keySet()) {
			if (ul.getUsers().get(key).getUsername() == username)
			{
				return ul.getUsers().get(key);
			}
		}
		return null;
		
	}
	
	
	@Override
	public Boolean createDirectMessage(int otherID, int userID) throws RemoteException
	{
		for (Integer roomID : rl.getRooms().keySet()) {
			if (rl.getRoom(roomID).toString().equals(ul.getUser(userID).getUsername() + " + " + ul.getUser(otherID).getUsername()) || rl.getRoom(roomID).toString().equals(ul.getUser(otherID).getUsername() + " + " + ul.getUser(userID).getUsername())){
				return false;
			}
		}
		Room dm = ul.getUser(userID).createDirectMessage(otherID, rl, ul);
		if (dm != null) {
			ul.getUser(otherID).addDirectMessage(dm.getRoomID());
			rl.getRooms().put(dm.getRoomID(), dm);
			System.out.println(rl.getRoom(dm.getRoomID()).getDescription());
			storeDataDisk();
			notifyClient(dm.getRoomID());
			return true;
		}
		return false;
	}
	
	
	@Override
	// Will need to use notify function
	public Boolean addChatLog(String name, int roomID, int userID, Boolean locked) throws RemoteException
	{	
		Room r = rl.getRoom(roomID).addChatLog(name, userID, locked);
		if (r != null)
		{
			rl.getRooms().put(r.getRoomID(), r);
			System.out.println(rl.getRoom(roomID).getChatLog(1));
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override
	// Will need to use notify function
	public Boolean removeChatLog(int roomID, int chatLogID, int userID) throws RemoteException
	{
		if (rl.getRoom(roomID).removeChatLog(chatLogID, userID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override 
	public ChatLog getChatLog(int chatLogID, int roomID) throws RemoteException 
	{
		return rl.getRoom(roomID).getChatLog(chatLogID);
	}

	@Override
	public Boolean addUserToRoom(int newUserID, int roomID) throws RemoteException
	{
		if (rl.getRoom(roomID).addUser(newUserID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override 
	public Boolean removeUserFromRoom(int removeID, int userID, int roomID) throws RemoteException
	{
		if(rl.getRoom(roomID).removeUser(removeID, userID)) {
			ul.getUser(removeID).removeRoom(roomID);
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean setRoomDescription(int userID, int roomID, String desc) throws RemoteException
	{
		if (rl.getRoom(roomID).setDescription(desc, userID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean setRoomLogo(int userID, int roomID, String logo) throws RemoteException
	{
		if (rl.getRoom(roomID).setLogo(logo, userID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
		
	}
	
	@Override
	public Boolean setRoomType(Boolean type, int userID, int roomID) throws RemoteException
	{
		if (rl.getRoom(roomID).setType(type, userID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean deleteChat(int roomID, int chatLogID, int chatID, int userID) throws RemoteException
	{
		if (rl.getRoom(roomID).deleteChat(userID, chatID, chatLogID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean giveRole(int userID, int giveID, int roomID, Role r) throws RemoteException
	{
		if (rl.getRoom(roomID).giveRole(userID, giveID, r)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	
	@Override 
	public Boolean inviteUserToRoom(int userID, int inviteID, int roomID) throws RemoteException
	{
		if (rl.getRoom(roomID).inviteUser(inviteID, userID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean setChatLogLocked(int userID, int chatLogID, int roomID, Boolean locked) throws RemoteException
	{
		if (rl.getRoom(roomID).setChatLogLocked(locked, chatLogID, userID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean addChat(int userID, int chatLogID, int roomID, String message) throws RemoteException
	{
		if (rl.getRoom(roomID).addChat(chatLogID, userID, message)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean chatLogReply(String message, int userID, int roomID, int chatLogID, int replyID) throws RemoteException
	{
		if (rl.getRoom(roomID).addChatReply(chatLogID, userID, message, replyID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean pinMessage(int chatID, int roomID, int chatLogID, int userID) throws RemoteException
	{
		if (rl.getRoom(roomID).getUserTable().get(userID).getDeleteChatPermission()) {
		if (rl.getRoom(roomID).getChatLog(chatLogID).pinMessage(chatID)) {
			storeDataDisk();
			notifyClient(roomID);
			return true;
		}
		}
		return false;
	}
	
	@Override
	public List<Chat> getPinnedMessages(int roomID, int chatLogID) throws RemoteException
	{
		return rl.getRoom(roomID).getChatLog(chatLogID).getPinned();
	}
	
	@Override
	public Room getRoom(int roomID) throws RemoteException
	{
		return rl.getRoom(roomID);
	}
	
	@Override
	public String getRole(int roomID, int userID) throws RemoteException
	{
		return rl.getRoom(roomID).getUserRoleName(userID);
	}

	@Override 
	public ArrayList<String> getOnline(int roomID) throws RemoteException
	{
		return rl.getRoom(roomID).getOnline(ul);
	}
	
	@Override 
	public ArrayList<String> getOffline(int roomID) throws RemoteException
	{
		return rl.getRoom(roomID).getOffline(ul);
	}
	
	@Override
	public String getRoomLogo(int roomID) throws RemoteException
	{
		return rl.getRoom(roomID).getLogo();
	}
	
	@Override
	public String getRoomDescription(int roomID) throws RemoteException
	{
		return rl.getRoom(roomID).getDescription();
	}
	
	
	@Override
	public Boolean createRoom(String des, String logo, Boolean type, User u) throws RemoteException
	{
		rl.addRoom(u.createRoom(des, logo, type), u);
		System.out.println(u.getRooms());
		Collections.sort(u.rooms);
		ul.addUser(u);
		System.out.println(ul.getUser(0).getRooms());
		storeDataDisk();
		System.out.println(rl.getRoom(rl.getRoomTracker()-1).getLogo());
		notifyClient(rl.getRoomTracker() - 1);
		return true;
	}

	/**
	 * @return the clients
	 */
	public HashMap<Integer, RMIObserver> getClients()
	{
		return clients;
	}

	/**
	 * @param clients the clients to set
	 */
	public void setClients(HashMap<Integer, RMIObserver> clients)
	{
		this.clients = clients;
	}

	/**
	 * @return the ul
	 */
	@Override
	public UserList getUl()
	{
		return ul;
	}

	/**
	 * @param ul the ul to set
	 */
	public void setUl(UserList ul) throws RemoteException
	{
		this.ul = ul;
	}

	/**
	 * @return the rl
	 */
	@Override
	public RoomList getRl() throws RemoteException
	{
		return rl;
	}

	/**
	 * @param rl the rl to set
	 */
	public void setRl(RoomList rl)
	{
		this.rl = rl;
	}


	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	@Override
	public Boolean notifyClient(int roomID) throws RemoteException
	{
		for (int key: rl.getRoom(roomID).userTable.keySet())
			if (clients.containsKey(key)) {
				clients.get(key).updateRl();
				clients.get(key).updateU();
			}
		return true;
	}

	@Override
	public Boolean updateUser(User u) throws RemoteException
	{
		if (ul.getUsers().put(u.getUserID(), u) != null)
			{
				storeDataDisk();
				return true;
			}
		return false;
		
	}
	

	@Override
	public void addObserver(RMIObserver o, int userID) throws RemoteException
	{
		clients.put(userID, o);
		
	}

	@Override
	public void removeObserver(int userID) throws RemoteException
	{
		clients.remove(userID);
		
	}
	
	@Override 
	public void notifyObserver(JVMServer o) throws RemoteException
	{
		// o.updateRL();
	}

	@Override
	public void setChatLogName(User u, int chatLogID, int roomID, String text) throws RemoteException
	{
		String role = rl.getRoom(roomID).getUserTable().get(u.getUserID()).getRoleName();
		if (role.equals("Admin") || role.equals("Moderator")) {
		rl.getRoom(roomID).getChatLog(chatLogID).setChatLogName(text);
		notifyClient(roomID);
		}
	}


}
