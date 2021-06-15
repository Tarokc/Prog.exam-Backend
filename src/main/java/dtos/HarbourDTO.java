/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Harbour;

/**
 *
 * @author Mathias
 */
public class HarbourDTO {
    
    private String name;
    private String address;
    private int capacity;

    public HarbourDTO(Harbour harbour) {
        this.name = harbour.getName();
        this.address = harbour.getAddress();
        this.capacity = harbour.getCapacity();
    }
}
