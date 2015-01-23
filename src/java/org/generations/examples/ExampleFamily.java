/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generations.examples;

import org.generations.offspring.Family;
import org.generations.offspring.Generator;
import org.generations.offspring.Parents;
import org.generations.population.Specimen;
import org.generations.population.exceptions.IncompatibleCharacteristicsException;
import org.generations.population.exceptions.IncompatibleGenderBreedingException;

/**
 *
 * @author Izabela
 */
public class ExampleFamily {
    public static Family get() throws IncompatibleGenderBreedingException,
            IncompatibleCharacteristicsException {
        Parents parents = ExampleParents.get();
        Specimen child = Generator.produceChild(parents);
        return new Family(parents, child);
    }
}
