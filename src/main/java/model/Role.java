package model;

import java.io.Serializable;

public class Role implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 668105775433326932L;
	public String roleName;
	public Boolean deleteChatPermission;
	public Boolean removeUserPermission;
	public Boolean roomTypePermission;
	public Boolean giveRolePermission;
	public Boolean invitePermission;
	public Boolean modifyRoom;

	
	/**
	 * @param roleName
	 * @param deleteChatPermission
	 * @param removeUserPermission
	 * @param roomTypePermission
	 * @param giveRolePermission
	 * @param invitePermission
	 */
	public Role(String roleName, Boolean deleteChatPermission, Boolean removeUserPermission, Boolean roomTypePermission,
			Boolean giveRolePermission, Boolean invitePermission, Boolean modifyRoom)
	{
		super();
		this.roleName = roleName;
		this.deleteChatPermission = deleteChatPermission;
		this.removeUserPermission = removeUserPermission;
		this.roomTypePermission = roomTypePermission;
		this.giveRolePermission = giveRolePermission;
		this.invitePermission = invitePermission;
		this.modifyRoom = modifyRoom;
	}

	public Role()
	{
		super();
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName()
	{
		return roleName;
	}


	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}


	/**
	 * @return the deleteChatPermission
	 */
	public Boolean getDeleteChatPermission()
	{
		return deleteChatPermission;
	}


	/**
	 * @param deleteChatPermission the deleteChatPermission to set
	 */
	public void setDeleteChatPermission(Boolean deleteChatPermission)
	{
		this.deleteChatPermission = deleteChatPermission;
	}


	/**
	 * @return the removeUserPermission
	 */
	public Boolean getRemoveUserPermission()
	{
		return removeUserPermission;
	}


	/**
	 * @param removeUserPermission the removeUserPermission to set
	 */
	public void setRemoveUserPermission(Boolean removeUserPermission)
	{
		this.removeUserPermission = removeUserPermission;
	}


	/**
	 * @return the roomTypePermission
	 */
	public Boolean getRoomTypePermission()
	{
		return roomTypePermission;
	}


	/**
	 * @param roomTypePermission the roomTypePermission to set
	 */
	public void setRoomTypePermission(Boolean roomTypePermission)
	{
		this.roomTypePermission = roomTypePermission;
	}


	/**
	 * @return the giveRolePermission
	 */
	public Boolean getGiveRolePermission()
	{
		return giveRolePermission;
	}


	/**
	 * @param giveRolePermission the giveRolePermission to set
	 */
	public void setGiveRolePermission(Boolean giveRolePermission)
	{
		this.giveRolePermission = giveRolePermission;
	}


	/**
	 * @return the invitePermission
	 */
	public Boolean getInvitePermission()
	{
		return invitePermission;
	}


	/**
	 * @param invitePermission the invitePermission to set
	 */
	public void setInvitePermission(Boolean invitePermission)
	{
		this.invitePermission = invitePermission;
	}
	
	/**
	 * @return the invitePermission
	 */
	public Boolean getModifyRoomPermission()
	{
		return this.modifyRoom;
	}


	/**
	 * @param invitePermission the invitePermission to set
	 */
	public void setModifyRoomPermission(Boolean modifyRoom)
	{
		this.modifyRoom = modifyRoom;
	}
	
	/*
	 * returns if permissions created are above moderator level
	 */
	public Boolean aboveModerator() 
	{
		if (roomTypePermission || modifyRoom) {
			return true;
		}
		return false;
	}

	/**
	 * @return the modifyRoom
	 */
	public Boolean getModifyRoom()
	{
		return modifyRoom;
	}

	/**
	 * @param modifyRoom the modifyRoom to set
	 */
	public void setModifyRoom(Boolean modifyRoom)
	{
		this.modifyRoom = modifyRoom;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deleteChatPermission == null) ? 0 : deleteChatPermission.hashCode());
		result = prime * result + ((giveRolePermission == null) ? 0 : giveRolePermission.hashCode());
		result = prime * result + ((invitePermission == null) ? 0 : invitePermission.hashCode());
		result = prime * result + ((modifyRoom == null) ? 0 : modifyRoom.hashCode());
		result = prime * result + ((removeUserPermission == null) ? 0 : removeUserPermission.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + ((roomTypePermission == null) ? 0 : roomTypePermission.hashCode());
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
		Role other = (Role) obj;
		if (deleteChatPermission == null)
		{
			if (other.deleteChatPermission != null)
				return false;
		} else if (!deleteChatPermission.equals(other.deleteChatPermission))
			return false;
		if (giveRolePermission == null)
		{
			if (other.giveRolePermission != null)
				return false;
		} else if (!giveRolePermission.equals(other.giveRolePermission))
			return false;
		if (invitePermission == null)
		{
			if (other.invitePermission != null)
				return false;
		} else if (!invitePermission.equals(other.invitePermission))
			return false;
		if (modifyRoom == null)
		{
			if (other.modifyRoom != null)
				return false;
		} else if (!modifyRoom.equals(other.modifyRoom))
			return false;
		if (removeUserPermission == null)
		{
			if (other.removeUserPermission != null)
				return false;
		} else if (!removeUserPermission.equals(other.removeUserPermission))
			return false;
		if (roleName == null)
		{
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		if (roomTypePermission == null)
		{
			if (other.roomTypePermission != null)
				return false;
		} else if (!roomTypePermission.equals(other.roomTypePermission))
			return false;
		return true;
	}
	
	
}
