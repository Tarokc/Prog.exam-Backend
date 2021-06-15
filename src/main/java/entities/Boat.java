/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mathias
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "boats")
public class Boat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String make;
    private String name;
    
    @ManyToMany
    @JoinColumn(name = "owner_id")
    private List<Owner> owners;
    
    private int harbour_id;
    
    public Boat(String brand, String make, String name, int harbour_id) {
        this.brand = brand;
        this.make = make;
        this.name = name;
        owners = new ArrayList();
        this.harbour_id = harbour_id;
    }
}
