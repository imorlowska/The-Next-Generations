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

import java.util.Random;

/**
 * Characteristic based on two allele. 
 * More info: http://learn.genetics.utah.edu/content/inheritance/patterns/
 * 
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public class AlleleCharacteristic extends Characteristic {
    private static final Random rand = new Random();
    private Allele first;
    private Allele second;
    
    public AlleleCharacteristic(String name) {
        super(name);
        first = second = null;
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
    
    public static AlleleCharacteristic produceChild(
            AlleleCharacteristic p1, AlleleCharacteristic p2) 
            throws IncompatibleCharacteristicsException {
        
        if (!(p1.getName().equals(p2.getName()))) {
            throw new IncompatibleCharacteristicsException();
        }
        
        AlleleCharacteristic child = new AlleleCharacteristic(p1.getName());
        if (rand.nextBoolean()) {
            child.setFirstAllele(p1.getFirstAllele());
        } else {
            child.setFirstAllele(p1.getSecondAllele());
        }
        if (rand.nextBoolean()) {
            child.setSecondAllele(p2.getFirstAllele());
        } else {
            child.setSecondAllele(p2.getSecondAllele());
        }
        return child;
    }
}
