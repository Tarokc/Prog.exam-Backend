/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Owner;
import entities.User;
import java.util.List;

/**
 *
 * @author Mathias
 */
public class OwnerDTO {
    private String name;
    private String phone;

    
    public OwnerDTO(Owner owner) {
        this.name = owner.getName();
        this.phone = owner.getPhone();
    }
}
