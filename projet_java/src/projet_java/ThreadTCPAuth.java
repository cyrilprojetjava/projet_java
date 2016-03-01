//#####################################################################################
// Class Thread qui permet la connexion de plusieurs utilisateurs sur le serveur athentification										 
//#####################################################################################

package projet_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadTCPAuth extends Thread{

	private Socket sockService;
	private GestionProtoAuth gpauth;

	
//#####################################################################################
// Constructeur qui associe un socket de service et la classe GESTIOPROTO du serveur authentification a chaque client										 
//#####################################################################################
public ThreadTCPAuth(Socket sockService, GestionProtoAuth gpauth) {
		super();
		this.sockService = sockService;
		this.gpauth = gpauth;
	}



//#####################################################################################
//Fonction qui lance la classe GestionProto pour annalyser le message du client										 
//#####################################################################################
public void run() {
		
		BufferedReader reader;
		try {
			reader = new BufferedReader (new InputStreamReader(sockService.getInputStream()));
			PrintStream pStream = new PrintStream(sockService.getOutputStream());
			// On lit les informations que le client nous envoit tant qu'il est différent de "null"
			while(true){
				String requete = reader.readLine();
				if (requete == null){
					break;
				}
				// On traite le message du client dans la fonction analyser traiter de la classe gestion proto auth
				String reponse = gpauth.analyserTraiter(requete);
				// On envoit la réponse de la fonction au client
				pStream.println(reponse); 
			}
			sockService.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
