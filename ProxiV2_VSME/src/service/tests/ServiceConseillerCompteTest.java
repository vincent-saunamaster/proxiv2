package service.tests;

import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import metier.Adresse;
import metier.Client;
import metier.Compte;
import service.IConseiller;
import service.Services;
import service.exception.AbsenceDeCompteCourantException;
import service.exception.AbsenceDeCompteEpargneException;
import service.exception.CompteCourantExistantException;
import service.exception.CompteEpargneExistantException;

public class ServiceConseillerCompteTest {

		//Test d'ajout d'un compte � un client
	@Ignore
	@Test
	public void testAjouterCompteEpargneClient() {
		IConseiller sc = new Services(); //Cr�ation d'un service
		
		//Cr�ation d'un compte et instanciation
		Compte ce1 = new Compte();
		ce1.setNumeroCompte(123);
		ce1.setSolde(10000);
		ce1.setTypeCompte("epargne");
		
		
		Adresse a1 = new Adresse("rue A",69000,"Lyon");
		
		Client c1 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); //Cr�ation d'un client avec instanciation
		
		c1.setComptes((Collection<Compte>) ce1);	//Association d'un compte1 au client1
					
		Client c2 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); // Cr�ation d'un client 2 identique au client 1
		try {
			sc.ajouterCompteClient(c2,ce1);
		} catch (CompteEpargneExistantException | CompteCourantExistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	//Appel de la m�thode ajouterCompteClient pour associer le compte1 au client2
				
		//V�rifier que le client1 a le m�me compte que client 2
		Assert.assertEquals(true, (c1.getComptes().size()==c2.getComptes().size()));
	}
	
	//Test Ajouter Compte Epargne dans le cas o� le client � d�j� un compte Epargne
	@Ignore
	@Test
	public void testAjouterCompteEpargneClient2() {
		IConseiller sc = new Services(); //Cr�ation d'un service
		
		//Cr�ation d'un compte et instanciation
				Compte ce1 = new Compte();
				ce1.setNumeroCompte(123);
				ce1.setSolde(10000);
				ce1.setTypeCompte("epargne");
		
		Adresse a1 = new Adresse("rue A",69000,"Lyon");
		
		Client c1 = new Client("Toto","Titi","0606060606",001,a1,"particulier");//Cr�ation et instanciation d'un client
		c1.setComptes((Collection<Compte>) ce1);	//Association du compte au client 
		
		//Cr�ation d'un compte 2 diff�rent du compte1
		Compte ce2 = new Compte();
		ce2.setNumeroCompte(456);
		ce2.setSolde(1000);
		ce2.setTypeCompte("epargne");
		
		
		Client c2 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); // Cr�ation d'un client2 identique au client 1
		c2.setComptes((Collection<Compte>) ce1); //Association du Compte1 au client2
		
		try {
			sc.ajouterCompteClient(c2, ce2);
		} catch (CompteEpargneExistantException | CompteCourantExistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Appel de la m�thode AjouterCompteClient pour essayer d'ajouter le compte 2 au client2
				
			
		//V�rifie que le compte2 du client2 n'a pas �t� ajout�
		Assert.assertEquals(true, (c1.getComptes().size()==c2.getComptes().size()));
	}
	
	
	
	// Test identique au testAjouterCompteEpargneClient avec un Compte Courant
	
	@Ignore
	@Test
	public void testAjouterCompteCourantClient() {
		IConseiller sc = new Services();
		
		//Cr�ation d'un compte1
		Compte ce1 = new Compte();
		ce1.setNumeroCompte(123);
		ce1.setSolde(10000);
		ce1.setTypeCompte("epargne"); 
		Adresse a1 = new Adresse("rue A",69000,"Lyon");
		
		Client c1 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); //Cr�ation d'un client
		
		c1.setComptes((Collection<Compte>) ce1); //Assigner le compte au client
					
		
		Client c2 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); //Cr�ation d'un client2
		try {
			sc.ajouterCompteClient(c2,ce1);
		} catch (CompteEpargneExistantException | CompteCourantExistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Assignation du compte1 au client2 avec la fonction AjouterClient
		
		Assert.assertEquals(true, (c1.getComptes().size()==c2.getComptes().size()));
	}
	
	//Test Ajouter Compte Courant dans le cas o� le client � d�j� un compte Courant
	@Ignore
	@Test
	public void testAjouterCompteCourantClient2() {
		IConseiller sc = new Services();
		
		 // Cr�ation d'un Compte 1
		Compte ce1 = new Compte();
		ce1.setNumeroCompte(123);
		ce1.setSolde(10000);
		ce1.setTypeCompte("courant"); 
		
		Adresse a1 = new Adresse("rue A",69000,"Lyon");
		
		Client c1 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); //Cr�ation d'un client 1
		
		c1.setComptes((Collection<Compte>) ce1); //Assignation du compte1 au client1
		
		//Cr�ation d'un compte 2
		Compte ce2 = new Compte();
		ce2.setNumeroCompte(456);
		ce2.setSolde(1000);
		ce2.setTypeCompte("courant");
		
		Client c2 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); // Cr�ation d'un client2 identique au client1
		c2.setComptes((Collection<Compte>) ce1); //Assignation du compte1 au client 2
		
		try {
			sc.ajouterCompteClient(c2, ce2);
		} catch (CompteEpargneExistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompteCourantExistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Appel de AjouterCompteClient pour assigner le compte2 au client2
		
		Assert.assertEquals(true, (c1.getComptes().size()==c2.getComptes().size())); //Test pour verifier que la modification n'a pas �t� possible
	
	}
	
	//Test de la suppression de l'association d'un compte Epargne � un client	
	@Ignore
	@Test
	public void testSupprimerCompteEpargneClient() {
		
		IConseiller sc = new Services(); //Cr�ation d'un service
		
		
			//Cr�ation compte
		Compte ce1 = new Compte();
		ce1.setNumeroCompte(123);
		ce1.setSolde(10000);
		ce1.setTypeCompte("epargne"); 
		
		Adresse a1 = new Adresse("rue A",69000,"Lyon");
		Client c1 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); //Cr�ation client
		
		
		Client c2 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); //Cr�ation Client2
		c2.setComptes((Collection<Compte>) ce1);
		try {
			sc.supprimerCompteClient(ce1,c2);
		} catch (AbsenceDeCompteEpargneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbsenceDeCompteCourantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(true, ((c1.getComptes().size()==c2.getComptes().size())));
	}
	
	//Test de la suppression de l'association d'un compte courant � un client	
	@Ignore
	@Test
	public void testSupprimerCompteCourantClient() {
		
		IConseiller sc = new Services(); //Cr�ation d'un service
		
		
		//Cr�ation compte
		Compte ce1 = new Compte();
		ce1.setNumeroCompte(123);
		ce1.setSolde(10000);
		ce1.setTypeCompte("epargne"); 
		
		Adresse a1 = new Adresse("rue A",69000,"Lyon");
		Client c1 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); //Cr�ation client
		
		
		Client c2 = new Client("Toto","Titi","0606060606",001,a1,"particulier"); //Cr�ation Client2
		c2.setComptes((Collection<Compte>) ce1); //Assignation du compte courant au client
		try {
			sc.supprimerCompteClient(ce1,c2);
		} catch (AbsenceDeCompteEpargneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbsenceDeCompteCourantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Appel de la fonction pour supprimer le compte
		
		Assert.assertEquals(true, (c1.getComptes().size()==c2.getComptes().size()));
	}
	
	
	
	
	@Ignore
	@Test
	public void testlisterCompteClient() {
		fail("Not yet implemented");
	}

}
