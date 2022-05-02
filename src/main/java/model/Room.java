package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Room implements Serializable
{
		/**
	 * 
	 */
	private static final long serialVersionUID = -4731388265538919262L;
		public HashMap<Integer, ChatLog> chatLogs = new HashMap<>();
		public HashMap<Integer, Role> userTable = new HashMap<>();
		public HashMap<Integer, Boolean> userInvites = new HashMap<>();
		
		public int chatLogTracker;
		public String description;
		public String logo;
		public int RoomID;
		public Boolean type;
		
		public Room() {
			super();
		}
		
		/**
		 * @param description
		 * @param logo
		 * @param roomID
		 * @param type
		 */
		public Room(String description, String logo, Boolean type)
		{
			super();
			this.description = description;
			this.logo = logo;
			RoomID = -1;
			this.type = type;
			this.chatLogTracker = 0;
		}

		/**
		 * @return the chatLogs
		 */
		public HashMap<Integer, ChatLog> getChatLogs()
		{
			return chatLogs;
		}

		/**
		 * @param chatLogs the chatLogs to set
		 */
		public void setChatLogs(HashMap<Integer, ChatLog> chatLogs)
		{
			this.chatLogs = chatLogs;
		}
		
		/*
		 * @param name - name of chatlog to be entered
		 */
		public Room addChatLog(String name, int userID, Boolean locked) 
		{
			if (userTable.containsKey(userID)) {
				if (userTable.get(userID).getModifyRoomPermission())
				{
					ChatLog cl = new ChatLog(chatLogTracker, name);
					cl.setLocked(locked);
					this.chatLogs.put(chatLogTracker, cl);
					this.chatLogTracker++;
					return this;
				}
			}
			return null;
		}
		
		public Boolean removeChatLog(int chatLogID, int userID)
		{
			if (userTable.containsKey(userID)) {
				if (userTable.get(userID).getModifyRoomPermission())
				{
					chatLogs.remove(chatLogID);
					return true;
				}
			}
			return false;
		}
		
		
		public ChatLog getChatLog(int chatLogID)
		{
			if (chatLogs.containsKey(chatLogID))
			{
				return chatLogs.get(chatLogID);
			}
			return null;
		}
		/**
		 * adds new user to role and user
		 * @param userID
		 * @return 
		 */
		public Boolean addUser(int userID) 
		{
			if (!userTable.containsKey(userID))
			{
				if ((type == true) || (userInvites.containsKey(userID)))
					userTable.put(userID, new Noob());
					return true;
			}
			return false;
		}
		
		/**
		 * Removes user from hashmap
		 * @param userID - for checking if user has permissions to remove another user
		 * @param removeID - id of user to be removed
		 * @return 
		 */
		public Boolean removeUser(int removeID, int userID)
		{
			System.out.println("Start");
			if (userTable.containsKey(removeID))
			{
			if ((userTable.get(userID).getRemoveUserPermission() || (removeID == userID)) && !userTable.get(removeID).getRoleName().equals("Admin"))
			{
				    System.out.println("removed");
					if (userTable.remove(removeID) != null) {
						System.out.println("Success");
					}
					return true;
			}
			}
			return false;
		}

		/**
		 * @return the userTable
		 */
		public HashMap<Integer, Role> getUserTable()
		{
			return userTable;
		}

		/**
		 * @param userTable the userTable to set
		 */
		public void setUserTable(HashMap<Integer, Role> userTable)
		{
			this.userTable = userTable;
		}

		/**
		 * @return the description
		 */
		public String getDescription()
		{
			return description;
		}

		/**
		 * @param description the description to set
		 */
		public Boolean setDescription(String description, int userID)
		{
			//if (userTable.containsKey(userID)) {
				if (userTable.get(userID).getModifyRoomPermission())
				{
					this.description = description;
					return true;
				}
			//}
			return false;
		}

		/**
		 * @return the logo
		 */
		public String getLogo()
		{
			return logo;
		}

		/**
		 * @param logo the logo to set
		 * @param userID 
		 */
		public Boolean setLogo(String logo, int userID)
		{
			if (userTable.get(userID).getModifyRoomPermission())
			{
				this.logo = logo;
				return true;
			}
			return false;
		}

		/**
		 * @return the roomID
		 */
		public int getRoomID()
		{
			return RoomID;
		}

		/**
		 * @param roomID the roomID to set
		 */
		public void setRoomID(int roomID)
		{
			RoomID = roomID;
		}

		/**
		 * @return the type
		 */
		public Boolean getType()
		{
			return type;
		}

		/**
		 * @param type the type to set
		 * @param userID - id of user to check against
		 * @return 
		 */
		public Boolean setType(Boolean type, int userID)
		{
			if (userTable.get(userID).getRoomTypePermission())
			{
				this.type = type;
				return true;
			}
			return false;
		}
		
		/**
		 * @param userID - check to see if user has permissions
		 * @param chatID - id of chat to be deleted
		 * @param chatLogID - id of chatlog message is to be deleted from
		 * @return 
		 */
		public Boolean deleteChat(int userID, int chatID, int chatLogID)
		{
			if (userTable.containsKey(userID)) {
				if (userTable.get(userID).getDeleteChatPermission())
				{
					chatLogs.get(chatLogID).deleteChat(chatID);
					return true;
				}
			}
			return false;
		}
		
		/**
		 * @param userID - id of user to check for permissions
		 * @param changeID - id of user to change role
		 * @return 
		 * @r Role that should be assigned
		 */
		public Boolean giveRole(int userID, int changeID, Role r)
		{
			if (userTable.containsKey(userID)) {
				if (userTable.get(userID).getGiveRolePermission())
				{
					userTable.put(changeID, r);
					return true;
				}
			}
			return false;
		}
		
		/*
		 * @param userID - id of user to be given admin
		 * called when creating room to give all priveledges to creator
		 */
		public void addAdmin(int userID)
		{
			userTable.put(userID, new Admin());
		}
		
		/**
		 * invites user to join, if public user will be able to add room
		 * @param userID - user that wants to invite to be checked
		 * @param inviteID - user to be added to invite list
		 * @return 
		 */
		public Boolean inviteUser(int inviteID, int userID) 
		{
			if (userTable.containsKey(userID)) {
				if (userTable.get(userID).getInvitePermission())
				{
					userInvites.put(inviteID, true);
					return true;
				}		
			}
			return false;
		}
		
		/**
		 * returns true if a user is invited
		 * @param userID
		 * @return
		 */
		public Boolean getInviteStatus(int userID)
		{
			if (userInvites.containsKey(userID)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		/**
		 * Returns array list of keys
		 */
		public ArrayList<Integer> getKeys() 
		{
			ArrayList<Integer> keys = new ArrayList<>();
			for (int key: userTable.keySet()) {
				keys.add(key);
			}
			return keys;
		}
		
		/**
		 * Returns users role name
		 * @param userID
		 * @return
		 */
		public String getUserRoleName(int userID) 
		{
			return userTable.get(userID).getRoleName();
		}
		
		/**
		 * Returns an array of strings which are the usernames of online members
		 * @param ul
		 * @return
		 */
		public ArrayList<String> getOnline(UserList ul)
		{
			User u;
			ArrayList<String> names = new ArrayList<>();
			for (int key: userTable.keySet()) {
				u = ul.getUser(key);
				if (u.getStatus())
				{
					names.add(u.getUsername());
				}
			}
			return names;
		}
		
		/**
		 * Returns an array of strings which are the usernames of offline members
		 * @param ul
		 * @return
		 */
		public ArrayList<String> getOffline(UserList ul)
		{
			User u;
			ArrayList<String> names = new ArrayList<>();
			for (int key: userTable.keySet()) {
				u = ul.getUser(key);
				if (!u.getStatus())
				{
					names.add(u.getUsername());
				}
			}
			return names;
		}
		
		/**
		 * Returns all username of members in server
		 * @param ul
		 * @return
		 */
		public ArrayList<String> getMembers(UserList ul)
		{
			User u;
			ArrayList<String> names = new ArrayList<>();
			for (int key: userTable.keySet()) {
				u = ul.getUser(key);
					names.add(u.getUsername());
			}
			return names;
		}
		
		/**
		 * Checks if user is in userTable and if they are an admin then removes them
		 * @param userID
		 */
		public void leaveServer(int userID) 
		{
			if (userTable.containsKey(userID))
			{
				if (userTable.get(userID).getRoleName() != "Admin")
				{
					userTable.remove(userID);
				}
			}
		}
		
		/**
		 * @param locked
		 * @param chatLogID
		 * @param userID
		 * Takes chatlog id and checks if user has permissino to alter status then does it
		 * @return 
		 */
		public Boolean setChatLogLocked(Boolean locked, int chatLogID, int userID)
		{
			if (userTable.get(userID).getRoomTypePermission())
			{
				if (chatLogs.containsKey(chatLogID))
				{
					chatLogs.get(chatLogID).setLocked(locked);
					return true;
				}
			}
			return false;
		}
		
		/**
		 * @param chatLogID
		 * @param userID
		 * @param message
		 * adds chat to specified chatlog
		 * @return true or false 
		 */
		public Boolean addChat(int chatLogID, int userID, String message)
		{
			if (userTable.containsKey(userID))
			{
				if ((chatLogs.get(chatLogID).getLocked() && (userTable.get(userID).getRoleName().equals("Admin") || userTable.get(userID).getRoleName().equals("Moderator"))) || !chatLogs.get(chatLogID).getLocked()){
					chatLogs.get(chatLogID).addChat(message, userID);
				} else {
					return false;
				}
				return true;
			}
			return false;
		}

		/**
		 * @param chatLogID
		 * @param userID
		 * @param message
		 * @param replyID
		 * adds reply for a chat
		 * @return true or false 
		 */
		public Boolean addChatReply(int chatLogID, int userID, String message, int replyID)
		{
			if (userTable.containsKey(userID)) {
				chatLogs.get(chatLogID).addReply(message, userID, replyID);
				return true;
			}
			return false;
		}
		
		/**
		 * @return the userInvites
		 */
		public HashMap<Integer, Boolean> getUserInvites()
		{
			return userInvites;
		}

		/**
		 * @param userInvites the userInvites to set
		 */
		public void setUserInvites(HashMap<Integer, Boolean> userInvites)
		{
			this.userInvites = userInvites;
		}

		/**
		 * @return the chatLogTracker
		 */
		public int getChatLogTracker()
		{
			return chatLogTracker;
		}

		/**
		 * @param chatLogTracker the chatLogTracker to set
		 */
		public void setChatLogTracker(int chatLogTracker)
		{
			this.chatLogTracker = chatLogTracker;
		}

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description)
		{
			this.description = description;
		}

		/**
		 * @param logo the logo to set
		 */
		public void setLogo(String logo)
		{
			this.logo = logo;
		}

		/**
		 * @param type the type to set
		 */
		public void setType(Boolean type)
		{
			this.type = type;
		}
		
		public String toString() {
			return logo;
			
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + RoomID;
			result = prime * result + chatLogTracker;
			result = prime * result + ((chatLogs == null) ? 0 : chatLogs.hashCode());
			result = prime * result + ((description == null) ? 0 : description.hashCode());
			result = prime * result + ((logo == null) ? 0 : logo.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			result = prime * result + ((userInvites == null) ? 0 : userInvites.hashCode());
			result = prime * result + ((userTable == null) ? 0 : userTable.hashCode());
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
			Room other = (Room) obj;
			if (RoomID != other.RoomID)
				return false;
			if (chatLogTracker != other.chatLogTracker)
				return false;
			if (chatLogs == null)
			{
				if (other.chatLogs != null)
					return false;
			} else if (!chatLogs.equals(other.chatLogs)) {
				System.out.println("chatlog");
				return false;}
			if (description == null)
			{
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			if (logo == null)
			{
				if (other.logo != null)
					return false;
			} else if (!logo.equals(other.logo))
				return false;
			if (type == null)
			{
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			if (userInvites == null)
			{
				if (other.userInvites != null)
					return false;
			} else if (!userInvites.equals(other.userInvites))
				return false;
			if (userTable == null)
			{
				if (other.userTable != null)
					return false;
			} else if (!userTable.equals(other.userTable))
				return false;
			return true;
		}
		
		
}

