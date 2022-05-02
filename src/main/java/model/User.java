package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable
{
	public String username;
	public String password;
	public String realName;
	public int userID;
	public List<Integer> rooms = new ArrayList<>();
	public List<Integer> directMessages = new ArrayList<>();
	public List<Integer> blocked = new ArrayList<>();
	public String profilePic;
	public Boolean status;
	public String profileData;
	
	private static final long serialVersionUID = 6328716265330861027L;
	
	public User(String username, String realName, String profilePic, Boolean status,
			String profileData, String password)
	{
		super();
		this.username = username;
		this.userID = -1;
		this.profilePic = profilePic;
		this.status = status;
		this.profileData = profileData;
		this.password = password;
		this.realName = realName;
	}
	
	public User() {
		super();
	}

	/**
	 * 
	 * @return - realName
	 */
	public String getRealName() 
	{
		return realName;
	}
	
	/**
	 * 
	 * @param name - name to be assigned to realname
	 */
	public void setRealName(String name) 
	{
		this.realName = name;
	}
	
	/**
	 * Returns the password
	 * @return
	 */
	public String getPassword() 
	{
		return password;
	}
	
	/**
	 * Sets new password
	 * @param pass - String of new password
	 */
	public void setPassword(String pass)
	{
		this.password = pass;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the userID
	 */
	public int getUserID()
	{
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID)
	{
		this.userID = userID;
	}

	/**
	 * @return the rooms
	 */
	public List<Integer> getRooms()
	{
		return rooms;
	}

	/**
	 * @param rooms the rooms to set
	 */
	public void setRooms(List<Integer> rooms)
	{
		this.rooms = rooms;
	}

	/**
	 * @return the directMessages
	 */
	public List<Integer> getDirectMessages()
	{
		return directMessages;
	}

	/**
	 * @param directMessages the directMessages to set
	 */
	public void setDirectMessages(List<Integer> directMessages)
	{
		this.directMessages = directMessages;
	}

	/**
	 * @return the profilePic
	 */
	public String getProfilePic()
	{
		return profilePic;
	}

	/**
	 * @param profilePic the profilePic to set
	 */
	public void setProfilePic(String profilePic)
	{
		this.profilePic = profilePic;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status)
	{
		this.status = status;
	}

	/**
	 * @return the profileData
	 */
	public String getProfileData()
	{
		return profileData;
	}
	/**
	 * @param userID - id to block
	 */
	public Boolean addBlocked(int userID)
	{
		if (!blocked.contains(userID))
		{
			blocked.add(userID);
			return true;
		}
		return false;
	}
	
	/**
	 * removes block
	 * @param userID - user id to unblock
	 */
	public Boolean removeBlocked(int userID)
	{
		int index = blocked.indexOf(userID);
		if (index != -1)
		{
			blocked.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param userID
	 * @return - true or false based on whether the userID is blocked
	 */
	public Boolean isBlocked(int userID)
	{
		if (blocked.contains(userID))
		{
			return true;
		}
		return false;
	}

	/**
	 * @param profileData the profileData to set
	 */
	public void setProfileData(String profileData)
	{
		this.profileData = profileData;
	}
	
	/**
	 * THIS NEEDS WORK
	 * @param roomID id of room to add
	 * @param r - list of rooms to check status
	 * check if status is public then add otherwise check if you are invited
	 * @return 
	 */
	public boolean addRoom(int roomID, RoomList r) {
		if (r.getRoom(roomID).getType()) 
		{
			rooms.add(roomID);
			return true;
		}
		else {
			if (r.getRoom(roomID).getInviteStatus(this.userID)) {
				rooms.add(roomID);
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Removes room if it exists from rooms
	 * @param roomID
	 */
	public void removeRoom(int roomID) {
		int index = rooms.indexOf(roomID);
		if (index != -1) {
			rooms.remove(index);
			if (rooms.contains(roomID)) {
				System.out.println("Nope");
			}
		}
	}
	
	
	/** 
	 * Creates a dm, adds user id of both people as Admin, adds to directMessages list of both people
	 * @param dmID
	 * @param userID
	 * @param r
	 * @param ul
	 */
	public Room createDirectMessage(int userID, RoomList r, UserList ul) {
		if (ul.getUsers().containsKey(userID) && ul.getUsers().containsKey(this.userID))
		{
			if (!ul.getUser(userID).isBlocked(this.getUserID()) && !ul.getUser(this.userID).isBlocked(userID))
			{
				Room dm = new Room("Direct Message", ul.getUser(userID).getUsername() + " + " + username, false);
				dm.addAdmin(userID);
				dm.addAdmin(this.getUserID());
				dm.addChatLog("New DM", userID, false);
				directMessages.add(dm.getRoomID());
				ul.getUser(userID).addDirectMessage(dm.getRoomID());
				r.addRoom(dm, this);
				return dm;
			}
		}
		return null;
	}
	
	
	/**
	 * Addes dm to the list of directMessages
	 * @param dmID
	 */
	public void addDirectMessage(int dmID) {
		directMessages.add(dmID);
	}
	
	/**
	 * removes the dm from the users list
	 * @param dmID 
	 */
	public void removeDirectMessage(int dmID) {
		int index = directMessages.indexOf(dmID);
		if (index != -1) {
			directMessages.remove(dmID);
		}
	}
	
	
	/**
	 * creates a room and adds it to the RoomList, adds room to list or user rooms, adds user as admin
	 * @param des - description of room
	 * @param logo - logo for room
	 * @param type - room type public or public
	 * @param r - list of all rooms to get id
	 */
	public Room createRoom(String des, String logo, Boolean type)
	{
		Room rm = new Room(des, logo, type);
		rm.addAdmin(this.getUserID());
		return rm;
	}

	/**
	 * @return the blocked
	 */
	public List<Integer> getBlocked()
	{
		return blocked;
	}

	/**
	 * @param blocked the blocked to set
	 */
	public void setBlocked(List<Integer> blocked)
	{
		this.blocked = blocked;
	}
	
	public String toString() {
		return username;
		
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blocked == null) ? 0 : blocked.hashCode());
		result = prime * result + ((directMessages == null) ? 0 : directMessages.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((profileData == null) ? 0 : profileData.hashCode());
		result = prime * result + ((profilePic == null) ? 0 : profilePic.hashCode());
		result = prime * result + ((realName == null) ? 0 : realName.hashCode());
		result = prime * result + ((rooms == null) ? 0 : rooms.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + userID;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (blocked == null)
		{
			if (other.blocked != null)
				return false;
		} else if (!blocked.equals(other.blocked))
			return false;
		if (directMessages == null)
		{
			if (other.directMessages != null)
				return false;
		} else if (!directMessages.equals(other.directMessages))
			return false;
		if (password == null)
		{
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (profileData == null)
		{
			if (other.profileData != null)
				return false;
		} else if (!profileData.equals(other.profileData))
			return false;
		if (profilePic == null)
		{
			if (other.profilePic != null)
				return false;
		} else if (!profilePic.equals(other.profilePic))
			return false;
		if (realName == null)
		{
			if (other.realName != null)
				return false;
		} else if (!realName.equals(other.realName))
			return false;
		if (rooms == null)
		{
			if (other.rooms != null)
				return false;
		} else if (!rooms.equals(other.rooms))
			return false;
		if (status == null)
		{
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (userID != other.userID)
			return false;
		if (username == null)
		{
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
