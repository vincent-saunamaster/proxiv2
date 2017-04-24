package metier;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Personne {
	private String nom;
	private String prenom;
	private String telephone;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_PERSONNE")
	private int id;
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Adresse sonAdresse;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	

	/**
	 * Constructeur de Personne sans argument
	 */
	public Personne() {
		super();
	}
	/**
	 * Constructeur de Personne avec 5 arguments
	 * @param nom Nom de la personne
	 * @param prenom Prenom de la personne
	 * @param telephone Telephone de la personne
	 * @param id Num�ro d'identifiant de la personne
	 * @param sonAdresse Adresse de la personne
	 */
	public Personne(String nom, String prenom, String telephone, int id, Adresse sonAdresse) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.id = id;
		this.setSonAdresse(sonAdresse);
	}

	public Adresse getSonAdresse() {
		return sonAdresse;
	}

	public void setSonAdresse(Adresse sonAdresse) {
		this.sonAdresse = sonAdresse;
	}

	
}
