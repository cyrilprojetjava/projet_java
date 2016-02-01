package projet_java;

import java.util.Scanner;
import java.net.Socket;
import java.net.UnknownHostException;
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
		/*
		PrintStream     fluxSortieSocket2;
		BufferedReader  fluxEntreeSocket2;
		Socket sockCom2;*/
		try {
			sockCom = new Socket("localhost",13214);
			fluxSortieSocket = new PrintStream(sockCom.getOutputStream());
			fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
			
			
			System.out.println("Connect !");
			Integer menuIncorrect = 1;
			while (menuIncorrect == 1)
			{
				
				System.out.println("Annuaire partagé");
				System.out.println("------------------------------------------------------------");
				System.out.println("MENU");
				System.out.println("Tapez 0 pour quitter");
				System.out.println("Tapez 1 pour vous connecter si vous avez déjà un compte");
				System.out.println("Tapez 2 pour vous inscrire");
				System.out.println("------------------------------------------------------------");

				Integer choixMenu = LireIntClavier();
				switch (choixMenu) {
				case 0:
				{
					System.out.println("Deconnexion de l'annuaire partagé");
					return;
				}
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
						fluxSortieSocket.println(messageConnect);
						String retour = fluxEntreeSocket.readLine();
						System.out.println("DEBUG :"+retour);
						if(retour.indexOf("CONNEXIONOK") != -1)
						{
							System.out.println("Connexion réussi");
							System.out.println("Fonctionnalités recherches annuaire");
							/*
							System.out.println("Tapez 1 pour consulter vos informations dans l'annuaire");
							System.out.println("Tapez 2 pour modifier une information");
							Integer choixMenu2 = LireIntClavier();
							switch (choixMenu2) {
							case 1:
							{
								sockCom2 = new Socket("localhost",13215);
								fluxSortieSocket2 = new PrintStream(sockCom.getOutputStream());
								fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
								String messageConsultInfoPerso = "CONSULTINFOPERSO".concat("#")+(user1.getNumeroFiche());
								fluxSortieSocket.println(messageConsultInfoPerso);
								String retour2 = fluxEntreeSocket.readLine();
								System.out.println("DEBUG :"+retour2);
								sockCom2.close();
							}
							case 2:
							{
								sockCom2 = new Socket("localhost",13215);
								fluxSortieSocket2 = new PrintStream(sockCom.getOutputStream());
								fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
								String modifInfoPerso = "MODIFINFOPERSO".concat("#")+(user1.getNumeroFiche());
							}
							}
							*/
							
							
						}else if(retour.equalsIgnoreCase("CONNEXIONREFUSEE"))
						{
							menuIncorrect=1;
							System.out.println("Mauvais identifiants");
						}
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
						System.out.println("DEBUG :"+retour);
						if(retour.indexOf("CREATIONOK") != -1)
						{
							menuIncorrect=1;
						}else if(retour.equalsIgnoreCase("CREATIONREFUSEE"))
						{
							menuIncorrect=1;
							System.out.println("Email d�j� utilis�, veuillez r�essayer.");
						}
					}
					catch(IOException ioe){
						System.out.println("Erreur de cr�ation ou de connexion : "+ioe.getMessage());
						return;
					}
					break;
				}
				default:
					System.out.println("Choix inconnu, veuillez r�essayer.");
					menuIncorrect = 1;
					break;
				}
			}
			sockCom.close();


		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
