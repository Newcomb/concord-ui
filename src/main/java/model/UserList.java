package model;

import java.io.Serializable;
import java.util.HashMap;

public class UserList implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1133355549345367927L;
	private HashMap<Integer, User> users = new HashMap<>();
	private int userTracker;

	public UserList()
	{
		super();
		this.userTracker = 0;
	}
	

	
	/**
	 * @return the users
	 * @param userID of User you want
	 */
	public User getUser(int userID) {
		User user; 
		user = users.get(userID);
		return user;
	}
	
	/**
	 * Sets id based on entry tracker
	 * @param User object you want to add
	 */
	public void addUser(User user) {
		if (users.containsKey(user.getUserID())) {
			users.put(user.getUserID(), user);
		}
		else {
			user.setUserID(userTracker);
			this.userTracker++;
			users.put(user.getUserID(), user);
		}
	}

	public Integer grabUserByUsername(String username)
	{
		for (int key: users.keySet()) {
			if (users.get(key).getUsername().equals(username))
			{
				return key;
			}
		}
		return -1;
	}


	/**
	 * @return the users
	 */
	public HashMap<Integer, User> getUsers()
	{
		return users;
	}



	/**
	 * @param users the users to set
	 */
	public void setUsers(HashMap<Integer, User> users)
	{
		this.users = users;
	}



	/**
	 * @return the userTracker
	 */
	public int getUserTracker()
	{
		return userTracker;
	}



	/**
	 * @param userTracker the userTracker to set
	 */
	public void setUserTracker(int userTracker)
	{
		this.userTracker = userTracker;
	}



	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + userTracker;
		result = prime * result + ((users == null) ? 0 : users.hashCode());
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
		UserList other = (UserList) obj;
		if (userTracker != other.userTracker)
			return false;
		if (users == null)
		{
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
	
}
