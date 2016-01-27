package projet_java;

import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
				String messageConnect = "CONNECT#".concat(adresseMail).concat("#").concat(motdepasse);
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
