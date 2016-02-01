package projet_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadTCPAuth extends Thread{

	private Socket sockService;
	private GestionProtoAuth gpauth;

	
	
public ThreadTCPAuth(Socket sockService, GestionProtoAuth gpauth) {
		super();
		this.sockService = sockService;
		this.gpauth = gpauth;
	}




public void run() {
		
		// Instancie un BufferedReader travaillant sur un InputStreamReader lieÌ� aÌ€  // lâ€™input stream de la socket
		BufferedReader reader;
		try {
			reader = new BufferedReader (new InputStreamReader(sockService.getInputStream()));
			PrintStream pStream = new PrintStream(sockService.getOutputStream());
			while(true){
				// Lit une ligne de caracteÌ€res depuix le flux, et donc la recÌ§oit du client
				String requete = reader.readLine();
				System.out.println(requete);
				if (requete == null){
					break;
				}
				String reponse = gpauth.analyserTraiter(requete);
				
				// Instancie un PrintStream travaillant sur lâ€™output stream de la socket PrintStream pStream = new PrintStream(sockService.getOutputStream());
				// eÌ�crit une ligne de caracteÌ€res sur le flux, et donc lâ€™envoie au client
				pStream.println(reponse); 
			}
			sockService.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
