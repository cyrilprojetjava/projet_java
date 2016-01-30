package projet_java;

import java.util.*;

public class GestionProtoAuth {

	private Utilisateur user = new Utilisateur();
	
	
	public String analyserTraiter(String req) {
		String[] requete = req.split("#");
		int validerConnexion;
		int validerCreation;
		
		if(requete[0].equalsIgnoreCase("CREATE"))
		{
			if (requete.length==8 ){
				user.setNom(requete[1]);
				user.setPrenom(requete[2]);
				user.setEmail(requete[3]);
				user.setMdp(requete[4]);
				user.setTelephone(requete[5]);
				user.setFormation(requete[6]);
				user.setAnneeDiplome(requete[7]);
				validerCreation = user.creerCompte(user);
				if(validerCreation == 0)
				{	
					return ("CREATIONREFUSEE");
				}
				else
					return ("CREATIONOK"+user.getNumeroFiche());
			}
			else
				return("ERREUR : REQUETE MAL FORMEE");
		}else if(requete[0].equalsIgnoreCase("CONNECT"))
		{
			if (requete.length==3 ){
				validerConnexion = user.connexion(requete[1],requete[2]);
				if (validerConnexion == 0)
				{
					return("CONNEXIONREFUSEE");
				}
				else
					return ("CONNEXIONOK#"+validerConnexion);
			}
			else
				return("ERREUR : REQUETE MAL FORMEE");
		}
		return("ERREURSRV");
	}

}
