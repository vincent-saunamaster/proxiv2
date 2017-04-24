package metier;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/** 
 * M�tier Adresse
 * Correspond aux adresses de chaque personnes (Client, Conseiller, G�rant, Agence)
 * @author Vincent,Marine
 *
 */
@Entity
public class Adresse {

	private String adresse;
	private int codePostale;
	private String ville;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idAdresse;
	
	public int getIdAdresse() {
		return idAdresse;
	}
	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}

	@OneToMany(mappedBy="sonAdresse")
	private Collection <Personne> lesPersonnes  = new ArrayList<Personne>();
	@Transient
	private Agence monAgence;
	
	
	
	public Collection<Personne> getLesPersonnes() {
		return lesPersonnes;
	}
	public void setLesPersonnes(Collection<Personne> lesPersonnes) {
		this.lesPersonnes = lesPersonnes;
	}
	public Agence getMonAgence() {
		return monAgence;
	}
	public void setMonAgence(Agence monAgence) {
		this.monAgence = monAgence;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getCodePostale() {
		return codePostale;
	}
	public void setCodePostale(int codePostale) {
		this.codePostale = codePostale;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}


	/**
	 * Constructeur � 3 arguments de l'Adresse
	 * @param adresse correspond au num�ro et  nom de rue
	 * @param codePostale Correspond au Code postale de la ville
	 * @param ville Correspond au nom de la ville
	 */
	public Adresse(String adresse, int codePostale, String ville) {
		super();
		this.adresse = adresse;
		this.codePostale = codePostale;
		this.ville = ville;
	}
	
	/**
	 * Constructeur de l'Adresse sans argument
	 */
	public Adresse() {
		super();
	}
	
	//Affichage de la Classe Adresse
	@Override
	public String toString() {
		return "Adresse [adresse=" + adresse + ", codePostale=" + codePostale + ", ville=" + ville + "]";
	}
	
	
	
}
