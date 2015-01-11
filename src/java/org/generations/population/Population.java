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
import java.util.Random;
import org.generations.population.exceptions.IncompatibleCharacteristicsException;
import org.generations.population.exceptions.IncompatibleGenderBreedingException;

/**
 * A class containing all information about the population.
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public class Population {
    public static final Random rand = new Random();
    private long specimenNumberToDate = 0;
    private long specimenDead = 0;
    private int averageLifeExp = 100;
    private List<Specimen> females;
    private List<Specimen> males;
    private String name;
    private int ageCycles;

    public Population(String name) {
        this.name = name;
        this.females = new ArrayList<>();
        this.males = new ArrayList<>();
        this.ageCycles = 0;
    }
    
    public Population(String name, List<Specimen> population, int ageCycles) {
        this.name = name;
        for (Specimen s : population) {
            addSpecimen(s);
        }
        this.ageCycles = ageCycles;
    }
    
    public void addSpecimen(Specimen s) {
        s.setSpecimenID(specimenNumberToDate);
        ++specimenNumberToDate;
        if (s.getLifeExp() == 0) {
            s.setLifeExp(averageLifeExp);
        }
        if (s.isMale()) {
                males.add(s);
            } else {
                females.add(s);
            }
    }
    
    /**
     * Remove a specimen from the population.
     * @param specimen to remove
     * @throws IllegalAccessException if specimen not in population
     */
    public void removeSpecimen(Specimen specimen) throws IllegalAccessException {
        if (this.males.contains(specimen)) {
            this.males.remove(specimen);
        } if (this.females.contains(specimen)) {
            this.females.remove(specimen);
        } else {
            throw new IllegalAccessException(specimen.toString() + " does not exist!");
        }
    }
    
    /**
     * @return the females
     */
    public List<Specimen> getFemales() {
        return females;
    }
    
    /**
     * @return the males
     */
    public List<Specimen> getMales() {
        return males;
    }

    /**
     * @param males the population to set
     */
    public void setMales(List<Specimen> males) {
        this.males = males;
    }
    
    /**
     * @param females the population to set
     */
    public void setFemales(List<Specimen> females) {
        this.females = females;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ageCycles
     */
    public int getAgeCycles() {
        return ageCycles;
    }

    /**
     * @param ageCycles the ageCycles to set
     */
    public void setAgeCycles(int ageCycles) {
        this.ageCycles = ageCycles;
    }
    
    /**
     * Should only be called if reading a whole population.
     * @param killed the number of specimen dead to date
     */
    public void setSpecimenDead(long killed) {
        specimenDead = killed;
    }
    
    /**
     * Should only be called if reading a whole population.
     * @param number the number of specimen to date
     */
    public void setSpecimenNumberToDate(long number) {
        specimenNumberToDate = number;
    }
    
    /**
     * Updates the average life expectancy.
     * @param age the new average
     */
    public void setAverageLifeExp(int age) {
        averageLifeExp = age;
    }
    
    public long getSpecimenNumberToDate() {
        return specimenNumberToDate;
    }
    
    public long getSpecimenDead() {
        return specimenDead;
    }
    
    public int getAverageLifeExp() {
        return averageLifeExp;
    }
    
    public void nextStep() throws IncompatibleGenderBreedingException,
            IncompatibleCharacteristicsException {
        List<Specimen> children = new ArrayList();
        // if possible produce offspring
        if (females.size() > 0 && males.size() > 0) {
            for (Specimen mother : females) {
                if (rand.nextBoolean()) { // 50-50 chances of producing offspring
                    Specimen father = males.get(rand.nextInt(males.size()));
                    Specimen child = mother.produceChildWith(father);
                    specimenNumberToDate++;
                    children.add(child);
                }
            }
        }
        
        List<Specimen> dead = new ArrayList();
        // age and maybe die
        for (Specimen s : females) {
            s.ageSpecimenAndMaybeDie();
            if (!s.isAlive()) { // specimen died of old age
                dead.add(s);
                specimenDead++;
            }
        }
        for (Specimen s : males) {
            s.ageSpecimenAndMaybeDie();
            if (!s.isAlive()) { // specimen died of old age
                dead.add(s);
                specimenDead++;
            }
        }
        for (Specimen pop : dead) {
            if (pop.isMale()) {
                males.remove(pop);
            } else {
                females.remove(pop);
            }
        }
        
        // add children
        for (Specimen child : children) {
            addSpecimen(child);
        }
        ++ageCycles;
    }
}
