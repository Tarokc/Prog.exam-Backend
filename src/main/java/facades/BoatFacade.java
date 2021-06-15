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
    
    public Boat _create(String name, String make, String brand, List<Owner> owners, int harbour_id) {
        EntityManager em = emf.createEntityManager();
        try {
         Boat boat = new Boat();
         boat.setName(name);
         boat.setBrand(brand);
         boat.setMake(make);
         boat.setOwners(owners);
         boat.setHarbour_id(harbour_id);
         
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
            boat.setBrand(boatDTO.getBoatbrand());
            
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
    
    public List<BoatDTO> getBoatsInHarbour(int harbord_id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> q = em.createQuery("SELECT b FROM Boat b WHERE b.harbour_id = :harbour_id", Boat.class);
            q.setParameter("harbour_id", harbord_id);
            return q.getResultList().stream().map(BoatDTO::new).collect(Collectors.toList());
        }
        finally {
            em.close();
        }
    }
}
