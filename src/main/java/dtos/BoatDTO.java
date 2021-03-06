/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Boat;
import entities.Harbour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Mathias
 */

@Data
@Builder
@AllArgsConstructor
public class BoatDTO {
    private String boatname;
    private String boatmake;
    private String boatmodel;

    public BoatDTO(Boat boat) {
        this.boatname = boat.getName();
        this.boatmake = boat.getMake();
        this.boatmodel = boat.getModel();
    }
}