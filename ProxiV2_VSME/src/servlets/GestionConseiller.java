package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import metier.Adresse;
import metier.Client;
import metier.ClientEntreprise;
import metier.ClientParticulier;
import metier.Compte;
import service.IConseiller;
import service.Services;
import service.exception.DecouvertNonAutorise;
import service.exception.LeConseillerADeja10Clients;
import service.exception.MontantNegatifException;
import service.exception.MontantSuperieurAuSoldeException;

/**
 * Servlet implementation class GestionClients
 */
@WebServlet("/GestionConseiller")
public class GestionConseiller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionConseiller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");

		/**
		 * contr�le authentification par session
		 */
		// si pas de session, on en initialise une toute neuve avec deux
		// attributs
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("login") == null || session.getAttribute("attemptsCount") == null) {
			session = request.getSession();
			session.setAttribute("login", "visiteur");
			session.setAttribute("attemptsCount", 0);
		} else {
			// si déjà une session, on augmente le compteur de fail (si appui
			// sur bouton valider de authenticate.jsp)
			if (request.getParameter("validauthenticate") != null) {
				session.setAttribute("attemptsCount", ((int) session.getAttribute("attemptsCount")) + 1);
			}
		}

		// si session n'est pas un conseiller, on verifie s'il y a un login
		if (!session.getAttribute("login").equals("Conseiller")) {
			if (request.getParameter("id") != null && request.getParameter("pwd") != null) {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				IConseiller ic = new Services();
				int idCons = ic.authentificationConseiller(id, pwd);
				if (idCons != 0) {
					session.setAttribute("idConseiller", idCons);
					session.setAttribute("login", "Conseiller");
					session.setAttribute("attemptsCount", 0);
				} else {
					request.getRequestDispatcher("/Authenticate.jsp").forward(request, response);
				}
			} else {
				request.getRequestDispatcher("/Authenticate.jsp").forward(request, response);
			}
		}

		/**
		 * déconnection
		 */
		if (request.getParameter("action").equals("Deconnection")) {
			session.invalidate();
			request.getRequestDispatcher("/Authenticate.jsp").forward(request, response);
		}

		/**
		 * listes clients pour l'interface conseiller
		 */
		IConseiller ic1 = new Services();
		Collection<Client> colcli1 = ic1
				.listerClient(Integer.parseInt(session.getAttribute("idConseiller").toString()));
		request.setAttribute("listeclients", colcli1);

		/**
		 * Ajouter un client
		 */
		if (request.getParameter("ajouterclient") != null || request.getParameter("validerajouterclient") != null) {

			// si appui sur le bouton de validation formulaire
			if (request.getParameter("validerajouterclient") != null) {

				// initialisation param�tre de controle saisie
				boolean validform = true;

				// v�rification formulaire
				if (request.getParameter("nom") == null || request.getParameter("nom").equals("")) {
					validform = false;
				}
				if (request.getParameter("prenom") == null || request.getParameter("prenom").equals("")) {
					validform = false;
				}
				if (request.getParameter("email") == null || request.getParameter("email").equals("")) {
					validform = false;
				}
				if (request.getParameter("telephone") == null || request.getParameter("telephone").equals("")) {
					validform = false;
				}
				if (request.getParameter("adresse") == null || request.getParameter("adresse").equals("")) {
					validform = false;
				}
				if (request.getParameter("codepostal") == null || request.getParameter("codepostal").equals("")) {
					validform = false;
				}
				if (request.getParameter("ville") == null || request.getParameter("ville").equals("")) {
					validform = false;
				}
				if (request.getParameter("typeclient") == null
						|| (!request.getParameter("typeclient").equals("particulier")
								&& !request.getParameter("typeclient").equals("entreprise"))) {
					validform = false;
				}

				// si une erreur de formulaire, on renvoie sur le formulaire
				if (validform == false) {
					request.setAttribute("validerajouterclientdefaut", "pb");
					request.getRequestDispatcher("/AjouterClient.jsp").forward(request, response);
				} else {
					// sinon en envoie le tout en base de donn�es
					if (request.getParameter("typeclient").equals("particulier")) {
						ClientParticulier c = new ClientParticulier();
						c.setNom(request.getParameter("nom"));
						c.setPrenom(request.getParameter("prenom"));
						c.setEmail(request.getParameter("email"));
						c.setTelephone(request.getParameter("telephone"));
						Adresse adr = new Adresse();
						adr.setAdresse(request.getParameter("adresse"));
						adr.setCodePostale((Integer.parseInt(request.getParameter("codepostal"))));
						adr.setVille(request.getParameter("ville"));
						c.setSonAdresse(adr);
						c.setTypeClient(request.getParameter("typeclient"));
						IConseiller ic = new Services();
						try {
							ic.ajouterClient(Integer.parseInt(session.getAttribute("idConseiller").toString()), c);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (LeConseillerADeja10Clients e) {
							// TODO Auto-generated catch block
							request.setAttribute("resultatvalidation", "vous avez atteint votre maximum de clients");
							request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
						}
					} else if (request.getParameter("typeclient").equals("entreprise")) {
						ClientEntreprise c = new ClientEntreprise();
						c.setNom(request.getParameter("nom"));
						c.setPrenom(request.getParameter("prenom"));
						c.setEmail(request.getParameter("email"));
						c.setTelephone(request.getParameter("telephone"));
						Adresse adr = new Adresse();
						adr.setAdresse(request.getParameter("adresse"));
						adr.setCodePostale((Integer.parseInt(request.getParameter("codepostal"))));
						adr.setVille(request.getParameter("ville"));
						c.setSonAdresse(adr);
						c.setTypeClient(request.getParameter("typeclient"));
						IConseiller ic = new Services();
						try {
							ic.ajouterClient(Integer.parseInt(session.getAttribute("idConseiller").toString()), c);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (LeConseillerADeja10Clients e) {
							// TODO Auto-generated catch block
							request.setAttribute("resultatvalidation", "vous avez atteint votre maximum de clients");
							request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
						}
					}
					request.setAttribute("resultatvalidation", "Nouveau client ajouté");
					request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
				}
			}
			// par défaut, on envoie sur le formulaire
			request.getRequestDispatcher("/AjouterClient.jsp").forward(request, response);
		}

		/**
		 * Modifier un client
		 */
		// si appui sur le bouton de validation formulaire
		if (request.getParameter("action").equals("fromModifierclient")) {

			// initialisation parametre de controle saisie
			boolean validform = true;
			// vérification formulaire

			if (request.getParameter("nom") == null || request.getParameter("nom").equals("")) {
				validform = false;
				request.setAttribute("validerModifierclientdefaut", "nom");
			}
			if (request.getParameter("prenom") == null || request.getParameter("prenom").equals("")) {
				validform = false;
				request.setAttribute("validerModifierclientdefaut", "prenom");
			}
			if (request.getParameter("email") == null || request.getParameter("email").equals("")) {
				validform = false;
				request.setAttribute("validerModifierclientdefaut", "email");
			}
			if (request.getParameter("telephone") == null || request.getParameter("telephone").equals("")) {
				validform = false;
				request.setAttribute("validerModifierclientdefaut", "telephone");
			}
			if (request.getParameter("adresse") == null || request.getParameter("adresse").equals("")) {
				validform = false;
				request.setAttribute("validerModifierclientdefaut", "adresse");
			}
			if (request.getParameter("codepostal") == null || request.getParameter("codepostal").equals("")) {
				validform = false;
				request.setAttribute("validerModifierclientdefaut", "code postal");
			}
			if (request.getParameter("ville") == null || request.getParameter("ville").equals("")) {
				validform = false;
				request.setAttribute("validerModifierclientdefaut", "ville");
			}
			if (request.getParameter("idclientform") == null || request.getParameter("idclientform").equals("")) {
				validform = false;
				request.setAttribute("validerModifierclientdefaut", "id");
			}
			// si une erreur de formulaire, on
			// renvoie sur le formulaire avec client
			if (validform == false) {
				IConseiller ic = new Services();
				int idclientform = Integer.parseInt(request.getParameter("idclientform").toString());
				Collection<Client> colcli = ic
						.listerClient(Integer.parseInt(session.getAttribute("idConseiller").toString()));
				for (Client client : colcli) {
					if (client.getIdClient() == idclientform) {
						request.setAttribute("client", client);
						request.setAttribute("clientadresse", client.getSonAdresse());
					}
				}
				request.getRequestDispatcher("/ModifierClient.jsp").forward(request, response);
			} else {

				// sinon on envoie le tout en BDD
				IConseiller ic = new Services();
				int idcli = Integer.parseInt(request.getParameter("idclientform").toString());
				Collection<Client> colcli = ic
						.listerClient(Integer.parseInt(session.getAttribute("idConseiller").toString()));
				for (Client client : colcli) {
					if (client.getIdClient() == idcli) {
						Adresse adr = new Adresse();
						adr.setAdresse(request.getParameter("adresse"));
						adr.setCodePostale((Integer.parseInt(request.getParameter("codepostal"))));
						adr.setVille(request.getParameter("ville"));
						ic.modifierClient(client, request.getParameter("nom"), request.getParameter("prenom"), adr,
								request.getParameter("email"));
					}
				}
				request.setAttribute("resultatvalidation", "client modifié");
				request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
			}
		}

		if (request.getParameter("toModifierClient") != null) {

			if (request.getParameter("idclientform") != null) {
				// par d�faut, on envoie sur le formulaire avec le client
				IConseiller ic = new Services();
				int idclientform = Integer.parseInt(request.getParameter("idclientform").toString());
				Collection<Client> colcli = ic
						.listerClient(Integer.parseInt(session.getAttribute("idConseiller").toString()));
				for (Client client : colcli) {
					if (client.getIdClient() == idclientform) {
						request.setAttribute("client", client);
						request.setAttribute("clientadresse", client.getSonAdresse());
					}
				}
				request.getRequestDispatcher("/ModifierClient.jsp").forward(request, response);
			}
			request.setAttribute("resultatvalidation", "veuillez choisir un client dans la liste");
			request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
		}

		/**
		 * voir comptes client
		 */
		if (request.getParameter("voircomptesclient") != null) {
			if (request.getParameter("idclientform") != null) {

				int idcli = Integer.parseInt(request.getParameter("idclientform").toString());
				for (Client client : colcli1) {
					if (client.getIdClient() == idcli) {
						Collection<Compte> colcom = ic1.listerCompteClient(client);
						request.setAttribute("listecomptesclient", colcom);
						request.getRequestDispatcher("/listecomptesclient.jsp").forward(request, response);
					}
				}
			}
			request.setAttribute("resultatvalidation", "veuillez choisir un client dans la liste");
			request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
		}

		/**
		 * Effectuer un virement
		 */
		// selection des clients
		if (request.getParameter("toEffectuervirementphase1") != null) {
			request.setAttribute("phase", "1");
			request.removeAttribute("toEffectuervirementphase1");
			request.getRequestDispatcher("/virementcompteacompte.jsp").forward(request, response);
		}
		// sélection des comptes
		if (request.getParameter("action").equals("fromEffectuervirementphase1")) {
			if (request.getParameter("motcle1") != "" && request.getParameter("motcle2") != "") {
				Collection<Client> colClliDeb = (Collection<Client>) ic1.listerClient(request.getParameter("motcle1"));
				Collection<Client> colClliCred = (Collection<Client>) ic1.listerClient(request.getParameter("motcle2"));
				Collection<Compte> colCompteDEb = new ArrayList<Compte>();
				Collection<Compte> colCompteCred = new ArrayList<Compte>();

				for (Client client1 : colClliDeb) {
					colCompteDEb.addAll((Collection<Compte>) ic1.listerCompteClient(client1));
				}
				for (Client client2 : colClliCred) {
					colCompteCred.addAll((Collection<Compte>) ic1.listerCompteClient(client2));
				}

				request.setAttribute("listecomptesclientAdebiter", colCompteDEb);
				request.setAttribute("listecomptesclientAcrediter", colCompteCred);
				request.setAttribute("phase", "2");
				request.getRequestDispatcher("/virementcompteacompte.jsp").forward(request, response);
			}
			request.setAttribute("resultatvalidation", "veuillez chercher un client");
			request.setAttribute("phase", "1");
			request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
		}
		// sélection du montant
		if (request.getParameter("action").equals("fromEffectuervirementphase2")) {
			if (request.getParameter("idcompte1form") != null && request.getParameter("idcompte2form") != null) {

				request.setAttribute("idcompte1form", request.getParameter("idcompte1form"));
				request.setAttribute("idcompte2form", request.getParameter("idcompte2form"));
				request.setAttribute("phase", "3");
				request.getRequestDispatcher("/virementcompteacompte.jsp").forward(request, response);
			}
			request.setAttribute("resultatvalidation", "Veuillez choisir un compte dans la liste");
			request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
		}
		// maintenent qu'on a toutes les infos,(comptes et montants) on passe au virement
		if (request.getParameter("action").equals("fromEffectuervirementphase3")) {
			if (request.getParameter("montant") != null && request.getParameter("idcompte1form") != null
					&& request.getParameter("idcompte2form") != null) {

				int montant = Integer.parseInt(request.getParameter("montant"));
				System.out.println(montant);
				Compte c1 = null;
				try {
					c1 = ic1.recuperationCompte(Integer.parseInt(request.getParameter("idcompte1form")));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
				Compte c2 = null;
				try {
					c2 = ic1.recuperationCompte(Integer.parseInt(request.getParameter("idcompte2form")));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ic1.effectuerVirement(montant, c1, c2);
				} catch (MontantNegatifException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MontantSuperieurAuSoldeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DecouvertNonAutorise e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("resultatvalidation", "virement effectué");
				request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
			}
			request.setAttribute("resultatvalidation", "Veuillez choisir un montant");
			request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
		}
		// page par défaut si connecté en conseiller
		request.getRequestDispatcher("/interfaceConseiller.jsp").forward(request, response);
	}

}
