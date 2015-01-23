/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generations.examples;

import org.generations.offspring.Parents;
import org.generations.population.Allele;
import org.generations.population.AlleleCharacteristic;
import org.generations.population.Genotype;
import org.generations.population.Specimen;

/**
 *
 * @author Izabela
 */
public class ExampleParents {
    public static Parents get() {
        Genotype mGen = new Genotype(Genotype.Gender.FEMALE);
        AlleleCharacteristic ch = new AlleleCharacteristic("Hair colour");
        ch.setDominantName("Brown");
        ch.setRecessiveName("Blonde");
        ch.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
        mGen.addCharacteristic(ch);
        
        Genotype fGen = new Genotype(Genotype.Gender.MALE);
        fGen.addCharacteristic(ch);
        
        Specimen mother = Specimen.createSpecimen().setGenotype(mGen);
        Specimen father = Specimen.createSpecimen().setGenotype(fGen);
        
        return new Parents(mother, father);
    }
}
