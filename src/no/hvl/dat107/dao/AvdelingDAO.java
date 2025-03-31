package no.hvl.dat107.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;

public class AvdelingDAO {
    
    private EntityManagerFactory emf;
    
    public AvdelingDAO() {
        emf = Persistence.createEntityManagerFactory("firmaPU");
    }
    
    public Avdeling finnAvdelingMedId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Avdeling.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Avdeling> finnAlleAvdelinger() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Avdeling> query = em.createQuery("SELECT a FROM Avdeling a ORDER BY a.avdeling_id", Avdeling.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void leggTilAvdeling(String navn, int sjefId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Ansatt sjef = em.find(Ansatt.class, sjefId);
            if (sjef == null) {
                throw new Exception("Finner ikke ansatt med ID: " + sjefId);
            }
            
            Avdeling nyAvdeling = new Avdeling(navn);
            nyAvdeling.setSjef(sjef);
            em.persist(nyAvdeling);
            
            // Flytt sjefen til den nye avdelingen
            sjef.setAvdeling(nyAvdeling);
            
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }
    
    public void slettAvdeling(int avdelingId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Avdeling a = em.find(Avdeling.class, avdelingId);
            if (a != null) {
                // Sjekk om avdelingen har ansatte
                if (a.getAnsatte() != null && !a.getAnsatte().isEmpty()) {
                    throw new Exception("Kan ikke slette en avdeling som har ansatte!");
                }
                
                em.remove(a);
            }
            
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }
}