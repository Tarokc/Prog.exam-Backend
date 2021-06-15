/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.OwnerDTO;
import entities.Harbour;
import entities.Owner;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mathias
 */
public class OwnerFacade {
    
    private static EntityManagerFactory emf;
    private static OwnerFacade instance;

    public OwnerFacade() {
    }
    
    public static OwnerFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }
    
    public Owner _create(String name, String address, String phone) {
        EntityManager em = emf.createEntityManager();
        try {
            Owner owner = new Owner();
            owner.setName(name);
            owner.setAddress(address);
            owner.setPhone(phone);
            
            em.getTransaction().begin();
            em.persist(owner);
            em.getTransaction().commit();
            return owner;
        }
        finally {
            em.close();
        }
    }
    
    public List<OwnerDTO> getAllOwners() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Owner> q = em.createQuery("SELECT o FROM Owner o", Owner.class);
            return q.getResultList().stream().map(OwnerDTO::new).collect(Collectors.toList());
        }
        finally {
            em.close();
        }
    }        
    
    
    public Owner getOwnerByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Owner> q = em.createQuery("SELECT o FROM Owner o WHERE o.name = :name", Owner.class);
            q.setParameter("name", name);
            return q.getSingleResult();
        }
        finally {
            em.close();
        }
    }
}
