//#####################################################################################
// Class Thread qui permet d'afficher les messages reçu par d'autre utilisateur										 
//#####################################################################################

package projet_java;

import com.mysql.jdbc.Blob;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadService extends Thread 
{

	private Socket sockService;

	public ThreadService(Socket sockService) 
	{
		super();
		this.sockService = sockService;
	}
	
	public void run() 
		{
			try 
			{
				BufferedReader  fluxEntreeSocket;
				fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockService.getInputStream()));
				String message;
				while(true)
				{
					message = fluxEntreeSocket.readLine();
					if (message == null)
					{
						break;
					}
					// On affiche le message que l'on reçoi des autres utilisateurs
					System.out.println(message);
				}
			}
		
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
	


