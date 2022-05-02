package model;

public class Noob extends Role
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9113469669343513619L;
	public final String roleName = "Noob";
	public final Boolean deleteChatPermission = false;
	public final Boolean removeUserPermission = false;
	public final Boolean roomTypePermission = false;
	public final Boolean giveRolePermission = false;
	public final Boolean invitePermission = true;
	public final Boolean modifyRoomPermission = false;
	

	public Noob()
	{
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
	 * @return the modifyRoomPermission
	 */
	public Boolean getModifyRoomPermission()
	{
		return modifyRoomPermission;
	}

	
	
}
