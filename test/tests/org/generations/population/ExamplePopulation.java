/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.org.generations.population;

import org.generations.population.Population;
import org.generations.population.Specimen;

/**
 * An example population.
 * @author Izabela
 */
public class ExamplePopulation {
    public Population population;
    
    public ExamplePopulation() {
        population = new Population("Tribbles");
        
        Specimen.setAverageLifeExp(5);
        
        Specimen s1 = Specimen.createSpecimen().setLifeExp(4);
        Specimen s2 = Specimen.createSpecimen().setLifeExp(6);
        Specimen s3 = Specimen.createSpecimen();
        
        population.addSpecimen(s1);
        population.addSpecimen(s2);
        population.addSpecimen(s3);
    }
    
    public String print() {
        StringBuilder desc = new StringBuilder();
        desc.append("Population: ");
        desc.append(population.getName());
        desc.append("\nSpecimen: \n");
        for (Specimen s : population.getPopulation()) {
            desc.append(s.toStringDetailed());
            desc.append("\n");
        }
        return desc.toString();
    }
}
