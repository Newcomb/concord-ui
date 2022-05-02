package model;

import java.io.Serializable;
import java.util.HashMap;

public class RoomList implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8547201075520092856L;
	public HashMap<Integer, Room> rooms = new HashMap<>();
	public int roomTracker;

	/**
	 * @param rooms
	 */
	public RoomList()
	{
		super();
		this.roomTracker = 0;
	}
	
	public void addRoom(Room r, User u) {
		r.setRoomID(roomTracker);
		this.roomTracker++;
		rooms.put(r.getRoomID(), r);
		if (r.getDescription().equals("Direct Message")) {
			u.directMessages.add(r.getRoomID());
		}
		else {
		u.rooms.add(r.getRoomID());
		}
	}

	public Room getRoom(int roomID) {
		return rooms.get(roomID);
	}
	
	public int getSize() {
		return rooms.size();
	}

	/**
	 * @return the rooms
	 */
	public HashMap<Integer, Room> getRooms()
	{
		return rooms;
	}

	/**
	 * @param rooms the rooms to set
	 */
	public void setRooms(HashMap<Integer, Room> rooms)
	{
		this.rooms = rooms;
	}

	/**
	 * @return the roomTracker
	 */
	public int getRoomTracker()
	{
		return roomTracker;
	}

	/**
	 * @param roomTracker the roomTracker to set
	 */
	public void setRoomTracker(int roomTracker)
	{
		this.roomTracker = roomTracker;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + roomTracker;
		result = prime * result + ((rooms == null) ? 0 : rooms.hashCode());
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
		RoomList other = (RoomList) obj;
		if (roomTracker != other.roomTracker)
			return false;
		if (rooms == null)
		{
			if (other.rooms != null)
				return false;
		} else if (!rooms.equals(other.rooms))
			return false;
		return true;
	}
}
