package process;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class ChatServerMain implements PresenceService {
	
	
//	Vector<RegistrationInfo> clients;
	Map<String,RegistrationInfo> clients;
	
	public ChatServerMain() 
	{
//		clients = new Vector<RegistrationInfo>();
		clients = new LinkedHashMap<String,RegistrationInfo>();
	}

	public static void main(String[] args) {
	
		int PORT =1099;
				
		try {
			String name="Server";
			Registry registry=LocateRegistry.createRegistry(PORT);	
			PresenceService service= new ChatServerMain();
			PresenceService stub = 
					(PresenceService) UnicastRemoteObject.exportObject(service,0);
			registry.rebind(name,stub);
			
			System.out.println("Server  Started");		
			
			Object o = new Object();
			synchronized (o) {
				o.wait();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Server is not started");
			
			e.printStackTrace();
		}
		
		
		
	}

	
	

		
		// TODO Auto-generated constructor stub
	

	public synchronized boolean register(RegistrationInfo reg) throws RemoteException {
		// TODO Auto-generated method stub
		
		clients.put(reg.getUserName(), reg);
		return true;
		
		
	}

	public synchronized boolean updateRegistrationInfo(RegistrationInfo reg) throws RemoteException {
		System.out.println("Inside the Update Registration Info");
		if(clients.get(reg.getUserName()) != null) {
			this.clients.put(reg.getUserName(), reg);
			return true;
		} else {
			return false;
		}
	}

	public synchronized void unregister(String userName) throws RemoteException {
		
		clients.remove(userName);
		
		// TODO Auto-generated method stub
		
	}

	public RegistrationInfo lookup(String name) throws RemoteException {
		
		System.out.println("In lookup");
		RegistrationInfo temp;
		temp = clients.get(name);	
		return temp;
		
	}

	public Vector<RegistrationInfo> listRegisteredUsers() throws RemoteException {
		System.out.println("in listRegisteredUsers");
		Set<String> keys = clients.keySet();
		Vector<RegistrationInfo> retVal = new Vector<RegistrationInfo>();
		for(String key: keys) {
			retVal.add(this.clients.get(key));
		}
		return retVal;
	}

	public void broadcastMessage(String message) throws RemoteException {
		
			
							
	}



}
