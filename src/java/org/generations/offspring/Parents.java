/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generations.offspring;

import org.generations.population.Specimen;

/**
 * Contains parents.
 * @author imorlowska@gmail.com
 */
public class Parents {
    public Specimen mother;
    public Specimen father;
    
    public Parents(Specimen mother, Specimen father) {
        this.mother = mother;
        this.father = father;
    }
    
    public Specimen getMother() {
        return mother;
    }
    
    public Specimen getFather() {
        return father;
    }
}
