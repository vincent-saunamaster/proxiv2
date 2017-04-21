package dao;

import java.sql.SQLException;
import java.util.Collection;

import metier.Adresse;
import metier.Agence;
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

public class daoJPA implements IDao {

	@Override
	public Compte recuperationCompte(int idcompte) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double recuperationSolde(Compte c1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int recuperationidAdresse(Client c) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int authentificationConseiller(String login, String pwd) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compterNombreClient(int idcon) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void ajouterClient(int idcon, Client c) throws LeConseillerADeja10Clients {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifierClient(Client c, String nom, String prenom, Adresse a, String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void supprimerClient(Client c, int idcon) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Client> listerClient(int idcon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void activationCarteVisa(Compte c, CarteBancaire cv) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivationCarteVisa(Compte c, CarteBancaire cv) {
		// TODO Auto-generated method stub

	}

	@Override
	public Compte creationCompte(Compte c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ajouterCompteClient(Client c, Compte co)
			throws CompteEpargneExistantException, CompteCourantExistantException {
		// TODO Auto-generated method stub

	}

	@Override
	public void supprimerCompteClient(Compte compte, Client c)
			throws AbsenceDeCompteEpargneException, AbsenceDeCompteCourantException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Compte> listerCompteClient(Client c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Client> listerClient(String motcle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client creerClient(Client c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Placement creerPlacement(String typePlacement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void supprimerPlacement(Placement placement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void effectuerVirement(int montant, Compte c1, Compte c2)
			throws MontantNegatifException, MontantSuperieurAuSoldeException, DecouvertNonAutorise {
		// TODO Auto-generated method stub

	}

	@Override
	public double effectuerSimulationCredit(double montant, int taux, int duree) throws MontantNegatifException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String effectuerAudit(Agence agence) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ajouterConseiller(int idge, Conseiller co) {
		// TODO Auto-generated method stub

	}

	@Override
	public void supprimerConseiller(Conseiller c, int idge) {
		// TODO Auto-generated method stub

	}

	@Override
	public void listerConseiller(int idge) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifierConseiller(Conseiller c, Adresse a, String telephone) {
		// TODO Auto-generated method stub

	}

	@Override
	public int recuperationidAdresse(Adresse a) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int recuperationidClient(int idPersonne) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int recuperationidPersonne(Client c) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String recuperationTypeCompte(Compte c) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client recuperationClient(int idCli) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int recuperationidClientParticulier(int idCli) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int recuperationidClientEntreprise(int idCli) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
