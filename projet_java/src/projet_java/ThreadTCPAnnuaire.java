package projet_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadTCPAnnuaire extends Thread{

	private Socket sockService;
	private GestionProtoAnnuaire gpannuaire;

	
	
public ThreadTCPAnnuaire(Socket sockService, GestionProtoAnnuaire gpannuaire) {
		super();
		this.sockService = sockService;
		this.gpannuaire = gpannuaire;
	}




public void run() {
		
		BufferedReader reader;
		try {
			reader = new BufferedReader (new InputStreamReader(sockService.getInputStream()));
			PrintStream pStream = new PrintStream(sockService.getOutputStream());
			while(true){
				String requete = reader.readLine();
				//System.out.println(requete);
				if (requete == null){
					break;
				}
				String reponse = gpannuaire.analyserTraiter(requete);
				
				pStream.println(reponse);
			}
			sockService.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
