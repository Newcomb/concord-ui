package model;

import java.io.Serializable;
import java.util.Date;

public class Chat implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4317744675522610005L;
	int chatID;
	int senderID;
	public String message;
	Date timeStamp;
	Boolean pinned;
	public Boolean deleted;
	public int replyToID;
	
	public Chat() {
		super();
	}
	
	/**
	 * @param chatID
	 * @param senderID
	 * @param message
	 * @param timeStamp
	 * @param pinned
	 */
	public Chat(int chatID, int senderID, String message, Date timeStamp, Boolean pinned)
	{
		super();
		this.chatID = chatID;
		this.senderID = senderID;
		this.message = message;
		this.timeStamp = timeStamp;
		this.pinned = pinned;
		this.deleted = false;
		this.replyToID = -1;
	}
	
	/**
	 * @param chatID
	 * @param senderID
	 * @param message
	 * @param timeStamp
	 * @param pinned
	 */
	public Chat(int chatID, int senderID, String message, Date timeStamp, Boolean pinned, int replyTo)
	{
		super();
		this.chatID = chatID;
		this.senderID = senderID;
		this.message = message;
		this.timeStamp = timeStamp;
		this.pinned = pinned;
		this.deleted = false;
		this.replyToID = replyTo;
	}

	/**
	 * @return the chatID
	 */
	public int getChatID()
	{
		return chatID;
	}

	/**
	 * @param chatID the chatID to set
	 */
	public void setChatID(int chatID)
	{
		this.chatID = chatID;
	}
	
	/**
	 * @return the chatID
	 */
	public int getReplyToID()
	{
		return replyToID;
	}

	/**
	 * @param chatID the chatID to set
	 */
	public void setReplyToID(int chatID)
	{
		this.replyToID = chatID;
	}

	/**
	 * @return the senderID
	 */
	public int getSenderID()
	{
		return senderID;
	}

	/**
	 * @param senderID the senderID to set
	 */
	public void setSenderID(int senderID)
	{
		this.senderID = senderID;
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the pinned
	 */
	public Boolean getPinned()
	{
		return pinned;
	}

	/**
	 * @param pinned the pinned to set
	 */
	public void setPinned(Boolean pinned)
	{
		if (this.pinned == null) {
		this.pinned = pinned;
		}
		else {
			if (this.pinned == true) {
				this.pinned = false;
			} else {
				this.pinned = true;
			}
		}
	}
	
	public void setDeleted(Boolean flag)
	{
		this.deleted = flag;
	}
	
	public Boolean getDeleted()
	{
		return deleted;
	}
	
	public String toString() {
		return chatID + " : " + message;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + chatID;
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((pinned == null) ? 0 : pinned.hashCode());
		result = prime * result + replyToID;
		result = prime * result + senderID;
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Chat other = (Chat) obj;
		if (chatID != other.chatID)
		{
			return false;
		}
		if (deleted == null)
		{
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
		{
			System.out.println("deleted");
			return false;
		}
		if (message == null)
		{
			if (other.message != null)
			{
				System.out.println("deleted");
				return false;
			}
		} else if (!message.equals(other.message))
		{
			System.out.println("deleted");
			return false;
		}
		if (pinned == null)
		{
			if (other.pinned != null)
			{
				System.out.println("deleted");
				return false;
			}
		} else if (!pinned.equals(other.pinned))
		{
			System.out.println("deleted");
			return false;
		}
		if (replyToID != other.replyToID)
			return false;
		if (senderID != other.senderID)
			return false;
		if (timeStamp == null)
		{
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
		{
			System.out.println("deleted");
			return false;
		}
		return true;
	}
	
}
