package metier;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends Personne {
	@OneToMany(mappedBy="client")
	private Collection<Compte> comptes = new ArrayList<Compte>();
	@Transient
	private Placement placement;
	@Transient
	private Collection<Credit> credits = new ArrayList<Credit>();
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Conseiller conseiller;
	private String typeClient; // particulier si client Particulier et
								// entreprise si client Entreprise
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idClient;
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getTypeClient() {
		return typeClient;
	}

	public void setTypeClient(String typeClient) {
		this.typeClient = typeClient;
	}

	/**
	 * Constructeur de Client auquel est associ� � la classe Personne et son
	 * adresse
	 * 
	 * @param nom
	 *            Correspond au nom du client
	 * @param prenom
	 *            Correspond au prenom du client
	 * @param telephone
	 *            Correspond au t�l�phone de la client
	 * @param id
	 *            Correspond au num�ro d'identification du client
	 * @param sonAdresse
	 *            Correspond � l'adresse du client
	 */

	public Client(String nom, String prenom, String telephone, int id, Adresse sonAdresse, String typeClient) {
		super(nom, prenom, telephone, id, sonAdresse);
		// TODO Auto-generated constructor stub
	}

	public Client(String nom, String prenom, String telephone, int id, Adresse sonAdresse) {
		super(nom, prenom, telephone, id, sonAdresse);
		// TODO Auto-generated constructor stub
	}

	public Collection<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(Collection<Compte> comptes) {
		this.comptes = comptes;
	}

	public Collection<Credit> getCredits() {
		return credits;
	}

	public void setCredits(Collection<Credit> credits) {
		this.credits = credits;
	}

	public Client(Collection<Compte> comptes, Placement placement, Conseiller conseiller) {
		super();
		this.comptes = comptes;
		this.placement = placement;
		this.conseiller = conseiller;
	}

	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}

	public Collection<Credit> getcredits() {
		return credits;
	}

	public void setcredits(Collection<Credit> credits) {
		this.credits = credits;
	}

	public Conseiller getConseiller() {
		return conseiller;
	}

	public void setConseiller(Conseiller conseiller) {
		this.conseiller = conseiller;
	}

	public Client() {
		super();
	}

}
