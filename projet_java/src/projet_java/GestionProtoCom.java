package projet_java;

public class GestionProtoCom {

	private Utilisateur user = new Utilisateur();
	
	
	public String analyserTraiter(String req) {
		String[] requete = req.split("#");
		
		if(requete[0].equalsIgnoreCase("INITMESSAGERIE"))
		{
			if (requete.length==4)
			{
				String numFiche = requete[1];
				String adresseIp = requete[2];
				String numPort = requete[3];
				String reponse = user.initialiserSocketMessagerie(numFiche,adresseIp,numPort);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("USERCONNECT"))
		{
			if (requete.length==2)
			{
				String numFiche = requete[1];
				String reponse = user.rechercheUserInLine(numFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		else if(requete[0].equalsIgnoreCase("DECONNECTINLINE"))
		{
			if (requete.length==2)
			{
				String numFiche = requete[1];
				String reponse = user.decoUserInLine(numFiche);
				return(reponse);
			}
			else
			{
				return("ERREUR : REQUETE MAL FORMEE");
			}
			
		}
		return("ERREURSRVCom");
	}
	
}