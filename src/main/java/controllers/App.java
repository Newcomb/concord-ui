package controllers;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Application;
import javafx.stage.Stage;
import model.JVMClient;
import model.JVMServer;

public class App extends Application {
    @Override
    public void start(Stage stage) {
    	JVMServer serv;
    	Registry registry;
    	
		try
		{
			serv = JVMServer.startServer();
			registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Concord", serv);
		} catch (RemoteException e)
		{
			System.out.println("Server already exists");
		}
        
        // ConcordClient client = new ConcordClient(server);
        JVMClient client;
		try
		{
			client = new JVMClient();
			ViewFactory viewFactory = new ViewFactory(client);

	        viewFactory.showLoginWindow();
		} catch (RemoteException e)
		{
			System.out.println("Client failed to start!");
		}

        // ViewFactory viewFactory = new ViewFactory(client);

        // viewFactory.showLoginWindow();

    }

    public static void main(String[] args) {
        launch();
    }
}