package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Vector;

import process.PresenceService;
import process.RegistrationInfo;

public class ChatClient {
	
	static ServerSocket serverSocket =null;
	ServerThread serverThread;
	static RegistrationInfo info;
	static PresenceService present;
	static PresenceService present1;
	static RegistrationInfo info1;
	public ChatClient() {
		
//		try {
//			present1 = (PresenceService)Naming.lookup("//" + "localhost" + "/Server");
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (RemoteException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (NotBoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
			try {
				serverSocket = new ServerSocket(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 System.out.println("Error: Couldn't allocate local socket endpoint.");
		            System.exit(-1);
				e.printStackTrace();
			}
			
			Thread t1 = new Thread(new ServerThread());
			t1.start();
			
			
	
	}

	class ServerThread implements Runnable
	{
		
		boolean done = false;
		
		
		public void run() {
			// TODO Auto-generated method stub
		while(!done) {
			Socket client_socket = null;			
			try {
				client_socket = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Inside the thread");
				e.printStackTrace();
			}
			
			 byte buf[] = new byte[2048];
             try {
                 int cnt = client_socket.getInputStream().read(buf,0,2048);
                 String msg = new String(buf,0,cnt);

                 // We'll refresh the prompt, lest the chimp on the console
                 // get's confused.
                 System.out.println(msg);
                 client_socket.close();

             } catch (IOException ie) {
            	 
            	 System.out.println("Inside the thread 1");
             }
		}
             try {
				ChatClient.serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 System.out.println("Inside the thread 2");
				e.printStackTrace();
			}
		
			
		}
		
		
		
		
		
	}
	
	public  void sendMessage(String name,String msg) 
	{
	
		try {
				info1=present.lookup(name);	
				if(info1!=null && info1.getStatus()!=false) {
	                // open a socket connection remote user's client and send message.
	                Socket skt = new Socket(info1.getHost(),info1.getPort());
	                String completeMsg = "Message from " + info.getUserName() + ": " + msg + "\n";
	                skt.getOutputStream().write(completeMsg.getBytes());
	                skt.close();
				}
				
				else 
				{
					System.out.println("The client is not active. Try messaging later");
				}
              
            } catch (Exception e) {
            	
            	e.printStackTrace();
                
                
            }
     
		
		
		
	}
	
	
	
	public static void main(String[] args) {

		ChatClient myClient = new ChatClient();
		String name = "Server";
		Registry registry;
		try {

			Vector<RegistrationInfo> clients;
			Scanner s = new Scanner(System.in);
			System.out.println("Enter Your name and press Enter:");
			String name_1 = s.nextLine().trim().toLowerCase();

			registry = LocateRegistry.getRegistry("localhost");
			
			present = (PresenceService) registry.lookup(name);
			 info = new RegistrationInfo(name_1, "localhost", serverSocket.getLocalPort(), true);
			
			 
			if(present.lookup(name_1)==null) { 
			present.register(info);
			}
			else 
			{
				System.out.println("User with this name already in the system");
				System.out.println("Exiting...........");
				System.exit(0);
				
			}
			String input;
			
			
			System.out.println("Please enter one of the following commands:");
			System.out.println("friends");
			System.out.println("chat {username} {message}");
			System.out.println("broadcast {message}");
			System.out.println("busy");
			System.out.println("available");
			System.out.println("clear");
			System.out.println("exit");
			System.out.println("------------------------------------");
			
			boolean active =true;
			while(active){
				Scanner s_1=new Scanner(System.in);
				
				String name_2=s_1.nextLine();
				String[] nameArray = name_2.trim().split(" "); 
				
				
				String a = nameArray[0].toLowerCase();
				switch(a) 
				{
				case "friends":
					clients = present.listRegisteredUsers();
						
					for (RegistrationInfo client : clients) {
						String name_broad = client.getUserName();
					if(clients.size()==1) 
					{
						
						System.out.println("Currently , there are no other users available");
					}
					
					else {
						
						if(!info.getUserName().equals(name_broad)) 
						{
							String res = client.getStatus()==true ? "online":"offline";
						System.out.println(client.getUserName() + " " + res);
						
						}
					}	
					}
					
					break;
				case "broadcast":
					
					String msg1="";
					for(int i=1 ; i<nameArray.length;i++) 
					{
						msg1 = msg1 +" "+nameArray[i];
					}
					
					
						clients = present.listRegisteredUsers();
						
						for (RegistrationInfo client : clients) {
							
							String name_broad = client.getUserName();
							if(!info.getUserName().equals(name_broad)) 
							{
								myClient.sendMessage(client.getUserName(), msg1);
							}
						}
					
					
					
					
				break;
					
				case "chat":
				String msg="";
				for(int i=2 ; i<nameArray.length;i++) 
				{
					msg = msg +" "+nameArray[i];
				}
				
			
				info1=present.lookup(nameArray[1].toLowerCase());
				myClient.sendMessage(info1.getUserName(),msg);
					
					break;
					
				case "busy":
					
					boolean status = false;
					info.setStatus(status);
					present.updateRegistrationInfo(info);					
					
					break;
					
				case "available":
					
					boolean status_a = true;
					info.setStatus(status_a);
					present.updateRegistrationInfo(info);					
					
					break;
				
					
				case "exit":
						present.unregister(info.getUserName());
						System.out.println("You are succesfully logged out from the system");
						System.exit(0);
					
											
				}		
					
														
				}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unsuccessfull");
			e.printStackTrace();
		}

	}
}
