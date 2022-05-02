package model;

public class Moderator extends Role
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6448643879340797087L;
	public final String roleName = "Moderator";
	public final Boolean deleteChatPermission = true;
	public final Boolean removeUserPermission = false;
	public final Boolean roomTypePermission = false;
	public final Boolean giveRolePermission = false;
	public final Boolean invitePermission = true;
	public final Boolean modifyRoom = false;
	
	public Moderator()
	{
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
		return modifyRoom;
	}

	/**
	 * @return the modifyRoom
	 */
	public Boolean getModifyRoom()
	{
		return modifyRoom;
	}


	
	
}
