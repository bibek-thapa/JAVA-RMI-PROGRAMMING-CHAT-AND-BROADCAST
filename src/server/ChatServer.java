package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import process.PresenceService;

public class ChatServer extends UnicastRemoteObject {

	
	protected ChatServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		String SERVER_NAME = "localhost";
		int PORT =1099;

		
		try {
			String name="Server";
			Registry registry=LocateRegistry.createRegistry(PORT);	
			PresenceService service= new PresenceServiceImpl();
			PresenceService stub = 
					(PresenceService) UnicastRemoteObject.exportObject(service,0);
			registry.rebind(name,stub);
			
			System.out.println("Server  Started");			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Server is not started");
			e.printStackTrace();
		}
		
		
		
	

}
}