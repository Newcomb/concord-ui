package modelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

public class GameLogTest
{
	
	JVMServer serv;
	Registry registry;
	JVMClient c;
	JVMClient c2;
	JVMClient c3;
	
	Room r;
	ChatLog cl;
	User bob;
	User janice;
	
	
	@BeforeEach
	void setUp() throws Exception
	{
//		try
//		{
//			serv = JVMServer.startServer();
//			registry = LocateRegistry.createRegistry(1099);
//			registry.rebind("Concord", serv);
//		} catch (RemoteException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		c = new JVMClient();
//		c.authenticate("bob", "ILoveDogs");
//		
//		c2 = new JVMClient();
//		c2.authenticate("janice", "CodingRocks");
//		
//		c3 = new JVMClient();
//		c3.authenticate("denise", "DeniseRocks");
	}
	
	
	@Test
	void test()
	{
		
	}

}
