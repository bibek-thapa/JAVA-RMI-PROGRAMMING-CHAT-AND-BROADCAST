package client;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Vector;

import process.PresenceService;
import process.RegistrationInfo;

public class ChatClient {

	public static void main(String[] args) {

		String name = "Server";
		Registry registry;
		try {

			Vector<RegistrationInfo> clients=new Vector<RegistrationInfo>();
			Scanner s = new Scanner(System.in);
			System.out.println("Enter Your name and press Enter:");
			String name_1 = s.nextLine().trim();

			registry = LocateRegistry.getRegistry("localhost");
			PresenceService present = (PresenceService) registry.lookup(name);
			
			//Some way to get the client info 
			RegistrationInfo info = new RegistrationInfo(name_1, "localhost", Integer.parseInt(args[0]), true);
			
			present.register(info);
			String input;
			
			
			System.out.println("Please enter one of the following commands:");
			System.out.println("friends");
			System.out.println("talk {username} {message}");
			System.out.println("broadcast {message}");
			System.out.println("busy");
			System.out.println("available");
			System.out.println("exit");
			System.out.println("------------------------------------");
			
			boolean active =true;
			while(active){
				Scanner s_1=new Scanner(System.in);
				String name_2=s_1.nextLine();
				String[] nameArray = name_2.trim().split(" "); 
				
				switch(nameArray[0]) 
				{
				case "friends":
					clients = present.listRegisteredUsers();
					for (RegistrationInfo client : clients) {
						if(client.getStatus()==true) {
						System.out.println(client.getUserName() + " " + "active");
						active=true;
						}
					}
					
					break;
				case "broadcast":
					String message_edit="";
					clients=present.listRegisteredUsers();
					for(int i=1;i<nameArray.length;i++) 
					{
						message_edit=message_edit+""+nameArray[i];
					}
					
					for(RegistrationInfo client : clients) 
					{
						
						
					}
			
					
					
					
					break;
					
				case "busy":
					
					clients.removeElement(info.getUserName());
										
				break;
				case "exit":
						
//					
						active=false;
						System.out.println("You are succesfully logged out from the system");
						break;
											
				}		
					
														
				}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unsuccessfull");
			e.printStackTrace();
		}

	}
}
