package projet_java;

import java.util.Scanner;

import com.mysql.jdbc.Blob;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;



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
				System.out.println("Tapez 1 pour vous connecter si vous avez deja un compte");
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
						//System.out.println(retour);
						String[] tabretour = retour.split("#");
						Integer verificationUtil = Integer.parseInt(tabretour[1]);
						if(verificationUtil != -1)
						{
							user1.setNumeroFiche(Integer.parseInt(tabretour[1]));
							System.out.println("Connexion reussie");
							
							sockCom2 = new Socket("localhost",13215);
							fluxSortieSocket2 = new PrintStream(sockCom2.getOutputStream());
							fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom2.getInputStream()));
							Socket sockMessagerie; 
							//InetAddress ipAddress = sockCom2.getLocalAddress();
							//System.out.println(ipAddress);
							sockMessagerie = new Socket("localhost", 15000);
							PrintStream     fluxSortieSocket3;
							BufferedReader  fluxEntreeSocket3;
							fluxSortieSocket3 = new PrintStream(sockMessagerie.getOutputStream());
							fluxEntreeSocket3 = new BufferedReader(new InputStreamReader(sockMessagerie.getInputStream()));
							ServerSocket sockEcouteClient = new ServerSocket(0);
							String initMessagerie = "INITMESSAGERIE#"+user1.getNumeroFiche()+"#"+sockMessagerie.getLocalAddress()+"#"+sockEcouteClient.getLocalPort();
							ThreadEcoute thEcoute = new ThreadEcoute(sockEcouteClient);
							thEcoute.start();
							//System.out.println(initMessagerie);
							fluxSortieSocket3.println(initMessagerie);
							String msgInst = fluxEntreeSocket3.readLine();
							System.out.println(msgInst);
							Integer menu2 = 1;
							while (menu2 == 1)
							{
								System.out.println("------------------------------------------------------------");
								System.out.println("MENU CONNECTE :");
								System.out.println("Tapez 0 pour vous deconnecter");
								System.out.println("Tapez 1 pour consulter vos informations dans l'annuaire");
								System.out.println("Tapez 2 pour modifier une information");
								System.out.println("Tapez 3 pour modifier la confidentialite d'une de vos information");
								System.out.println("Tapez 4 pour faire une recherche sur l'annuaire");
								System.out.println("Tapez 5 pour supprimer votre compte");
								System.out.println("Tapez 6 pour rentrer dans le menu de messagerie");
								System.out.println("------------------------------------------------------------");
								Integer choixMenu2 = LireIntClavier();
								switch (choixMenu2) 
								{
									case 0:
									{
										sockCom2 = new Socket("localhost",13215);
										fluxSortieSocket2 = new PrintStream(sockCom2.getOutputStream());
										fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom2.getInputStream()));
										String messageDecoUserInLine = "DECONNECTINLINE#"+(user1.getNumeroFiche());
										fluxSortieSocket3.println(messageDecoUserInLine);
										String retour2 = fluxEntreeSocket3.readLine();
										System.out.println("Vous etes deconnecte");
										sockCom2.close();
										sockMessagerie.close();
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
										System.out.println("Tapez 0 pour quitter le menu");
										System.out.println("Tapez 1 pour modifier votre nom");
										System.out.println("Tapez 2 pour modifier votre prenom");
										System.out.println("Tapez 3 pour modifier votre telephone");
										System.out.println("Tapez 4 pour modifier votre formation");
										System.out.println("Tapez 5 pour modifier votre annee obtention diplome");
										System.out.println("Tapez 6 pour modifier votre identifiant mail");
										System.out.println("Tapez 7 pour modifier votre mot de passe");
										System.out.println("Tapez 8 pour modifier votre competence");
										System.out.println("------------------------------------------------------------");
										Integer choixMenu3 = LireIntClavier();
										switch (choixMenu3)
										{
												case 0:
												{
													System.out.println("Vous avez quitter le menu");
													
													break;
												}
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
												case 8:
												{
													
													System.out.println("Veuillez entrer votre nouvelle competence");
													String newCompetence = LireStringClavier();
													String modifInfoPersoCompetence = "MODIFINFOPERSOCOMPETENCE#"+(user1.getNumeroFiche());
													String newInfoPersoCompetence = modifInfoPersoCompetence.concat("#").concat(newCompetence);
													fluxSortieSocket2.println(newInfoPersoCompetence);
													String retour10 = fluxEntreeSocket2.readLine();
													System.out.println(retour10);
													break;
												}
									
								}
								sockCom2.close();
								break;
								
							}
									case 3:
									{
										System.out.println("------------------------------------------------------------");
										System.out.println("Modification de la confidentialite de vos informations");
										System.out.println("Tapez 0 pour quitter le menu");
										System.out.println("Tapez 1 pour voir la confidentialite de votre nom");
										System.out.println("Tapez 2 pour modifier la confidentialite de votre nom");
										System.out.println("Tapez 3 pour voir la confidentialite de votre prenom");
										System.out.println("Tapez 4 pour modifier la confidentialite de votre prenom");
										System.out.println("Tapez 5 pour voir la confidentialite de numero de telephone");
										System.out.println("Tapez 6 pour modifier la confidentialite de numero de telephone");
										System.out.println("Tapez 7 pour voir la confidentialite de votre formation");
										System.out.println("Tapez 8 pour modifier la confidentialite de votre formation");
										System.out.println("Tapez 9 pour voir la confidentialite de votre annee d'obtention de diplome");
										System.out.println("Tapez 10 pour modifier la confidentialite de votre annee d'obtention de diplome");
										System.out.println("Tapez 11 pour voir la confidentialite de votre competence");
										System.out.println("Tapez 12 pour modifier la confidentialite de votre competence");
										System.out.println("------------------------------------------------------------");
										int choixMenu4 = LireIntClavier();
										sockCom2 = new Socket("localhost",13215);
										fluxSortieSocket2 = new PrintStream(sockCom2.getOutputStream());
										fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom2.getInputStream()));
										
										switch (choixMenu4) {
											case 0:
											{
												System.out.println("Vous avez quitter le menu");
												break;
											}
											case 1 : 
											{
												String messageVisibiliteNom = "VISIBILITENOM#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageVisibiliteNom);
												String retour1 = fluxEntreeSocket2.readLine();
												System.out.println(retour1);
												break;
											}
											case 2 :
											{
												String messageModifVisibiliteNom = "MODIFVISINOM#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageModifVisibiliteNom);
												String retour2 = fluxEntreeSocket2.readLine();
												System.out.println(retour2);
												break;
											}
											case 3 : 
											{
												String messageVisibilitePrenom = "VISIBILITEPRENOM#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageVisibilitePrenom);
												String retour3 = fluxEntreeSocket2.readLine();
												System.out.println(retour3);
												break;
											}
											case 4 :
											{
												String messageModifVisibilitePrenom = "MODIFVISIPRENOM#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageModifVisibilitePrenom);
												String retour4 = fluxEntreeSocket2.readLine();
												System.out.println(retour4);
												break;
											}
											case 5 : 
											{
												String messageVisibiliteTel = "VISIBILITETEL#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageVisibiliteTel);
												String retour5 = fluxEntreeSocket2.readLine();
												System.out.println(retour5);
												break;
											}
											case 6 :
											{
												String messageModifVisibiliteTel = "MODIFVISITEL#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageModifVisibiliteTel);
												String retour6 = fluxEntreeSocket2.readLine();
												System.out.println(retour6);
												break;
											}
											case 7 : 
											{
												String messageVisibiliteFormation = "VISIBILITEFORM#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageVisibiliteFormation);
												String retour7 = fluxEntreeSocket2.readLine();
												System.out.println(retour7);
												break;
											}
											case 8 :
											{
												String messageModifVisibiliteFormation = "MODIFVISIFORM#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageModifVisibiliteFormation);
												String retour8 = fluxEntreeSocket2.readLine();
												System.out.println(retour8);
												break;
											}
											case 9 : 
											{
												String messageVisibiliteAnDiplome = "VISIBILITEANDIPLOME#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageVisibiliteAnDiplome);
												String retour9 = fluxEntreeSocket2.readLine();
												System.out.println(retour9);
												break;
											}
											case 10 :
											{
												String messageModifVisibiliteFormation = "MODIFVISIANDIPLOME#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageModifVisibiliteFormation);
												String retour10 = fluxEntreeSocket2.readLine();
												System.out.println(retour10);
												break;
											}
											case 11 : 
											{
												String messageVisibiliteCompetence = "VISIBILITECOMPETENCE#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageVisibiliteCompetence);
												String retour11 = fluxEntreeSocket2.readLine();
												System.out.println(retour11);
												break;
											}
											case 12 :
											{
												String messageModifVisibiliteCompetence = "MODIFVISICompetence#"+(user1.getNumeroFiche());
												fluxSortieSocket2.println(messageModifVisibiliteCompetence);
												String retour12 = fluxEntreeSocket2.readLine();
												System.out.println(retour12);
												break;
											}
										
										
										}
										
										break;
									}
									
									
									case 4 :
									{
										sockCom2 = new Socket("localhost",13215);
										fluxSortieSocket2 = new PrintStream(sockCom2.getOutputStream());
										fluxEntreeSocket2 = new BufferedReader(new InputStreamReader(sockCom2.getInputStream()));
										System.out.println("------------------------------------------------------------");
										System.out.println("Recherche de personne dans l'annuaire");
										System.out.println("Tapez 0 pour quitter");
										System.out.println("Tapez 1 pour effectuer une recherche par nom");
										System.out.println("Tapez 2 pour effectuer une recherche par prenom");
										System.out.println("Tapez 3 pour effectuer une recherche par adresse mail");
										System.out.println("Tapez 4 pour effectuer une recherche par formation");
										System.out.println("Tapez 5 pour effectuer une recherche par annee d'obtention de diplome");
										System.out.println("Tapez 6 pour effectuer une recherche par competence");
										System.out.println("------------------------------------------------------------");
										Integer choixMenu5 = LireIntClavier();
										switch (choixMenu5) {
											case 0:
											{
												System.out.println("Vous avez quitter le menu");
												break;
											}
											case 1:
											{
												System.out.println("Veuillez entrer le nom de la personne que vous souhaitez rechercher");
												String nomPersonne = LireStringClavier();
												String messageRechercheNom = "RECHERCHENOM#".concat(nomPersonne);
												fluxSortieSocket2.println(messageRechercheNom);
												String retour1 = fluxEntreeSocket2.readLine();
												String[] tabretour1 = retour1.split("]");
												System.out.println("Resultat de votre recherche :\n");
												for (String i : tabretour1)
												{
												    System.out.println(i);
												}
												System.out.println("\n");
												break;
											}
											
											case 2:
											{
												System.out.println("Veuillez entrer le prenom de la personne que vous souhaitez rechercher");
												String prenomPersonne = LireStringClavier();
												String messageRecherchePrenom = "RECHERCHEPRENOM#".concat(prenomPersonne);
												fluxSortieSocket2.println(messageRecherchePrenom);
												String retour2 = fluxEntreeSocket2.readLine();
												String[] tabretour2 = retour2.split("]");
												System.out.println("Resultat de votre recherche :\n");
												for (String i : tabretour2)
												{
												    System.out.println(i);
												}
												System.out.println("\n");
												break;
											}
											
											case 3:
											{
												System.out.println("Veuillez entrer le mail de la personne que vous souhaitez rechercher");
												String mailPersonne = LireStringClavier();
												String messageRechercheMail = "RECHERCHEMAIL#".concat(mailPersonne);
												fluxSortieSocket2.println(messageRechercheMail);
												String retour3 = fluxEntreeSocket2.readLine();
												String[] tabretour3 = retour3.split("]");
												System.out.println("Resultat de votre recherche :\n");
												for (String i : tabretour3)
												{
												    System.out.println(i);
												}
												System.out.println("\n");
												break;
											}
											
											case 4:
											{
												System.out.println("Veuillez entrer la formation de la personne que vous souhaitez rechercher");
												String formationPersonne = LireStringClavier();
												String messageRechercheFormation = "RECHERCHEFORMATION#".concat(formationPersonne);
												fluxSortieSocket2.println(messageRechercheFormation);
												String retour4 = fluxEntreeSocket2.readLine();
												String[] tabretour4 = retour4.split("]");
												System.out.println("Resultat de votre recherche :\n");
												for (String i : tabretour4)
												{
												    System.out.println(i);
												}
												System.out.println("\n");
												break;
											}
											
											case 5:
											{
												System.out.println("Veuillez entrer l'annee d'obtention du diplome de la personne que vous souhaitez rechercher");
												String AnneeDiplomePersonne = LireStringClavier();
												String messageRechercheAnneeDiplome = "RECHERCHEANDIPLOME#".concat(AnneeDiplomePersonne);
												fluxSortieSocket2.println(messageRechercheAnneeDiplome);
												String retour5 = fluxEntreeSocket2.readLine();
												String[] tabretour5 = retour5.split("]");
												System.out.println("Resultat de votre recherche :\n");
												for (String i : tabretour5)
												{
												    System.out.println(i);
												}
												System.out.println("\n");
												break;
											}
											case 6:
											{
												System.out.println("Veuillez entrer la competence de la personne que vous souhaitez rechercher");
												String competencePersonne = LireStringClavier();
												String messageRechercheCompetence = "RECHERCHECOMPETENCE#".concat(competencePersonne);
												fluxSortieSocket2.println(messageRechercheCompetence);
												String retour6 = fluxEntreeSocket2.readLine();
												String[] tabretour6 = retour6.split("]");
												System.out.println("Resultat de votre recherche :\n");
												for (String i : tabretour6)
												{
												    System.out.println(i);
												}
												System.out.println("\n");
												break;
											}

										
									}
						
								}
								break;
							
									case 5:
									{
										sockCom = new Socket("localhost",13214);
										fluxSortieSocket = new PrintStream(sockCom.getOutputStream());
										fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
										String messageSuppressionCompte = "SUPPRESSIONCOMPTE#"+(user1.getNumeroFiche());
										//System.out.println(messageSuppressionCompte);
										fluxSortieSocket.println(messageSuppressionCompte);
										String retour1 = fluxEntreeSocket.readLine();
										System.out.println(retour1);
										sockCom.close();
										return;
										
									}
									case 6:
									{
										String messageUserInLine = "USERCONNECT#"+(user1.getNumeroFiche());
										fluxSortieSocket3.println(messageUserInLine);
										String retour1 = fluxEntreeSocket3.readLine();
										String[] tabretour1 = retour1.split("]");
										System.out.println("Resultat des utilisateurs connectes :\n");
										for (String i : tabretour1)
										{
										    System.out.println(i);
										}
										System.out.println("\n");
										System.out.println("------------------------------------------------------------");
										System.out.println("Menu messagerie");
										System.out.println("Tapez 0 pour vous deconnecter");
										System.out.println("Tapez 1 pour entrer le numero de conversation et avoir une discussion instantanee");
										System.out.println("Tapez 2 pour laisser un message à un utilisateur non connecte");
										System.out.println("Tapez 3 pour consulter vos messages");
										System.out.println("------------------------------------------------------------");
										Integer choixMenu6 = LireIntClavier();
										switch (choixMenu6) {
										
												case 0 : {
													System.out.println("Vous avez quitte la discution instantanee");
													menu2 = 1;
													break;
												}
												
												case 1 : {
													System.out.println("Entrez le numero de conversation");
													Integer numConversation = LireIntClavier();
													String messageConversation = "CONVINSTANT#"+(numConversation);
													fluxSortieSocket3.println(messageConversation);
													String retour2 = fluxEntreeSocket3.readLine();
													String requete[] = retour2.split("#");
													int numeroPort = Integer.parseInt(requete[2]);
													System.out.println(requete[1]+numeroPort);
													Socket sockComClient = new Socket("localhost", numeroPort);
													System.out.println("Entrez votre message :");
													String message = LireStringClavier();
													String quiSuisJe = "QUISUISJE#"+user1.getNumeroFiche();
													fluxSortieSocket3.println(quiSuisJe);
													String retour3 = fluxEntreeSocket3.readLine();
													String[] tabretour3 = retour3.split("]");
													for (String j : tabretour3)
													{
														message = message +j;
													    System.out.println();
													}
													
													PrintStream     fluxSortieSocket4;
													fluxSortieSocket4 = new PrintStream(sockComClient.getOutputStream());
													fluxSortieSocket4.println(message);
													break;
												}
												
												case 2 : {
													String messageUserOffLine = "USERNOTCONNECT#";
													fluxSortieSocket3.println(messageUserOffLine);
													String retour2 = fluxEntreeSocket3.readLine();
													String[] tabretour2 = retour2.split("]");
													System.out.println("Resultat des utilisateurs deconnectes :\n");
													for (String j : tabretour2)
													{
													    System.out.println(j);
													}
													System.out.println("\n");
													System.out.println("------------------------------------------------------------");
													System.out.println("Tapez 0 pour quitter");
													System.out.println("Tapez 1 pour entrer le numero de messagerie d'un utilisateur et lui laisser un message");
													System.out.println("------------------------------------------------------------");
													Integer choixMenu7 = LireIntClavier();
													switch (choixMenu7) {
														case 0:
														{
															System.out.println("Vous avez quitter le menu");
															break;
														}
														case 1 : {
															System.out.println("Tapez le numero de messagerie d'un utilisateur et laissez lui un message");
															Integer numMessagerie = LireIntClavier();
															System.out.println("Entrez votre message");
															String messageDepose = LireStringClavier();
															String messageGestionCom = "DEPOTMESSAGE#"+(numMessagerie)+"#"+(messageDepose)+"#"+user1.getNumeroFiche();															fluxSortieSocket3.println(messageGestionCom);
															String retour3 = fluxEntreeSocket3.readLine();
															System.out.println(retour3);

														}
														
													}
													
												}
													case 3 : {
														String messageRecu = "MESSAGERECU#"+user1.getNumeroFiche();
														fluxSortieSocket3.println(messageRecu);
														String retour3 = fluxEntreeSocket3.readLine();
														String[] tabretour3 = retour3.split("]");
														System.out.println("Voici les messages que vous avez recues durant votre absence :\n");
														for (String j : tabretour3)
														{
														    System.out.println(j);
														}
														
													}
												
												
												}
										
										//sockMessagerie.close();
							
										
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
					System.out.println("Veuillez entrer votre annee d'obtention de votre diplome");
					int anneeDiplomation = LireIntClavier();
					System.out.println("Veuillez entrer votre competence");
					String competence = LireStringClavier();
					System.out.println("ATTENTION : par defaut, la politique de confidentialite de vos donnees est ouverte a tous.\nVous pouvez modifier la confidentialite de vos parametres une fois connecte sur votre compte personnel");
					String messageInscription = "CREATE".concat("#").concat(nom).concat("#").concat(prenom).concat("#").concat(adresseMail).concat("#").concat(motdepasse).concat("#").concat(numTel).concat("#").concat(formation).concat("#").concat(String.valueOf(anneeDiplomation).concat("#").concat(competence));
					try {
						
						sockCom = new Socket("localhost",13214);
						fluxSortieSocket = new PrintStream(sockCom.getOutputStream());
						fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockCom.getInputStream()));
						fluxSortieSocket.println(messageInscription);
						String retour = fluxEntreeSocket.readLine();
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
		

	static String LireStringClavier() {
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
