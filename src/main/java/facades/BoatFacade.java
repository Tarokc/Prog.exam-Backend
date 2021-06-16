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
import entities.Owner;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mathias
 */
public class BoatFacade {
    
    private static EntityManagerFactory emf;
    private static BoatFacade instance;
    
    private BoatFacade() {
    }
    
    public static BoatFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }
    
    public Boat _create(BoatDTO boatDTO) {
        EntityManager em = emf.createEntityManager();
        try {
         Boat boat = new Boat();
         boat.setName(boatDTO.getBoatname());
         boat.setModel(boatDTO.getBoatmodel());
         boat.setMake(boatDTO.getBoatmake());
         
         em.getTransaction().begin();
         em.persist(boat);
         em.getTransaction().commit();
         
         return boat;
            
        }
        finally {
            em.close();
        }
    }
    
    public List<BoatDTO> getAllBoats() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> q = em.createQuery("SELECT b FROM Boat b", Boat.class);
            return q.getResultList().stream().map(BoatDTO::new).collect(Collectors.toList());
        }
        finally {
            em.close();
        }
    }
    
    public BoatDTO register(BoatDTO boatDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Boat boat = new Boat();
            boat.setName(boatDTO.getBoatname());
            boat.setMake(boatDTO.getBoatmake());
            boat.setModel(boatDTO.getBoatmodel());
            
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
            return boatDTO;
        }
        finally {
            em.close();
        }
    }
    
    public void removeBoat(String boatname) {
        EntityManager em = emf.createEntityManager();
        try {
            Boat boat = em.find(Boat.class, boatname);
            
            em.getTransaction().begin();
            em.remove(boat);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }
    
    public List<BoatDTO> getBoatsInHarbour(int harbour_id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> q = em.createQuery("SELECT b FROM Boat b WHERE b.harbour_id = :harbour_id", Boat.class);
            q.setParameter("harbour_id", harbour_id);
            return q.getResultList().stream().map(BoatDTO::new).collect(Collectors.toList());
        }
        catch (NoResultException e) {
            e.getMessage();
        }
        finally {
            em.close();
        }
        return null;
    }
    
    public BoatDTO connectHarbour(BoatDTO boatDTO, int harbour_id) {
        EntityManager em = emf.createEntityManager();

        try {
            Query q = em.createQuery("SELECT b FROM Boat b WHERE b.name = :name", Boat.class);
            q.setParameter("name", boatDTO.getBoatname());
            Boat boat = (Boat)q.getSingleResult();
            boat.setHarbour_id(harbour_id);
            
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
            return new BoatDTO(boat);
        }
        finally {
            em.close();
        }
    }
    
    public BoatDTO setOwner(BoatDTO boatDTO, int owner_id) {
        EntityManager em = emf.createEntityManager();

        try {
            Query q = em.createQuery("SELECT b FROM Boat b WHERE b.name = :name", Boat.class);
            q.setParameter("name", boatDTO.getBoatname());
            Boat boat = (Boat)q.getSingleResult();
            boat.setOwner_id(owner_id);
            
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
            return new BoatDTO(boat);
        }
        finally {
            em.close();
        }
    }
    
    public List<Integer> getOwnerByBoatName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT b.owner_id FROM Boat b WHERE b.name = :name");
            q.setParameter("name", name);
            return q.getResultList();
        }
        finally {
            em.close();
        }
    }
    
    public List<BoatDTO> getBoatsWithoutHarbour() {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT b FROM Boat b WHERE b.owner_id = 0");
            return q.getResultList();
        }
        finally {
            em.close();
        }
    }
    
    public BoatDTO getBoat(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> q = em.createQuery("SELECT b FROm Boat b WHERE b.name = :name", Boat.class);
            q.setParameter("name", name);
            return new BoatDTO(q.getSingleResult());
        }
        finally {
            em.close();
        }
    }
}
