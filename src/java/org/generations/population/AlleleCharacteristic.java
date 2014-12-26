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

import org.generations.population.exceptions.IncompatibleCharacteristicsException;
import java.util.Random;

/**
 * Characteristic based on two alleles.
 * More info: http://learn.genetics.utah.edu/content/inheritance/patterns/
 * 
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public class AlleleCharacteristic extends Characteristic {
    private static final Random rand = new Random();
    private String dominantName;
    private String recessiveName;
    private Allele first;
    private Allele second;
    
    public AlleleCharacteristic(String name) {
        super(name);
        first = second = null;
        dominantName = recessiveName = "Not set";
    }
    
    public void setAllele(Allele first, Allele second) {
        this.first = first;
        this.second = second;
    }
    
    public void setFirstAllele(Allele first) {
        this.first = first;
    }
    
    public void setSecondAllele(Allele second) {
        this.second = second;
    }
    
    public Allele getFirstAllele() {
        return first;
    }
    
    public Allele getSecondAllele() {
        return second;
    }
    
    public void setNames(String dominant, String recessive) {
        this.dominantName = dominant;
        this.recessiveName = recessive;
    }
    
    public void setDominantName(String dominant) {
        this.dominantName = dominant;
    }
    
    public void setRecessiveName(String recessive) {
        this.recessiveName = recessive;
    }
    
    public String getDominantName() {
        return this.dominantName;
    }
    
    public String getRecessiveName() {
        return this.recessiveName;
    }
    
    @Override
    public String getCharacteristicType() {
        if (this.isDominant()) {
            return this.getDominantName();
        } else {
            return this.getRecessiveName();
        }
    }
    
    public boolean isDominant() {
        return first.equals(Allele.DOMINANT) || second.equals(Allele.DOMINANT);
    }
    
    public boolean isStronglyDominant() {
        return first.equals(Allele.DOMINANT) && second.equals(Allele.DOMINANT);
    }
    
    public boolean isRecessive() {
        return 
            first.equals(Allele.RECESSIVE) && second.equals(Allele.RECESSIVE);
    }
    
    @Override
    public Characteristic produceChildWith(Characteristic mate) 
            throws IncompatibleCharacteristicsException {
        
        if (!(mate instanceof AlleleCharacteristic)
                || !(mate.getName().equals(this.getName()))) {
            throw new IncompatibleCharacteristicsException();
        }
        
        AlleleCharacteristic child = new AlleleCharacteristic(this.getName());
        if (rand.nextBoolean()) {
            child.setFirstAllele(this.getFirstAllele());
        } else {
            child.setFirstAllele(this.getSecondAllele());
        }
        if (rand.nextBoolean()) {
            child.setSecondAllele(
                    ((AlleleCharacteristic)mate).getFirstAllele());
        } else {
            child.setSecondAllele(
                    ((AlleleCharacteristic)mate).getSecondAllele());
        }
        return child;
    }
}
