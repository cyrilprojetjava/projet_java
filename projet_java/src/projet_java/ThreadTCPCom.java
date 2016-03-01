//#####################################################################################
// Class Thread qui permet la connexion de plusieurs utilisateurs sur le serveur de communication										 
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
import java.io.PrintStream;
import java.net.Socket;

public class ThreadTCPCom extends Thread
{

	private Socket sockService;
	private GestionProtoCom gpcom;

	
//#####################################################################################
// Constructeur qui associe un socket de service et la classe GESTIOPROTO du serveur de communication a chaque client										 
//#####################################################################################
	public ThreadTCPCom(Socket sockService, GestionProtoCom gpcom)
	{
		super();
		this.sockService = sockService;
		this.gpcom = gpcom;
	}



	//#####################################################################################
	//Fonction qui lance la classe GestionProto pour annalyser le message du client										 
	//#####################################################################################
	public void run()
	{
		
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader (new InputStreamReader(sockService.getInputStream()));
			PrintStream pStream = new PrintStream(sockService.getOutputStream());
			while(true)
			{
				String requete = reader.readLine();
				// On lit les informations que le client nous envoi tant qu'il est différent de "null"
				if (requete == null)
				{
					break;
				}
				// On traite le message du client dans la fonction analyser traiter de la classe gestion proto Comm
				String reponse = gpcom.analyserTraiter(requete);
				// On envoi la réponse de la fonction au client
				pStream.println(reponse); 
			}
			sockService.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
