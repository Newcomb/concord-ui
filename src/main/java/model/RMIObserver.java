package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIObserver extends Remote
{
	public void setRl(RoomList rl) throws RemoteException;
	public Boolean updateRl() throws RemoteException;
	public Boolean updateU() throws RemoteException;
}
