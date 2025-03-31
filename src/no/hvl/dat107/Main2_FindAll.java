package no.hvl.dat107;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Main2_FindAll {
	
	private static EntityManagerFactory emf;
	static {
		emf = Persistence.createEntityManagerFactory("brukernavnPersistenceUnit");
	}
	
	/* ------------------------------------------------------------------- */

	public static void main(String[] args) {

		String jpql = "SELECT b FROM Ansatt b"; //NB! Dette er ikke SQL, men JPQL !!!
		List<Ansatt> ansatte = null;
		
		System.out.println("Kobler til database...");
		EntityManager em = emf.createEntityManager();
		
		try {
	        TypedQuery<Ansatt> query = em.createQuery(jpql, Ansatt.class);
	        ansatte = query.getResultList();
		} finally {
	        em.close();
		}
		
		System.out.println("Resultat:");
		for (Ansatt p : ansatte) {
			System.out.println(p);
		}
		System.out.println("Ferdig!");
	}
}
