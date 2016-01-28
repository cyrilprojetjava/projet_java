package projet_java;

import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;



public class Client extends Object {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Utilisateur user1 = new Utilisateur();
		PrintStream     fluxSortieSocket;
		BufferedReader  fluxEntreeSocket;
		Socket sockCom;
		
		System.out.println("Connect !");
		System.out.println("Annuaire partagé");
		System.out.println("Tapez 1 pour vous connecter si vous avez déjà un compte");
		System.out.println("Tapez 2 pour vous inscrire");
		Integer menuIncorrect = 1;
		while (menuIncorrect == 1)
		{
			Integer choixMenu = LireIntClavier();
			switch (choixMenu) {
			case 1:
			{
				menuIncorrect = 0;
				System.out.println("Connexion");
				System.out.println("Veuillez entrer votre adresse mail");
				String adresseMail = LireStringClavier();
				System.out.println("Veuillez entrer votre mot de passe");
				String motdepasse = LireStringClavier();
				String messageConnect = "CONNECT".concat("#").concat(adresseMail).concat("#").concat(motdepasse);
				try {
					
					sockCom = new Socket("localhost",13214);
					fluxSortieSocket = new PrintStream(sockCom.getOutputStream());
					fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
					fluxSortieSocket.println(messageConnect);
					String retour = fluxEntreeSocket.readLine();
					System.out.println(retour);

				}
				catch(IOException ioe){
					System.out.println("Erreur de création ou de connexion : "+ioe.getMessage());
					return;
				}
				break;
				}
			case 2:
			{
				menuIncorrect = 0;
				System.out.println("Inscription");
				System.out.println("Veuillez entrer votre nom");
				String nom = LireStringClavier();
				System.out.println("Veuillez entrer votre prénom");
				String prenom = LireStringClavier();
				System.out.println("Veuillez entrer votre adresse mail");
				String adresseMail = LireStringClavier();
				System.out.println("Veuillez entrer votre mot de passe");
				String motdepasse = LireStringClavier();
				System.out.println("Veuillez entrer votre numero de telephone");
				String numTel = LireStringClavier();
				System.out.println("Veuillez entrer le nom de votre formation");
				String formation = LireStringClavier();
				System.out.println("Veuillez entrer votre année de diplomation");
				int anneeDiplomation = LireIntClavier();
				String messageInscription = "CREATE".concat("#").concat(nom).concat("#").concat(prenom).concat("#").concat(adresseMail).concat("#").concat(motdepasse).concat("#").concat(numTel).concat("#").concat(formation).concat("#").concat(String.valueOf(anneeDiplomation));
				try {
					
					sockCom = new Socket("localhost",13214);
					fluxSortieSocket = new PrintStream(sockCom.getOutputStream());
					fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
					fluxSortieSocket.println(messageInscription);
					String retour = fluxEntreeSocket.readLine();
					System.out.println(retour);
					if(retour.indexOf("CREATIONOK") != -1)
					{
						menuIncorrect=1;
						System.out.println("Tapez 1 pour vous connecter si vous avez déjà un compte");
						System.out.println("Tapez 2 pour vous inscrire");
					}
				}
				catch(IOException ioe){
					System.out.println("Erreur de création ou de connexion : "+ioe.getMessage());
					return;
				}
				break;
			}
			default:
				System.out.println("Choix inconnu, veuillez réessayer.");
				menuIncorrect = 1;
				break;
			}
		}
	}

	private static String LireStringClavier() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		return str;
	}
	
	private static Integer LireIntClavier() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Integer num = sc.nextInt();
		return num;
	}

}
