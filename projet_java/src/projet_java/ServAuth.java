package projet_java;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServAuth {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket sockEcoute;
		Socket sockService;
		try{
			sockEcoute = new ServerSocket(13214);
		}
		catch(IOException ioe){
			System.out.println("Erreur de création ou de connexion : "+ioe.getMessage());
			return;
		}
		while(true){
			try{
				sockService = sockEcoute.accept();
			   }
			catch(IOException ioe){
				System.out.println("Erreur d'accept : "+ioe.getMessage());
				break;
			}
			try{
				BufferedReader reader = new BufferedReader(new InputStreamReader(sockService.getInputStream()));
				String recu = reader.readLine();
				System.out.println(recu);
			}
			catch(IOException ioe){
				System.out.println("Erreur de lecture : "+ioe.getMessage());
			}
			try{
				PrintStream pStream = new PrintStream(sockService.getOutputStream());
				pStream.println("OK");
			   }
			catch(IOException ioe){
				System.out.println("Erreur d'écriture : "+ioe.getMessage());
				break;
			}
		}

	}

}
