package no.hvl.dat107;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main1_FindById {
	
	private static EntityManagerFactory emf;
	static {
		emf = Persistence.createEntityManagerFactory("brukernavnPersistenceUnit");
	}
	
	/* ------------------------------------------------------------------- */

    public static void main(String[] args) {
    	
        Ansatt a = finnBrukernavnMedId("SM");
        System.out.println(a);
    }

    private static Ansatt finnBrukernavnMedId(String Brukernavn) {

		System.out.println("Kobler til database...");
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Ansatt.class, Brukernavn);
        } finally {
            em.close();
        }
    }
    
}
