package projet_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadTCP extends Thread{

	private Socket sockService;
	private GestionProtoAuth gpa;
	private GestionProtoAnnuaire gpannuaire;
	
	
public ThreadTCP(Socket sockService, GestionProtoAuth gpa) {
		super();
		this.sockService = sockService;
		this.gpa = gpa;
	}

public ThreadTCP(Socket sockService, GestionProtoAnnuaire gpannuaire) {
	super();
	this.sockService = sockService;
	this.gpannuaire = gpannuaire;
}


public void run() {
		
		// Instancie un BufferedReader travaillant sur un InputStreamReader lié à  // l’input stream de la socket
		BufferedReader reader;
		try {
			reader = new BufferedReader (new InputStreamReader(sockService.getInputStream()));
			PrintStream pStream = new PrintStream(sockService.getOutputStream());
			while(true){
				// Lit une ligne de caractères depuix le flux, et donc la reçoit du client
				String requete = reader.readLine();
				System.out.println(requete);
				if (requete == null){
					break;
				}
				String reponse = gpa.analyserTraiter(requete);
				
				// Instancie un PrintStream travaillant sur l’output stream de la socket PrintStream pStream = new PrintStream(sockService.getOutputStream());
				// écrit une ligne de caractères sur le flux, et donc l’envoie au client
				pStream.println(reponse); 
			}
			sockService.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
