package model;

public class Admin extends Role
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4538945378831131307L;
	public final String roleName = "Admin";
	public final Boolean deleteChatPermission = true;
	public final Boolean removeUserPermission = true;
	public final Boolean roomTypePermission = true;
	public final Boolean giveRolePermission = true;
	public final Boolean invitePermission = true;
	public final Boolean modifyRoom = true;
	 
	public Admin()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName()
	{
		return roleName;
	}

	/**
	 * @return the deleteChatPermission
	 */
	public Boolean getDeleteChatPermission()
	{
		return deleteChatPermission;
	}

	/**
	 * @return the removeUserPermission
	 */
	public Boolean getRemoveUserPermission()
	{
		return removeUserPermission;
	}

	/**
	 * @return the roomTypePermission
	 */
	public Boolean getRoomTypePermission()
	{
		return roomTypePermission;
	}

	/**
	 * @return the giveRolePermission
	 */
	public Boolean getGiveRolePermission()
	{
		return giveRolePermission;
	}

	/**
	 * @return the invitePermission
	 */
	public Boolean getInvitePermission()
	{
		return invitePermission;
	}

	/**
	 * @return the invitePermission
	 */
	public Boolean getModifyRoomPermission()
	{
		return this.modifyRoom;
	}

	/**
	 * @return the modifyRoom
	 */
	public Boolean getModifyRoom()
	{
		return modifyRoom;
	}


}
