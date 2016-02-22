package projet_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadTCPCom extends Thread{

	private Socket sockService;
	private GestionProtoCom gpcom;

	
	
public ThreadTCPCom(Socket sockService, GestionProtoCom gpcom) {
		super();
		this.sockService = sockService;
		this.gpcom = gpcom;
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
				String reponse = gpcom.analyserTraiter(requete);
				
				pStream.println(reponse); 
			}
			sockService.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
