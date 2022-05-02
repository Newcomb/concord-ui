package modelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.JVMServer;
import model.Moderator;
import model.RoomList;
import model.User;
import model.UserList;



class JVMServerXMLTest
{
	JVMServer serv;
	User bob;
	UserList ul;
	RoomList rl;
	User janice;
	User denise;
	Registry registry;
	
	@BeforeEach
	void setUp() throws Exception
	{
		ul = new UserList();
		rl = new RoomList();
	
		
		try
		{
			serv = JVMServer.startServer();
			registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Concord", serv);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Here");
		}
		
		bob = new User("bob", "Bob Allen", null, false, "I am a freelance encoder!", "ILoveDogs");
		janice = new User("janice", "Janice Nelson", null, true, "I am a freelance decoder!", "CodingRocks");
		denise = new  User("denise", "denise Nelson", null, true, "I am a freelance decoder!", "DeniseRocks");
		bob.addBlocked(2);
		
		
		ul.addUser(bob);
		ul.addUser(janice);
		ul.addUser(denise);
		
		ul.getUser(0).createDirectMessage(1, rl, ul);
		ul.getUser(1).addBlocked(2);
		ul.getUser(1).addBlocked(0);
		
		
		rl.addRoom(bob.createRoom("This room is for data science majors!", "GO DS!", true), bob);
		rl.getRoom(0).addUser(1);
		
		rl.getRoom(0).addChatLog("Gaming", 0, false);
		rl.getRoom(0).addChatLog("Reading", 0, false);
		rl.getRoom(0).addChat(0, 0, "Hello");
		rl.getRoom(0).addChat(1,0,"bye");
		rl.getRoom(0).addChat(1, 0, "He");
		
		rl.addRoom(janice.createRoom("New Room", "Janice likes this room", false), janice);
		rl.getRoom(1).addChatLog("JANICE", 1, false);
		rl.getRoom(1).addChat(0, 1, "Janice is great");
		rl.getRoom(1).deleteChat(1, 0, 0);
		rl.getRoom(1).addUser(0);
		rl.getRoom(1).giveRole(1, 0, new Moderator());
		
		ul.getUser(0).createDirectMessage(1, rl, ul);
		
		
		
		serv.setRl(rl);
		serv.setUl(ul);

	}

	@AfterEach
	void tearDown() throws Exception
	{
	}

	@Test
	void testXML() throws RemoteException
	{
		
			serv.storeDataDisk();
			JVMServer servCopy = JVMServer.readFromDisk();
			
			assertEquals(serv.equals(servCopy), true);
			
		
	}

}
