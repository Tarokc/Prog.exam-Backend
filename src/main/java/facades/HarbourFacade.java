/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.BoatDTO;
import dtos.HarbourDTO;
import entities.Boat;
import entities.Harbour;
import entities.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mathias
 */
public class HarbourFacade {
    
    private static EntityManagerFactory emf;
    private static HarbourFacade instance;

    private HarbourFacade() {
    }
    
    public static HarbourFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HarbourFacade();
        }
        return instance;
    }
        
    
    public Harbour _create(String name, String address, int capacity) {
       EntityManager em = emf.createEntityManager();
       try {
           Harbour harbour = new Harbour();
           harbour.setName(name);
           harbour.setAddress(address);
           harbour.setCapacity(capacity);
           
           em.getTransaction().begin();
           em.persist(harbour);
           em.getTransaction().commit();
           
           return harbour;
       }
       finally {
           em.close();
       }
    }
    
    public List<HarbourDTO> getAllHarbours() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Harbour> q = em.createQuery("SELECT h FROM Harbour h", Harbour.class);
            return q.getResultList().stream().map(HarbourDTO::new).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
    
    public int getHarbourId(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Harbour> q = em.createQuery("SELECT h FROM Harbour h WHERE h.name = :name", Harbour.class);
            q.setParameter("name", name);
            return q.getSingleResult().getId().intValue();
        }
        catch (NoResultException e) {
            e.getMessage();
        }
        finally {
            em.close();
        }
        return -1;
    }
}
