package projet_java;

import java.util.*;

public class GestionProtoAnnuaire {

	private Utilisateur user = new Utilisateur();
	private Bd bdAnnu = new Bd();

	public String analyserTraiter(String req) {
		String[] requete = req.split("#");
		
		if(requete[0].equalsIgnoreCase("CREATE"))
		{
			
			if (requete.length==7 )
			{
				return ("CREATIONOK");

			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
	
		else if(requete[0].equalsIgnoreCase("CONSULTINFOPERSO"))
		{
			if (requete.length==2 ) /* ne pas oublier de modif suivant le nb de parametre */
			{
				//bdAnnu.consulterBdAnnuaire(requete[1]);
				return("CONSULTINFOPERSO");
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		
		return("ERREURSRVAnnuaire");
	}

}
