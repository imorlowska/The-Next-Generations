/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generations.offspring;

import org.generations.population.Specimen;

/**
 * Family container: parents and child.
 * @author Izabela
 */
public class Family {
    Parents parents;
    Specimen child;
    
    public Family(Parents parents, Specimen child) {
        this.parents = parents;
        this.child = child;
    }
    
    public Specimen getMother() {
        return this.parents.mother;
    }
    
    public Specimen getFather() {
        return this.parents.father;
    }
    
    public Specimen getChild() {
        return this.child;
    }
}
