package service;

import java.sql.SQLException;
import java.util.Collection;

import metier.Adresse;
import metier.CarteBancaire;
import metier.Client;
import metier.Compte;
import metier.Conseiller;
import metier.Placement;
import service.exception.AbsenceDeCompteCourantException;
import service.exception.AbsenceDeCompteEpargneException;
import service.exception.CompteCourantExistantException;
import service.exception.CompteEpargneExistantException;
import service.exception.DecouvertNonAutorise;
import service.exception.LeConseillerADeja10Clients;
import service.exception.MontantNegatifException;
import service.exception.MontantSuperieurAuSoldeException;

/**
 * 
 * Interface ConseillerClient faisant appelle aux m�thodes d'Ajout/Modification/Suppression/Affichage d'un client par un Conseiller
 * @author ME VS
 * 
 *
 *
 */

public interface IConseiller {
	public Compte recuperationCompte(int idcompte) throws SQLException;
	public Client recuperationClient(int idCli) throws SQLException;
	public int authentificationConseiller(String login, String pwd);
	
	public void ajouterClient(int idcon, Client c) throws LeConseillerADeja10Clients;
	public void modifierClient(Client c, String nom, String prenom, Adresse a, String email);
	public void supprimerClient(Client c, int idcon);
	public Collection<Client> listerClient(int idcon);
	public Collection<Client> listerClient(String motcle);
	
	public void activationCarteVisa(Compte c, CarteBancaire cv);
	public void desactivationCarteVisa(Compte c, CarteBancaire cv);
	
	public int compterNombreClient(int idcon);
	
	public void ajouterCompteClient (Client c, Compte co) throws CompteEpargneExistantException, CompteCourantExistantException;
	public void supprimerCompteClient (Compte compte, Client c) throws AbsenceDeCompteEpargneException, AbsenceDeCompteCourantException;
	public Collection<Compte> listerCompteClient (Client c);
	
	
	public Placement creerPlacement(String typePlacement);
	public void supprimerPlacement(Placement placement);
	
	public void effectuerVirement(int montant,Compte c1, Compte c2 )throws MontantNegatifException, MontantSuperieurAuSoldeException, DecouvertNonAutorise;
	public double effectuerSimulationCredit(double montant, int taux, int duree) throws MontantNegatifException;
	
	
	
	
}
