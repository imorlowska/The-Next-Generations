package org.generations.offspring;

import org.generations.population.Specimen;
import org.generations.population.exceptions.IncompatibleCharacteristicsException;
import org.generations.population.exceptions.IncompatibleGenderBreedingException;

/**
 * Offspring generator. Takes parents and produces a random offspring.
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public class Generator {
    public static Specimen produceChild(Parents parents) 
            throws IncompatibleGenderBreedingException, IncompatibleCharacteristicsException {
        return parents.mother.produceChildWith(parents.father);
    }
}
