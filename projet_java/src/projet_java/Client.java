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
		
		PrintStream     fluxSortieSocket2;
		BufferedReader  fluxEntreeSocket2;
		Socket sockCom2;
		
		try {
			sockCom = new Socket("localhost",13214);
			fluxSortieSocket = new PrintStream(sockCom.getOutputStream());
			fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
			
			
			System.out.println("Connect !");
			Integer menuIncorrect = 1;
			while (menuIncorrect == 1)
			{
				
				System.out.println("Annuaire partage");
				System.out.println("------------------------------------------------------------");
				System.out.println("MENU :");
				System.out.println("Tapez 0 pour quitter");
				System.out.println("Tapez 1 pour vous connecter si vous avez deja  un compte");
				System.out.println("Tapez 2 pour vous inscrire");
				System.out.println("------------------------------------------------------------");

				Integer choixMenu = LireIntClavier();
				switch (choixMenu) {
				case 0:
				{
					System.out.println("Vous etes deconnecte");
					menuIncorrect = 0;
					break;
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
						String[] tabretour = retour.split("#");
						Integer verificationUtil = Integer.parseInt(tabretour[1]);
						if(verificationUtil != -1)
						{
							user1.setNumeroFiche(Integer.parseInt(tabretour[1]));
							System.out.println("Connexion reussie");
							Integer menu2 = 1;
							while (menu2 == 1)
							{
								System.out.println("------------------------------------------------------------");
								System.out.println("MENU CONNECTE :");
								System.out.println("Tapez 0 pour vous deconnecter");
								System.out.println("Tapez 1 pour consulter vos informations dans l'annuaire");
								System.out.println("Tapez 2 pour modifier une information");
								System.out.println("------------------------------------------------------------");
								Integer choixMenu2 = LireIntClavier();
								switch (choixMenu2) 
								{
									case 0:
									{
										sockCom2 = new Socket("localhost",13215);
										fluxSortieSocket2 = new PrintStream(sockCom2.getOutputStream());
										fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom2.getInputStream()));
										System.out.println("Vous etes deconnecte");
										sockCom2.close();
										menu2 = 0;
										break;
									}
									case 1:
									{
										sockCom2 = new Socket("localhost",13215);
										fluxSortieSocket2 = new PrintStream(sockCom2.getOutputStream());
										fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom2.getInputStream()));
										System.out.println("Consultation d'information");
										// Mettre un menu pour demander qui l'on veut consulter (nom ou/et prenom, etc)
										String messageConsultInfoPerso = "CONSULTINFOPERSO#"+(user1.getNumeroFiche());
										System.out.println("message :"+messageConsultInfoPerso);
										fluxSortieSocket2.println(messageConsultInfoPerso);
										String retour2 = fluxEntreeSocket2.readLine();
										System.out.println("DEBUG :"+retour2);
										sockCom2.close();
										//executer la fonction dans utilisateur pour la consultation
										break;
									}
									case 2:
									{
										sockCom2 = new Socket("localhost",13215);
										fluxSortieSocket2 = new PrintStream(sockCom2.getOutputStream());
										fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom2.getInputStream()));
										System.out.println("Modification d'information");
										// Mettre un menu pour demander qui l'on veut modifier (nom ou/et prenom, etc)
										String modifInfoPerso = "MODIFINFOPERSO#"+(user1.getNumeroFiche());
										System.out.println("message :"+modifInfoPerso);
										fluxSortieSocket2.println(modifInfoPerso);
										String retour2 = fluxEntreeSocket2.readLine();
										System.out.println("DEBUG :"+retour2);
										sockCom2.close();
										//executer la fonction dans utilisateur pour la modification
										break;
									}
								}
								
							}
						} 
						else
						{
							menuIncorrect=1;
							System.out.println("Mauvais identifiants");
						}
					}
					catch(IOException ioe){
						System.out.println("Erreur de creation ou de connexion : "+ioe.getMessage());
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
					System.out.println("Veuillez entrer votre prenom");
					String prenom = LireStringClavier();
					System.out.println("Veuillez entrer votre adresse mail");
					String adresseMail = LireStringClavier();
					System.out.println("Veuillez entrer votre mot de passe");
					String motdepasse = LireStringClavier();
					System.out.println("Veuillez entrer votre numero de telephone");
					String numTel = LireStringClavier();
					System.out.println("Veuillez entrer le nom de votre formation");
					String formation = LireStringClavier();
					System.out.println("Veuillez entrer votre annee de votre diplome");
					int anneeDiplomation = LireIntClavier();
					String messageInscription = "CREATE".concat("#").concat(nom).concat("#").concat(prenom).concat("#").concat(adresseMail).concat("#").concat(motdepasse).concat("#").concat(numTel).concat("#").concat(formation).concat("#").concat(String.valueOf(anneeDiplomation));
					try {
						
						sockCom = new Socket("localhost",13214);
						fluxSortieSocket = new PrintStream(sockCom.getOutputStream());
						fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
						fluxSortieSocket.println(messageInscription);
						String retour = fluxEntreeSocket.readLine();
						System.out.println("DEBUG : "+retour);
						
						if(retour.indexOf("CREATIONOK") != -1)
						{
							System.out.println("Vous etes inscrit !");
							menuIncorrect=1;
						}else if(retour.equalsIgnoreCase("CREATIONREFUSEE"))
						{
							menuIncorrect=1;
							System.out.println("Email deja utilise, veuillez reessayer.");
						}
					}
					catch(IOException ioe){
						System.out.println("Erreur de creation ou de connexion : "+ioe.getMessage());
						return;
					}
					break;
				}
				default:
					System.out.println("Choix inconnu, veuillez reessayer.");
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
