package no.hvl.dat107.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;

public class AnsattDAO {
    
    private EntityManagerFactory emf;
    
    public AnsattDAO() {
        emf = Persistence.createEntityManagerFactory("firmaPU");
    }
    
    public Ansatt finnAnsattMedId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Ansatt.class, id);
        } finally {
            em.close();
        }
    }
    
    public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Ansatt> query = em.createQuery(
                    "SELECT a FROM Ansatt a WHERE a.brukernavn = :brukernavn", Ansatt.class);
            query.setParameter("brukernavn", brukernavn);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<Ansatt> finnAlleAnsatte() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a ORDER BY a.ansatt_id", Ansatt.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void oppdaterStillingOgLonn(int ansattId, String nyStilling, BigDecimal nyLonn) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Ansatt a = em.find(Ansatt.class, ansattId);
            if (a != null) {
                if (nyStilling != null && !nyStilling.isBlank()) {
                    a.setStilling(nyStilling);
                }
                if (nyLonn != null) {
                    a.setManedslonn(nyLonn);
                }
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
    
    public void byttAvdeling(int ansattId, int nyAvdelingId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Ansatt a = em.find(Ansatt.class, ansattId);
            Avdeling nyAvdeling = em.find(Avdeling.class, nyAvdelingId);
            
            if (a != null && nyAvdeling != null) {
                // Sjekker om ansatt er sjef i sin nåværende avdeling
                if (a.erSjef()) {
                    throw new Exception("Kan ikke bytte avdeling for ansatt som er sjef!");
                }
                a.setAvdeling(nyAvdeling);
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
    
    public void leggTilAnsatt(Ansatt nyAnsatt, int avdelingId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Avdeling avdeling = em.find(Avdeling.class, avdelingId);
            if (avdeling != null) {
                nyAnsatt.setAvdeling(avdeling);
                em.persist(nyAnsatt);
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
    
    public void slettAnsatt(int ansattId) throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Ansatt a = em.find(Ansatt.class, ansattId);
            if (a != null) {
                // Sjekker om ansatt er sjef
                if (a.erSjef()) {
                    throw new Exception("Kan ikke slette en ansatt som er sjef!");
                }
                
                // Sjekker om ansatt har prosjektdeltagelser
                if (a.getProsjektdeltagelser() != null && !a.getProsjektdeltagelser().isEmpty()) {
                    throw new Exception("Kan ikke slette en ansatt som har registrerte timer i prosjekter!");
                }
                
                em.remove(a);
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}

