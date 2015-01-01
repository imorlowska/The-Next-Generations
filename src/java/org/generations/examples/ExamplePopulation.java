/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generations.examples;

import org.generations.population.Allele;
import org.generations.population.AlleleCharacteristic;
import org.generations.population.Characteristic;
import org.generations.population.Genotype;
import org.generations.population.Genotype.Gender;
import org.generations.population.Population;
import org.generations.population.Specimen;
import org.generations.population.exceptions.IncompatibleCharacteristicsException;
import org.generations.population.exceptions.IncompatibleGenderBreedingException;

/**
 * An example population.
 * @author Izabela
 */
public class ExamplePopulation {
    public Population population;
    
    public ExamplePopulation() throws IncompatibleGenderBreedingException, 
            IncompatibleCharacteristicsException {
        population = new Population("Tribbles2");
        
        Specimen.setAverageLifeExp(5);
        
        Specimen s1 = Specimen.createSpecimen().setLifeExp(4);
        Specimen s2 = Specimen.createSpecimen().setLifeExp(6);
        Specimen s3 = Specimen.createSpecimen();
        
        AlleleCharacteristic c1 = new AlleleCharacteristic("test");
        c1.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
        c1.setDominantName("Dom");
        c1.setRecessiveName("Rec");
        
        Genotype g1 = new Genotype(Gender.FEMALE);
        g1.addCharacteristic(c1);
        s1.setGenotype(g1);
        
        Genotype g2 = new Genotype(Gender.MALE);
        g2.addCharacteristic(c1);
        s2.setGenotype(g2);

        Specimen s4 = s1.produceChildWith(s2);
        
        population.addSpecimen(s1);
        population.addSpecimen(s2);
        population.addSpecimen(s3);
        population.addSpecimen(s4);
    }
}
