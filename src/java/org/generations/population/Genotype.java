/*
 * Copyright (C) 2014 Izabela Orlowska <imorlowska@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.generations.population;

import java.util.ArrayList;
import java.util.List;
import org.generations.population.exceptions.IncompatibleCharacteristicsException;
import org.generations.population.exceptions.IncompatibleGenderBreedingException;

/**
 * A class with information about all characteristics of a single genotype.
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public class Genotype {
    public static enum Gender {MALE, FEMALE};
    private final Gender gender;
    private final AlleleCharacteristic genderCharacteristic;
    private List<Characteristic> characteristics;
    
    public Genotype(Gender g) {
        this.gender = g;
        if (g.equals(Gender.MALE)) {
            genderCharacteristic = AlleleCharacteristic.MALE;
        } else {
            genderCharacteristic = AlleleCharacteristic.FEMALE;
        }
        characteristics = new ArrayList();
    }
    
    private Genotype(AlleleCharacteristic g) {
        this.genderCharacteristic = g;
        if (g.isDominant()) {
            this.gender = Gender.MALE;
        } else {
            this.gender = Gender.FEMALE;
        }
        characteristics = new ArrayList();
    }
    
    public void addCharacteristic(Characteristic c) {
        characteristics.add(c);
    }
    
    public void addCharacteristics(List<Characteristic> characteristics) {
        this.characteristics.addAll(characteristics);
    }
    
    public List<Characteristic> getCharacteristics() {
        return this.characteristics;
    }
    
    public Gender getGender() {
        return this.gender;
    }
    
    public Characteristic getCharacteristic(String name) 
            throws IllegalAccessException {
        for (Characteristic c : characteristics) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new IllegalAccessException("Characterstic not found");
    }
    
    public static Genotype createChild(Genotype g1, Genotype g2)
            throws IncompatibleGenderBreedingException, 
            IncompatibleCharacteristicsException {
        if (g1.gender.equals(g2.gender)) {
            throw new IncompatibleGenderBreedingException();
        }
        if (g1.getCharacteristics().size() != g2.getCharacteristics().size()) {
            throw new IncompatibleCharacteristicsException();
        }
        
        AlleleCharacteristic sex = (AlleleCharacteristic) 
                g1.genderCharacteristic.produceChildWith(
                        g2.genderCharacteristic);
        Genotype child = new Genotype(sex);
        try {
            for (Characteristic c1 : g1.getCharacteristics()) {
                Characteristic c2 = g2.getCharacteristic(c1.getName());
                Characteristic childCharacteristic = c1.produceChildWith(c2);
                child.addCharacteristic(childCharacteristic);
            }
        } catch (Exception e) {
            throw new IncompatibleCharacteristicsException();
        }
        return child;
    }   
}
