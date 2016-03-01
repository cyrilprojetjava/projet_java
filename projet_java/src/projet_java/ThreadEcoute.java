//#####################################################################################
// Class Thread qui permet d'écouter en permanence sur le client pour recevoir des messages instentanés 										 
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
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadEcoute extends Thread
{

	private ServerSocket sockEcoute;

	public ThreadEcoute(ServerSocket sockEcoute) 
	{
		super();
		this.sockEcoute = sockEcoute;
	}
	
public void run() 
	{
		while(true)
		{
			try
			{
				// Dés qu'une information arrive on lance un thread qui va l'afficher
				Socket sockService = sockEcoute.accept(); 
				ThreadService th   = new ThreadService(sockService);
				th.start();
			}
		
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
	


