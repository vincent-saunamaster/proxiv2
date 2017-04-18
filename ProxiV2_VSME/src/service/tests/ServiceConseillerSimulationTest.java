package service.tests;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import service.IConseiller;
import service.Services;
import service.exception.MontantNegatifException;

public class ServiceConseillerSimulationTest {

	// Test d'une simulation d'un cr�dit
	@Ignore
	@Test
	public void testEffectuerSimulationCredit() throws MontantNegatifException {
		IConseiller sc = new Services(); //Cr�ation d'un service
		double montant=200000;	//Instanciation du montant du cr�dit
		int taux=3; //Instanciation du taux du cr�dit
		int duree=24;//dur�e du cr�dit en mois
		
		double montantARembourser = 200000*(1+(3/100)); // calcul du montant � rembourser en totalit�
		double montantARembourserParMois = montantARembourser/duree; //Calcul des mensualit�s � rembourser
		
		double m1=sc.effectuerSimulationCredit(montant, taux, duree); //Appel � la m�thode de simulation 
		Assert.assertEquals(true, (m1==montantARembourserParMois)); //V�rifier la m�thode avec les calculs dans le test
		
	}
	
	@Ignore
	@Test//cas montant negatif
	public void testEffectuerSimulationCredit2() throws MontantNegatifException {
		IConseiller  sc = new Services();
		double montant=-200;
		int taux=3;
		int duree=24;//mois
		
		double montantARembourser = 0;
		double montantARembourserParMois = 0;
		
		double m1=sc.effectuerSimulationCredit(montant, taux, duree);
		
		Assert.assertEquals(true, (m1==montantARembourserParMois)); //V�rifier si la m�thode renvoi une valeur null identique au valeur du test
		
	}

}
