//#####################################################################################
// Class qui permet de traiter les messages des clients	pour le serveur authentification							 
//#####################################################################################

package projet_java;

public class GestionProtoAnnuaire 
{

	private Utilisateur user = new Utilisateur();


	public String analyserTraiter(String req) 
	{
		// On recupére les informations qui sont séparé par des #
		String[] requete = req.split("#");
		
		// Si le debut de la requette commence par CREATE
		if(requete[0].equalsIgnoreCase("CREATE"))
		{
			// On regarde qu'il y ait bien 8 paramétres
			if (requete.length == 8 )
			{
				return ("CREATIONOK");
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		// Si le debut de la requette commence par CONSULTINFOPERSO
		else if(requete[0].equalsIgnoreCase("CONSULTINFOPERSO"))
		{
			// On regarde qu'il y ait bien 8 paramétres
			if (requete.length==2 )
			{
				// On récupére le numéro de la fiche de l'utilisateur
				String numFiche = requete[1];
				// On appelle la fonction qui permet de consulter les informations perso de l'utilisateur
				String reponse = user.consulterInfoPerso(numFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		// Si le debut de la requette commence par MODIFINFOPERSONOM
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSONOM"))
		{
			if (requete.length==3)
			{
				String numFiche = requete[1];
				// On récupére le nom de l'utilisateur pour le changer dans la fonction modificationInformationNom
				String nom = requete[2];
				String reponse = user.modificationInformationNom(numFiche, nom);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par MODIFINFOPERSOPRENOM
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOPRENOM"))
		{
			if (requete.length==3)
			{
				String numFiche = requete[1];
				// On récupére le prenom de l'utilisateur pour le changer dans la fonction modificationInformationPrenom
				String prenom = requete[2];
				String reponse = user.modificationInformationPrenom(numFiche, prenom);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par MODIFINFOPERSOTEL
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOTEL"))
		{
			if (requete.length==3)
			{
				String numFiche = requete[1];
				// On récupére le telephone de l'utilisateur pour le changer dans la fonction modificationInformationTel
				String tel = requete[2];
				String rep = user.modificationInformationTel(numFiche, tel);
				return(rep);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}	
		}
		// Si le debut de la requette commence par MODIFINFOPERSOFORMATION
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOFORMATION"))
		{
			if (requete.length==3)
			{
				String numFiche = requete[1];
				// On récupére la formation de l'utilisateur pour le changer dans la fonction modificationInformationFormation
				String formation = requete[2];
				String reponse = user.modificationInformationFormation(numFiche, formation);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}	
		}
		// Si le debut de la requette commence par MODIFINFOPERSOANDIPLOME
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOANDIPLOME"))
		{
			if (requete.length==3)
			{
				String numFiche = requete[1];
				// On récupére l'annee d'obtention du diplome de l'utilisateur pour le changer dans la fonction modificationInformationAnneeDiplome
				String anneeDiplome = requete[2];
				String reponse = user.modificationInformationAnneeDiplome(numFiche, anneeDiplome);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}	
		}
		// Si le debut de la requette commence par MODIFINFOPERSOMAIL
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOMAIL"))
		{
			if (requete.length==3)
			{
				String numFiche = requete[1];
				// On récupére le mail de l'utilisateur pour le changer dans la fonction modificationInformationMail
				String mail = requete[2];
				String reponse = user.modificationInformationMail(numFiche, mail);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}	
		}
		// Si le debut de la requette commence par MODIFINFOPERSOMDP
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOMDP"))
		{
			if (requete.length==3)
			{
				String numFiche = requete[1];
				// On récupére le mdp de l'utilisateur pour le changer dans la fonction modificationInformationMotDePasse
				String mdp = requete[2];
				String reponse = user.modificationInformationMotDePasse(numFiche, mdp);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}	
		}
		// Si le debut de la requette commence par MODIFINFOPERSOCOMPETENCE
		else if(requete[0].equalsIgnoreCase("MODIFINFOPERSOCOMPETENCE"))
		{
			if (requete.length==3)
			{
				String numFiche = requete[1];
				// On récupére la compétence de l'utilisateur pour le changer dans la fonction modificationInformationCompetence
				String competence = requete[2];
				String reponse = user.modificationInformationCompetence(numFiche, competence);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par RECHERCHENOM
		else if(requete[0].equalsIgnoreCase("RECHERCHENOM"))
		{
			if (requete.length==2)
			{
				// On recupére le nom de l'utilisateur pour le rechercher dans la base de donnee
				String nom = requete[1];
				String reponse = user.rechercheNom(nom);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par RECHERCHEPRENOM
		else if(requete[0].equalsIgnoreCase("RECHERCHEPRENOM"))
		{
			if (requete.length==2)
			{
				// On recupére le prenom de l'utilisateur pour le rechercher dans la base de donnee
				String prenom = requete[1];
				String reponse = user.recherchePrenom(prenom);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par RECHERCHEMAIL
		else if(requete[0].equalsIgnoreCase("RECHERCHEMAIL"))
		{
			if (requete.length==2) 
			{
				// On recupére le mail de l'utilisateur pour le rechercher dans la base de donnee
				String mail = requete[1];
				String reponse = user.rechercheMail(mail);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par RECHERCHEFORMATION
		else if(requete[0].equalsIgnoreCase("RECHERCHEFORMATION"))
		{
			if (requete.length==2) 
			{
				// On recupére la formation de l'utilisateur pour le rechercher dans la base de donnee
				String formation = requete[1];
				String reponse = user.rechercheFormation(formation);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par RECHERCHEANDIPLOME
		else if(requete[0].equalsIgnoreCase("RECHERCHEANDIPLOME"))
		{
			if (requete.length==2) 
			{
				// On recupére l'annee d'obtention du diplome de l'utilisateur pour le rechercher dans la base de donnee
				String anneeDiplome = requete[1];
				String reponse = user.rechercheAnneeDiplome(anneeDiplome);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par RECHERCHECOMPETENCE
		else if(requete[0].equalsIgnoreCase("RECHERCHECOMPETENCE"))
		{
			if (requete.length==2)
			{
				// On recupére la competence de l'utilisateur pour le rechercher dans la base de donnee
				String competence = requete[1];
				String reponse = user.rechercheCompetence(competence);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par VISIBILITENOM
		else if(requete[0].equalsIgnoreCase("VISIBILITENOM"))
		{
			if (requete.length==2)
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe de la visibilite
				String NumFiche = requete[1];
				String reponse = user.visibiliteNom(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}	
		}
		// Si le debut de la requette commence par MODIFVISINOM
		else if(requete[0].equalsIgnoreCase("MODIFVISINOM"))
		{
			if (requete.length==2)
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe modification de la visibilite
				String NumFiche = requete[1];
				String reponse = user.modifVisibiliteNom(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par VISIBILITEPRENOM
		else if(requete[0].equalsIgnoreCase("VISIBILITEPRENOM"))
		{
			if (requete.length==2)
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe de la visibilite
				String NumFiche = requete[1];
				String reponse = user.visibilitePrenom(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par MODIFVISIPRENOM
		else if(requete[0].equalsIgnoreCase("MODIFVISIPRENOM"))
		{
			if (requete.length==2) 
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe modification de la visibilite
				String NumFiche = requete[1];
				String reponse = user.modifVisibilitePrenom(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par VISIBILITETEL
		else if(requete[0].equalsIgnoreCase("VISIBILITETEL"))
		{
			if (requete.length==2)
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe de la visibilite
				String NumFiche = requete[1];
				String reponse = user.visibiliteTel(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par MODIFVISITEL
		else if(requete[0].equalsIgnoreCase("MODIFVISITEL"))
		{
			if (requete.length==2) 
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe modification de la visibilite
				String NumFiche = requete[1];
				String reponse = user.modifVisibiliteTel(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par VISIBILITEFORM
		else if(requete[0].equalsIgnoreCase("VISIBILITEFORM"))
		{
			if (requete.length==2) 
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe de la visibilite
				String NumFiche = requete[1];
				String reponse = user.visibiliteFormation(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par MODIFVISIFORM
		else if(requete[0].equalsIgnoreCase("MODIFVISIFORM"))
		{
			if (requete.length==2) 
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe modification de la visibilite
				String NumFiche = requete[1];
				String reponse = user.modifVisibiliteFormation(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par VISIBILITEANDIPLOME
		else if(requete[0].equalsIgnoreCase("VISIBILITEANDIPLOME"))
		{
			if (requete.length==2) 
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe de la visibilite
				String NumFiche = requete[1];
				String reponse = user.visibiliteAnneeDiplome(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par MODIFVISIANDIPLOME
		else if(requete[0].equalsIgnoreCase("MODIFVISIANDIPLOME"))
		{
			if (requete.length==2) 
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe modification de la visibilite
				String NumFiche = requete[1];
				String reponse = user.modifVisibiliteAnneeDiplome(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par VISIBILITECOMPETENCE
		else if(requete[0].equalsIgnoreCase("VISIBILITECOMPETENCE"))
		{
			if (requete.length==2)
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe de la visibilite
				String NumFiche = requete[1];
				String reponse = user.visibiliteCompetence(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par MODIFVISICOMPETENCE
		else if(requete[0].equalsIgnoreCase("MODIFVISICOMPETENCE"))
		{
			if (requete.length==2) 
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe modification de la visibilite
				String NumFiche = requete[1];
				String reponse = user.modifVisibiliteCompetence(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		// Si le debut de la requette commence par SEELIKE
		else if(requete[0].equalsIgnoreCase("SEELIKE"))
		{
			if (requete.length==2) 
			{
				// On recupére le numero de fiche de l'utilisateur pour appeler la fonction qui s'occupe de voir les likes
				String NumFiche = requete[1];
				String reponse = user.seeLike(NumFiche);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par LIKE
		else if(requete[0].equalsIgnoreCase("LIKE"))
		{
			if (requete.length==3)
			{
				// On recupére le numero de fiche des utilisateurs
				String numFicheLikeur = requete[1];
				String numPersLike = requete[2];
				String reponse = user.like(numFicheLikeur,numPersLike);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut de la requette commence par DONTLIKE
		else if(requete[0].equalsIgnoreCase("DONTLIKE"))
		{
			if (requete.length==3) 
			{
				// On recupére le numero de fiche des utilisateurs
				String numFicheLikeur = requete[1];
				String numPersLike = requete[2];
				String reponse = user.dontLike(numFicheLikeur,numPersLike);
				return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		return("ERREURSRVAnnuaire");
	}
	
	

}
