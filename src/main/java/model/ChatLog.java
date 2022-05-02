package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChatLog implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2583302736466311742L;
	public HashMap<Integer, Chat> chat = new HashMap<>();
	public int chatLogID;
	public String chatLogName;
	public int chatTracker;
	public Boolean locked;
	
	public ChatLog() {
		super();
	}
	
	/**
	 * @param chat
	 * @param chatLogID
	 * @param chatLogName
	 */
	public ChatLog(int chatLogID, String chatLogName)
	{
		super();
		this.chatLogID = chatLogID;
		this.chatLogName = chatLogName;
		this.chatTracker = 0;
		this.locked = false;
	}
	

	/**
	 * @return the chat
	 */
	public Chat getChat(int chatID)
	{
		return chat.get(chatID);
	}

	/**
	 * @param chat the chat to set
	 * @return 
	 */
	public void addChat(String message, int userID)
	{
		chat.put(chatTracker, new Chat(chatTracker, userID, message, new Date(), false));
		chatTracker++;
	}

	/**
	 * @param chat the chat to set
	 * @param message string containing text
	 * @param userID the id of the user to make the chat
	 */
	public void addReply(String message, int userID, int chatID)
	{
		this.chat.put(chatTracker, new Chat(chatTracker, userID, message, new Date(), false, chatID));
		this.chatTracker++;
	}
	
	/**
	 * @return the chatLogID
	 */
	public int getChatLogID()
	{
		return chatLogID;
	}

	/**
	 * @param chatLogID the chatLogID to set
	 */
	public void setChatLogID(int chatLogID)
	{
		this.chatLogID = chatLogID;
	}

	/**
	 * @return the chatLogName
	 */
	public String getChatLogName()
	{
		return chatLogName;
	}

	/**
	 * @param chatLogName the chatLogName to set
	 */
	public void setChatLogName(String chatLogName)
	{
		this.chatLogName = chatLogName;
	}
	
	/**
	 * @return the chatLogName
	 */
	public Boolean getLocked()
	{
		return locked;
	}

	/**
	 * @param chatLogName the chatLogName to set
	 */
	public void setLocked(Boolean locked)
	{
		this.locked = locked;
	}
	

	public Boolean pinMessage(int chatID) {
		if ((chat.size() > chatID) && (!chat.get(chatID).getDeleted()))
		{
			chat.get(chatID).setPinned(true);
			return true;
		}
		return false;
	}
	
	/**
	 * @return ArrayList of pinned messages
	 */
	public List<Chat> getPinned() {
		List<Chat> pinned = new ArrayList<>();
		for (int key: chat.keySet()) {
			if (chat.get(key).getPinned())
			{
				pinned.add(chat.get(key));
			}
		}
		return pinned;
	}
	
	/**
	 * @param chatID - id of chat to be updated
	 */
	public void deleteChat(int chatID)
	{
		if (chat.size() > chatID)
		{
			chat.get(chatID).setDeleted(true);
		}
	}
	
	/**
	 * returns chatIDs of messages that arent deleted
	 */
	public List<Integer> getMessages()
	{
		int i;
		List<Integer> mes = new ArrayList<>();
		for (i = 0; i < chat.size(); i++)
		{
			if (!chat.get(i).getDeleted())
			{
				mes.add(i);
			}
		}
		return mes;
		
	}


	/**
	 * @return the chat
	 */
	public HashMap<Integer,Chat> getChat()
	{
		return chat;
	}


	/**
	 * @param chat the chat to set
	 */
	public void setChat(HashMap<Integer,Chat> chat)
	{
		this.chat = chat;
	}


	/**
	 * @return the chatTracker
	 */
	public int getChatTracker()
	{
		return chatTracker;
	}

	public String toString() {
		return chatLogName;
	}

	/**
	 * @param chatTracker the chatTracker to set
	 */
	public void setChatTracker(int chatTracker)
	{
		this.chatTracker = chatTracker;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chat == null) ? 0 : chat.hashCode());
		result = prime * result + chatLogID;
		result = prime * result + ((chatLogName == null) ? 0 : chatLogName.hashCode());
		result = prime * result + chatTracker;
		result = prime * result + ((locked == null) ? 0 : locked.hashCode());
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
		ChatLog other = (ChatLog) obj;
		if (chat == null)
		{
			if (other.chat != null)
				return false;
		} else if (!chat.equals(other.chat))
		{
			System.out.println("chat list");
			return false;
		}
		if (chatLogID != other.chatLogID)
			return false;
		if (chatLogName == null)
		{
			if (other.chatLogName != null)
				return false;
		} else if (!chatLogName.equals(other.chatLogName))
			return false;
		if (chatTracker != other.chatTracker)
			return false;
		if (locked == null)
		{
			if (other.locked != null)
				return false;
		} else if (!locked.equals(other.locked))
			return false;
		return true;
	}
}

