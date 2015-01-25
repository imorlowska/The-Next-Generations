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
        AlleleCharacteristic ch1 = new AlleleCharacteristic("Hair colour");
        ch1.setDominantName("Black hair");
        ch1.setRecessiveName("Ginger hair");
        ch1.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
        
        AlleleCharacteristic ch2a = new AlleleCharacteristic("Eye colour");
        ch2a.setDominantName("Brown eyes");
        ch2a.setRecessiveName("Green eyes");
        ch2a.setAllele(Allele.RECESSIVE, Allele.RECESSIVE);
        AlleleCharacteristic ch2b = new AlleleCharacteristic("Eye colour");
        ch2b.setDominantName("Brown eyes");
        ch2b.setRecessiveName("Green eyes");
        ch2b.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
        
        AlleleCharacteristic ch3 = new AlleleCharacteristic("Right/left handed");
        ch3.setDominantName("Right handed");
        ch3.setRecessiveName("Left handed");
        ch3.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
        
        AlleleCharacteristic ch4a = new AlleleCharacteristic("Hair curliness");
        ch4a.setDominantName("Curly hair");
        ch4a.setRecessiveName("Straight hair");
        ch4a.setAllele(Allele.RECESSIVE, Allele.RECESSIVE);
        AlleleCharacteristic ch4b = new AlleleCharacteristic("Hair curliness");
        ch4b.setDominantName("Curly hair");
        ch4b.setRecessiveName("Straight hair");
        ch4b.setAllele(Allele.DOMINANT, Allele.DOMINANT);
        
        AlleleCharacteristic ch5a = new AlleleCharacteristic("Freckles");
        ch5a.setDominantName("Present");
        ch5a.setRecessiveName("Absent");
        ch5a.setAllele(Allele.DOMINANT, Allele.DOMINANT);
        AlleleCharacteristic ch5b = new AlleleCharacteristic("Freckles");
        ch5b.setDominantName("Present");
        ch5b.setRecessiveName("Absent");
        ch5b.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
        
        AlleleCharacteristic ch6a = new AlleleCharacteristic("Tongue rolling ability");
        ch6a.setDominantName("Present");
        ch6a.setRecessiveName("Absent");
        ch6a.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
        AlleleCharacteristic ch6b = new AlleleCharacteristic("Tongue rolling ability");
        ch6b.setDominantName("Present");
        ch6b.setRecessiveName("Absent");
        ch6b.setAllele(Allele.RECESSIVE, Allele.RECESSIVE);
        
        Genotype mGen = new Genotype(Genotype.Gender.FEMALE);
        mGen.addCharacteristic(ch1);
        mGen.addCharacteristic(ch2a);
        mGen.addCharacteristic(ch3);
        mGen.addCharacteristic(ch4a);
        mGen.addCharacteristic(ch5a);
        mGen.addCharacteristic(ch6a);
        
        Genotype fGen = new Genotype(Genotype.Gender.MALE);
        fGen.addCharacteristic(ch1);
        fGen.addCharacteristic(ch2b);
        fGen.addCharacteristic(ch3);
        fGen.addCharacteristic(ch4b);
        fGen.addCharacteristic(ch5b);
        fGen.addCharacteristic(ch6b);
        
        Specimen mother = Specimen.createSpecimen().setGenotype(mGen);
        Specimen father = Specimen.createSpecimen().setGenotype(fGen);
        
        return new Parents(mother, father);
    }
}
