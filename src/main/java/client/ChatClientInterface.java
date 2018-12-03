package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientInterface extends Remote{

		public void sendMessage(String message) throws RemoteException;	
}
