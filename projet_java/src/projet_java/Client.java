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
				System.out.println("Tapez 1 pour vous connecter si vous avez deja� un compte");
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
						System.out.println(retour);
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
										String messageConsultInfoPerso = "CONSULTINFOPERSO#"+(user1.getNumeroFiche());
										//System.out.println("message :"+messageConsultInfoPerso);
										fluxSortieSocket2.println(messageConsultInfoPerso);
										String retour2 = fluxEntreeSocket2.readLine();
										System.out.println(retour2);
										sockCom2.close();
										break;
									}
									case 2:
									{
										sockCom2 = new Socket("localhost",13215);
										fluxSortieSocket2 = new PrintStream(sockCom2.getOutputStream());
										fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom2.getInputStream()));
										System.out.println("------------------------------------------------------------");
										System.out.println("Modification d'information");
										System.out.println("Tapez 1 pour modifier votre nom");
										System.out.println("Tapez 2 pour modifier votre prenom");
										System.out.println("Tapez 3 pour modifier votre telephone");
										System.out.println("Tapez 4 pour modifier votre formation");
										System.out.println("Tapez 5 pour modifier votre année obtention diplome");
										System.out.println("Tapez 6 pour modifier votre identifiant mail");
										System.out.println("Tapez 7 pour modifier votre mot de passe");
										System.out.println("------------------------------------------------------------");
										Integer choixMenu3 = LireIntClavier();
										switch (choixMenu3) 
										{
												case 1:
												{
													
													System.out.println("Veuillez entrer votre nouveau nom");
													String newNom = LireStringClavier();
													String modifInfoPersoNom = "MODIFINFOPERSONOM#"+(user1.getNumeroFiche());
													String newInfoPersoNom = modifInfoPersoNom.concat("#").concat(newNom);
													//System.out.println("message :"+newInfoPersoNom);
													fluxSortieSocket2.println(newInfoPersoNom);
													String retour3 = fluxEntreeSocket2.readLine();
													System.out.println(retour3);
													break;
												}
												
												case 2:
												{
													
													System.out.println("Veuillez entrer votre nouveau prenom");
													String newPrenom = LireStringClavier();
													String modifInfoPersoPrenom = "MODIFINFOPERSOPRENOM#"+(user1.getNumeroFiche());
													String newInfoPersoPrenom = modifInfoPersoPrenom.concat("#").concat(newPrenom);
													//System.out.println("message :"+newInfoPersoPrenom);
													fluxSortieSocket2.println(newInfoPersoPrenom);
													String retour4 = fluxEntreeSocket2.readLine();
													System.out.println(retour4);
													break;
												}
												
												case 3:
												{
													
													System.out.println("Veuillez entrer votre nouveau numero de telephone");
													String newTel = LireStringClavier();
													String modifInfoPersoTel = "MODIFINFOPERSOTEL#"+(user1.getNumeroFiche());
													String newInfoPersoTel = modifInfoPersoTel.concat("#").concat(newTel);
													//System.out.println("message :"+newInfoPersoTel);
													fluxSortieSocket2.println(newInfoPersoTel);
													String retour5 = fluxEntreeSocket2.readLine();
													System.out.println(retour5);
													break;
												}
												
												case 4:
												{
													
													System.out.println("Veuillez entrer votre nouvelle formation");
													String newFormation = LireStringClavier();
													String modifInfoPersoFormation = "MODIFINFOPERSOFORMATION#"+(user1.getNumeroFiche());
													String newInfoPersoFormation = modifInfoPersoFormation.concat("#").concat(newFormation);
													//System.out.println("message :"+newInfoPersoFormation);
													fluxSortieSocket2.println(newInfoPersoFormation);
													String retour6 = fluxEntreeSocket2.readLine();
													System.out.println(retour6);
													break;
												}
												
												case 5:
												{
													
													System.out.println("Veuillez entrer votre nouvelle annee d'obtention de votre diplome");
													String newAnneeDiplome = LireStringClavier();
													String modifInfoPersoAnneeDiplome = "MODIFINFOPERSOANDIPLOME#"+(user1.getNumeroFiche());
													String newInfoPersoAnneeDiplome = modifInfoPersoAnneeDiplome.concat("#").concat(newAnneeDiplome);
													//System.out.println("message :"+newInfoPersoAnneeDiplome);
													fluxSortieSocket2.println(newInfoPersoAnneeDiplome);
													String retour7 = fluxEntreeSocket2.readLine();
													System.out.println(retour7);
													break;
												}
												case 6:
												{
													
													System.out.println("Veuillez entrer votre nouvelle adresse mail");
													String newAdresseMail = LireStringClavier();
													String modifInfoPersoAdresseMail = "MODIFINFOPERSOMAIL#"+(user1.getNumeroFiche());
													String newInfoPersoAdresseMail = modifInfoPersoAdresseMail.concat("#").concat(newAdresseMail);
													//System.out.println("message :"+newInfoPersoAdresseMail);
													fluxSortieSocket2.println(newInfoPersoAdresseMail);
													String retour8 = fluxEntreeSocket2.readLine();
													System.out.println(retour8);
													break;
												}
												
												case 7:
												{
													
													System.out.println("Veuillez entrer votre nouveau mot de passe");
													String newMotDePasse = LireStringClavier();
													String modifInfoPersoMotDePasse = "MODIFINFOPERSOMDP#"+(user1.getNumeroFiche());
													String newInfoPersoMotDePasse = modifInfoPersoMotDePasse.concat("#").concat(newMotDePasse);
													//System.out.println("message :"+newInfoPersoAdresseMail);
													fluxSortieSocket2.println(newInfoPersoMotDePasse);
													String retour9 = fluxEntreeSocket2.readLine();
													System.out.println(retour9);
													break;
												}
									
								}
								
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
							System.out.println("Pour modifier la visibilite de vos informations aller dans le menu Confidentialite !");
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
