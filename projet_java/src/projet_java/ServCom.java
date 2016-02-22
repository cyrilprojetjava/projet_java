package projet_java;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServCom {

	ServerSocket sockEcoute;  
	public ServCom(){

		try { 
			sockEcoute = new ServerSocket(15000); 
		} 
		catch(IOException ioe) { 
			System.out.println("Erreur de creation du server socket: " + ioe.getMessage()); 
			return; 
		}
	}

	public void Service(){
		Socket sockService; 

		GestionProtoCom gpcom = new GestionProtoCom();  
		while(true) {

			try {
				sockService = sockEcoute.accept(); 
				ThreadTCPCom th = new ThreadTCPCom(sockService,gpcom);
				th.start();
			}
			catch(IOException ioe) 
			{
				System.out.println("Erreur d ecriture : " + ioe.getMessage());
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServCom srv = new ServCom();
		srv.Service();
	}
}




