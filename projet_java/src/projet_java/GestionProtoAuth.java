//#####################################################################################
// Class qui permet de traiter les messages des clients	pour le serveur annuaire							 
//#####################################################################################

package projet_java;

public class GestionProtoAuth 
{

	private Utilisateur user = new Utilisateur();
	
	public String analyserTraiter(String req) 
	{
		// On recupére les informations qui sont séparé par des #
		String[] requete = req.split("#");
		
		int validerConnexion;
		int validerCreation;
		
		// Si le debut du message est CREATE
		if(requete[0].equalsIgnoreCase("CREATE"))
		{
			// On regarde qu'il y ait bien 9 paramétres
			if (requete.length == 9 )
			{
				// On remplace les informations par les informations reçues
				user.setNom(requete[1]);
				user.setPrenom(requete[2]);
				user.setEmail(requete[3]);
				user.setMdp(requete[4]);
				user.setTelephone(requete[5]);
				user.setFormation(requete[6]);
				user.setAnneeDiplome(requete[7]);
				user.setCompetence(requete[8]);
				
				// On fait appel a la fonction creerCompte qui permet de creer un compte dans la base de donnees
				validerCreation = user.creerCompte(user);
				
				// On regarde que l'inscription de l'utilisateur s'est bien effectué et on renvoi le message adéquate
				if(validerCreation == -1)
				{	
					return ("CREATIONREFUSEE");
				}
				else
				{
					return ("CREATIONOK#"+user.getNumeroFiche());
				}
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		} 
		// Si le debut du message est CONNECT
		else if(requete[0].equalsIgnoreCase("CONNECT"))
		{
			// on verifie la taille de la requette
			if (requete.length == 3 )
			{
				// On appelle la fonction Connexion qui permet a l'utilisateur de se connecter sur les serveurs
				validerConnexion = user.connexion(requete[1],requete[2]);
				
				// On verifie que la connexion c'est bien passé et on renvoi le message adéquate
				if (validerConnexion == 0)
				{
					return("CONNEXIONREFUSEE");
				}
				else
				{
					return ("CONNEXIONOK#"+validerConnexion);
				}
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		// Si le debut du message est SUPPRESSIONCOMPTE
		else if(requete[0].equalsIgnoreCase("SUPPRESSIONCOMPTE"))
		{
			// on verifie la taille de la requette
			if (requete.length==2 )
			{
				// On appelle la fonction supprimerCompte qui permet a l'utilisateur de supprimer son compte
				 String reponse = user.supprimerCompte(requete[1]);
				 
				 return(reponse);
			}
			else
			{
				// Si la taille de la requette n'est pas bonnne on renvoi une erreur
				return("ERREUR : REQUETE MAL FORMEE");
			}
		}
		return("ERREURSRVAuth");
	}

}
