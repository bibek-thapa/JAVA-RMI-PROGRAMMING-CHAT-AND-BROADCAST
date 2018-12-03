package server;

import java.rmi.RemoteException;

import java.util.Vector;

import process.PresenceService;
import process.RegistrationInfo;

public class PresenceServiceImpl  implements PresenceService  {

	/**
	 * 
	 */
	

	Vector<RegistrationInfo> clients;
	
	protected PresenceServiceImpl() throws RemoteException {
		super();
		clients = new Vector<RegistrationInfo>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized boolean register(RegistrationInfo reg) throws RemoteException {
		// TODO Auto-generated method stub
		
		clients.addElement(reg);		
		return true;
	}

	@Override
	public synchronized boolean updateRegistrationInfo(RegistrationInfo reg) throws RemoteException {
		
		RegistrationInfo info=new RegistrationInfo();
		info.setUserName(reg.getUserName());
		info.setStatus(reg.getStatus());
		info.setPort(reg.getPort());
		info.setHost(reg.getHost());
		
		
		// TODO Auto-generated method stub
		
		return true;
	}

	@Override
	public synchronized void unregister(String userName) throws RemoteException {
		
		clients.removeElement(lookup(userName));
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public RegistrationInfo lookup(String name) throws RemoteException {
		// TODO Auto-generated method stub
		
		RegistrationInfo temp=new RegistrationInfo();
		for(RegistrationInfo client : clients) 
		{
			if(name == client.getUserName()) 
			{
				temp = client;
			}
			else 
			{
				temp=null;
			}
		}
		return temp;
	}

	@Override
	public Vector<RegistrationInfo> listRegisteredUsers() throws RemoteException {
		return clients;
	}

	
	
}

