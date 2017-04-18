package service;

import java.sql.SQLException;
import java.util.Collection;

import dao.Dao;
import dao.IDao;
import metier.Adresse;
import metier.Agence;
import metier.CarteBancaire;
import metier.Client;
import metier.ClientEntreprise;
import metier.ClientParticulier;
import metier.Compte;
import metier.CompteCourant;
import metier.CompteEpargne;
import metier.Conseiller;
import metier.Gerant;
import metier.Placement;
import service.exception.AbsenceDeCompteCourantException;
import service.exception.AbsenceDeCompteEpargneException;
import service.exception.CompteCourantExistantException;
import service.exception.CompteEpargneExistantException;
import service.exception.DecouvertNonAutorise;
import service.exception.LeConseillerADeja10Clients;
import service.exception.MontantNegatifException;
import service.exception.MontantSuperieurAuSoldeException;

public class Services implements IConseiller, IGerant {

	IDao idao = new Dao();

	
	public Services() 
		{
			super();
		}
	
	public int compterNombreClient(int idcon)
	{
		return idao.compterNombreClient(idcon);
	}
	
	public Collection<Client> recuperationClient(int idCli) throws SQLException
	{
		return idao.recuperationClient(idCli);
	}
	
	/**
	 * Authentification du conseiller
	 */
	@Override
	public int authentificationConseiller(String login, String pwd) 
		{
			return idao.authentificationConseiller(login,pwd);
		}
	
	
	/**
	 * Lister les clients d'un conseiller
	 */
	@Override
	public Collection<Client> listerClient(int idcon) 
		{
			return idao.listerClient(idcon);
		}
	
	
	/**
	 * Modification du nom, prenom, adresse et email d'un client
	 */
	@Override
	public void modifierClient(Client c, String nom, String prenom, Adresse a, String email) 
		{
			idao.modifierClient(c, nom, prenom,a,email);	
		}
	
	
	/**
	 * Lister les comptes d'un client
	 */
	@Override
	public Collection<Compte> listerCompteClient (Client c) 
		{
			return idao.listerCompteClient (c);
		}


		
	/**
	 * Lister les clients par mot cl� (recherche dans le nom)
	 */
	@Override
	public Collection<Client> listerClient(String motcle)
		{
			return idao.listerClient(motcle);	
		}
	
	
	
	/**
	 * Virement d'un montant d'un compte c1(d�biteur) � un compte c2(cr�diteur) 
	 * @throws MontantSuperieurAuSoldeException 
	 * @throws DecouvertNonAutorise 
	 */
	
	@Override
	public void effectuerVirement(int montant,Compte c1, Compte c2) throws MontantNegatifException, MontantSuperieurAuSoldeException, DecouvertNonAutorise {
		
		if (montant<0) //Test si le montant entr� est inf�rieur � 0
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			throw new MontantNegatifException("montant inférieur à zéro");
		}
		else 
		{
			String type;
			try {
				type = idao.recuperationTypeCompte(c1);
			
			if(type.equals("epargne")) //Test si le compte est un compte Epargne
			{
				if(montant<c1.getSolde()) // Test si le montant est inferieur au solde du compte
				{
					idao.effectuerVirement(montant, c1, c2);
				}
				else
				{
					throw new MontantSuperieurAuSoldeException("montant supperieur au solde");
				}
			}
			else
			{
				if(type.equals("courant")) //Test si le compte est un compte Courant
				{
					if((c1.getSolde()-montant)>-1000) //Test si le solde du compte vir� est au dessus du d�couvert autoris�
					{
						idao.effectuerVirement(montant, c1, c2);
					}
					else
					{
						throw new DecouvertNonAutorise("le decouvert n'autorise pas ce virement");
					}
				}
			
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
			
	}

	
	@Override
	public void ajouterClient(int idcon, Client c) throws LeConseillerADeja10Clients {
		
		if(idao.compterNombreClient(idcon)<10)
				{ 
		
			if(c.getTypeClient().equals("particulier") || c.getTypeClient().equals("entreprise")){ //Test si client entreprise ou particulier
		
				idao.ajouterClient(idcon, c);
				
			}
			
		} else{
			throw new LeConseillerADeja10Clients("Vous avez d�j� 10 clients.");
		}	
	
	}
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * Ajout d'un conseiller par un g�rant
	 */
	@Override
	public void ajouterConseiller(int idge, Conseiller co) 
		{
		idao.ajouterConseiller(idge, co);
			
			/*Collection<Conseiller> col = g.getConseillers(); // R�cup�re la liste des conseillers du g�rant
			col.add(co); //Ajoute le Conseiller co � la liste col
			g.setConseillers(col); // Associe la nouvelle liste des conseillers au g�rant
			co.setGerant(g); // Associe le nouveau conseiller � son g�rant
			*/
			
		}

	/**
	 * Modification d'un conseiller par un g�rant
	 */
		@Override
		public void modifierConseiller(Conseiller c, Adresse a, String telephone) {
			
			idao.modifierConseiller(c, a, telephone);
			/*c.setSonAdresse(a);
			c.setTelephone(telephone);
			*/
			
		}

	/**
	 * Suppression d'un conseiller par un g�rant
	 */
		@Override
		public void supprimerConseiller(Conseiller c, int idge) {
			
			idao.supprimerConseiller(c, idge);
		
			/*
			Collection<Conseiller> col1 = g.getConseillers(); //R�cup�re la liste des conseillers du g�rant
			col1.remove(c); //supprimer le conseiller c
			g.setConseillers(col1); //Associe la nouvelle liste au g�rant
			*/
		
			
		}
	/**
	 * Affichage d'un conseiller par un g�rant
	 */

		@Override
		public void listerConseiller(int idge) {
			idao.listerConseiller(idge);
		}
	
	
		
		public String effectuerAudit(Agence agence) {
			return idao.effectuerAudit(agence);
			/*String res = "Audit de l'Agence " + agence.getIdAgence();
		Iterator<Conseiller> itCons = agence.getGerant().getConseillers().iterator();
		while (itCons.hasNext()) {
			Iterator<Client> itClient = itCons.next().getClients().iterator();
			while (itClient.hasNext()) {
				Iterator<Compte> itCompte = itClient.next().getComptes().iterator();
				while (itCompte.hasNext()) {
					Compte eval = itCompte.next();
					if (eval.getClient() instanceof ClientParticulier) {
						if (eval.getSolde() < 5000) {
							res += "" + eval.getClient().getConseiller() + eval.getClient() + eval + eval.getSolde();
						}
						if (eval.getClient() instanceof ClientEntreprise) {
							if (eval.getSolde() < 50000) {
								res += "" + eval.getClient().getConseiller() + eval.getClient() + eval
										+ eval.getSolde();
							}

						}
					}
				}
			}
		}
		return res;
*/
	}

	
	
		
	
		
	/**
	 * Ajout d'un compte Epargne ou un Compte Courant � un client
	 * @throws CompteEpargneExistantException 
	 * @throws CompteCourantExistantException 
	 */
	private Compte creationCompte(Compte c) {

		return idao.creationCompte(c);
		// pour choisir type de client

		/*
		 * if(c.getTypeCompte().equals("epargne")) {
		 
			CompteEpargne ce = new CompteEpargne();
			return ce;
		}
		if(c.getTypeCompte().equals("courant"))
			{
			CompteCourant cc = new CompteCourant();
			return cc;
		}
		
		return null;
		*/
	}
	
	
		@Override
		public void ajouterCompteClient(Client c, Compte co) throws CompteEpargneExistantException, CompteCourantExistantException {
			
			idao.ajouterCompteClient(c, co);
			
			/*
			IConseiller cs = new Services();
			co=cs.creationCompte(co);
			
			if(co instanceof CompteEpargne){ //Test si le compte � ajouter est un Compte Epargne
				boolean b=true;
				for (Compte compte : c.getComptes())
				{
					if(compte instanceof CompteEpargne)
					{
						b=false;
					}
				}
				if(b=true){ // Test pour savoir si le client a d�j� un compte epargne
				
					c.setComptes((Collection<Compte>) co);
					System.out.println("Le compte Epargne a �t� ajout�.");
				}else{
					throw new CompteEpargneExistantException("Le client a d�j� un Compte Epargne.");
				}				
			
			}
			else{
				if(co instanceof CompteCourant){	// Dans le cas d'un ajout de Compte Courant
					boolean b2=true;
					for (Compte compte : c.getComptes())
					{
						if(compte instanceof CompteCourant)
						{
							b2=false;
						}
					}
					if(b2=true){ // Test pour savoir si le client a d�j� un compte courant
					
						c.setComptes((Collection<Compte>) co);
						System.out.println("Le compte courant a �t� ajout�.");
											
					}
					else{
						throw new CompteCourantExistantException("Le client a d�j� un Compte Courant.");
					}
				
				}
				
							
			}		
			*/	
		}

	
	
	
	/**
	 * Supprimer un client de la liste d'un conseiller
	 */

		@Override
		public void supprimerClient(Client c, int idcon) {
			idao.supprimerClient(c, idcon);
			/*
			Collection<Client> col = co.getClients(); //R�cup�ration de la liste des clients du conseiller dans la collection col
			col.remove(c);	//Suppression du client de la collection
			co.setClients(col);	//Association de la Collection mise � jour au conseiller
			*/
		}
	
	
	
	
	/**
	 * Suppression d'un compte Epargne ou compte courant
	 * @throws AbsenceDeCompteEpargneException 
	 * @throws AbsenceDeCompteCourantException 
	 */

		@Override
		public void supprimerCompteClient(Compte co, Client c) throws AbsenceDeCompteEpargneException, AbsenceDeCompteCourantException {
			idao.supprimerCompteClient(co, c);
			
			/*
			if(co instanceof CompteEpargne){ //Test si le compte � supprimer est un Compte Epargne
				boolean b=false;
				for (Compte compte : c.getComptes())
				{
					if(compte == co)
					{
						b=true;
					}
				} // Test pour savoir si le client a  bien ce compte
				if(b==true)
				{
				co = null;
				System.out.println("Le compte Epargne a �t� supprim�.");
				
				}else{
					throw new AbsenceDeCompteEpargneException("Le compte n'appartient pas au Client");
				}				
			
			}
			else{
				if(co instanceof CompteCourant){
					boolean b=false;
					for (Compte compte : c.getComptes())
					{
						if(compte == co)
						{
							b=true;
						}
					}// Dans le cas d'une suppression du Compte Courant
					if(b==true){ // Test pour savoir si le client a d�j� un compte courant
						
						
						co=null;
						System.out.println("Le compte courant a �t� supprim�.");
											
					}
					else{
						throw new AbsenceDeCompteCourantException("Le Client n'a pas ce compte courant.");
					}
				}
										
			}			
			*/
		}

	
			
	
	/**
	 * Realisation d'une simulation de cr�dit avec un montant, un taux et une dur�e de remboursement
	 * @throws MontantNegatifException 
	 */
		@Override
		public double effectuerSimulationCredit(double montant, int taux, int duree) throws MontantNegatifException {
			
			return idao.effectuerSimulationCredit(montant, taux, duree);
			/*
			double montantARembourserParMois;
			if (montant<=0) //Test si le montant entr� est inf�rieur � 0
			{
				montantARembourserParMois = 0;
				throw new MontantNegatifException("montant de remboursement negatif");
			}
			else
			{
			double montantARembourser = montant*(1+(taux/100)); //Calcul de la somme totale � rembours� (montant avec les interets)
			montantARembourserParMois = montantARembourser/duree; //Calcul des mensualit�s � rembourser par le client
			}
			return montantARembourserParMois;
		*/
		
		}

	/**
	 * la methode cr��e un placement et l'ajoute au patrimoine du client
	 * 
	 * @param patrimoine
	 *            parametre qui donne lepatrimoine du client
	 * @param typePlacement
	 *            parametre qui permet de choisir le type de placement
	 * @return retourne le placement cr�e
	 */
	public Placement creerPlacement(String typePlacement) {

		return idao.creerPlacement(typePlacement);
		/*
		// calcul de la fortune client
		Collection<Compte> col = patrimoine.getClient().getComptes();
		double fortune = 0;

		Iterator<Compte> it = col.iterator();
		while (it.hasNext())
		{
			fortune = fortune + (it.next().getSolde());
		}
		// condition de fortune pour creer un placement
		if (fortune > 500000) {

			Collection<Placement> pl = new ArrayList<Placement>();
			pl = patrimoine.getPlacements();
			// creer le placement en choisissant la bourse
			if(typePlacement.equals("Paris")) {
			
				Placement p = new Placement(patrimoine);
				pl.add(p);
				patrimoine.setPlacements(pl);
				return p;
			}

			if(typePlacement.equals("Tokyo")) {
				Placement p = new Placement(patrimoine);
				pl.add(p);
				patrimoine.setPlacements(pl);
				return p;
			}
			if(typePlacement.equals("NewYork")) {
				Placement p = new Placement(patrimoine);
				pl.add(p);
				patrimoine.setPlacements(pl);
				return p;
			}
			 

		
		}
		return null;
		*/
	}

	/**
	 * Methode pour supprimer un placement
	 * 
	 * @param placement
	 *            parametre qui donne le placement a supprimmer
	 */
	public void supprimerPlacement(Placement placement) {
		
		idao.supprimerPlacement(placement);
		
		/*

		Collection<Placement> col = placement.getPatrimoine().getPlacements();
		Collection<Placement> col2 = new ArrayList<Placement>();
		Iterator<Placement> it = col.iterator();
		while (it.hasNext())
			if (it.next().getIdPlacement() == placement.getIdPlacement()) {
				// ne fais rien quand on arrive sur le placement a supprimer
			} else {
				col2.add(it.next()); // ajoute les placements que l'on ne
										// supprimme pas dans une Collection
			}

		placement.getPatrimoine().setPlacements(col2); // met a jour la
														// Collection
														 * 
														 */

	}
	
	

		
	/**
	 * Ajout d'une carte � un compte
	 */

		@Override
		public void activationCarteVisa(Compte c, CarteBancaire cv) {
			
			idao.activationCarteVisa(c, cv);
			/*
				c.setCarteBancaire(cv);
				System.out.println("La carte " + cv +" a �t� activ�e pour le compte " + c);
				*/
		}

	/**
	 *Supprimer la carte de son compte 
	 */

		@Override
		public void desactivationCarteVisa(Compte c, CarteBancaire cv) {
			idao.desactivationCarteVisa(c, cv);
			
			/*
				c.setCarteBancaire(null);
				System.out.println("La carte " + cv +" a �t� supprim�e de compte " + c);
				*/
		}

	@Override
	public Compte recuperationCompte(int idcompte) throws SQLException {
		// TODO Auto-generated method stub
		return idao.recuperationCompte(idcompte);
	}



	

	



}
