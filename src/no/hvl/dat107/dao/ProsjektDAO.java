package no.hvl.dat107.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.entity.Prosjektdeltagelse;

public class ProsjektDAO {
    
    private EntityManagerFactory emf;
    
    public ProsjektDAO() {
        emf = Persistence.createEntityManagerFactory("firmaPU");
    }
    
    public Prosjekt finnProsjektMedId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Prosjekt.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Prosjekt> finnAlleProsjekter() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Prosjekt> query = em.createQuery("SELECT p FROM Prosjekt p ORDER BY p.prosjekt_id", Prosjekt.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void leggTilProsjekt(String navn, String beskrivelse) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Prosjekt p = new Prosjekt(navn, beskrivelse);
            em.persist(p);
            
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
    
    public void registrerProsjektdeltagelse(int ansattId, int prosjektId, String rolle) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Ansatt a = em.find(Ansatt.class, ansattId);
            Prosjekt p = em.find(Prosjekt.class, prosjektId);
            
            if (a != null && p != null) {
                Prosjektdeltagelse pd = new Prosjektdeltagelse(a, p, rolle, 0);
                em.persist(pd);
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
    
    public void registrerTimer(int ansattId, int prosjektId, int timer) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            // Vi må finne riktig prosjektdeltagelse
            TypedQuery<Prosjektdeltagelse> query = em.createQuery(
                    "SELECT pd FROM Prosjektdeltagelse pd " +
                    "WHERE pd.ansatt.ansatt_id = :ansattId " +
                    "AND pd.prosjekt.prosjekt_id = :prosjektId", 
                    Prosjektdeltagelse.class);
            
            query.setParameter("ansattId", ansattId);
            query.setParameter("prosjektId", prosjektId);
            
            Prosjektdeltagelse pd = query.getSingleResult();
            if (pd != null) {
                pd.leggTilTimer(timer);
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
    
    public void slettProsjekt(int prosjektId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Prosjekt p = em.find(Prosjekt.class, prosjektId);
            if (p != null) {
                // Sjekk om prosjektet har deltagere
                if (p.getDeltagelser() != null && !p.getDeltagelser().isEmpty()) {
                    throw new Exception("Kan ikke slette et prosjekt som har deltagere!");
                }
                
                em.remove(p);
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