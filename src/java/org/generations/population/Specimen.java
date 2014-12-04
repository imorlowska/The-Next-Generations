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

import com.sun.istack.Nullable;

/**
 * A class containing information about a single specimen and its genotype.
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public class Specimen {
    private static long specimenNumberToDate = 0;
    private static long specimenDead = 0;
    private static int averageLifeExp = 100;
    
    private long specimenID;
    private int age;
    private int lifeExp;
    private boolean alive;
    private Genotype genotype;
    
    /////////////////////////////////// PRIVATE CONSTRUCTOR AND DEFAULT VALUES ///////////////////
    private Specimen() {
        this.specimenID = ++specimenNumberToDate;
        this.age = 0;
        lifeExp = averageLifeExp;
        alive = true;
        genotype = null;
    }
    
    /////////////////////////////////// STATIC CONSTRUCTOR METHOD AND IN PLACE SETTERS ///////////
    public static Specimen createSpecimen() {
        return new Specimen();
    }
    
    public Specimen setAge(int age) throws IllegalStateException {
        throwExceptionIfDead();
        this.age = age;
        return this;
    }
    
    public Specimen setSpecimenID(long ID) throws IllegalStateException {
        throwExceptionIfDead();
        this.specimenID = ID;
        if (ID >= specimenNumberToDate) {
            specimenNumberToDate = ID +1;
        }
        return this;
    }
    
    public Specimen setLifeExp(int lifeExp) throws IllegalStateException {
        throwExceptionIfDead();
        this.lifeExp = lifeExp;
        return this;
    }
    
    public Specimen setGenotype(Genotype genotype) throws IllegalStateException {
        throwExceptionIfDead();
        this.genotype = genotype;
        return this;
    }
    /////////////////////////////////// STATIC SETTERS ///////////////////////////////////////////
    /**
     * Should only be called if reading a whole population.
     * @param killed the number of specimen dead to date
     */
    public static void setSpecimenDead(long killed) {
        specimenDead = killed;
    }
    
    /**
     * Should only be called if reading a whole population.
     * @param number the number of specimen to date
     */
    public static void setSpecimenNumberToDate(long number) {
        specimenNumberToDate = number;
    }
    
    /**
     * Updates the average life expectancy.
     * @param age the new average
     */
    public static void setAverageLifeExp(int age) {
        averageLifeExp = age;
    }
    
    /////////////////////////////////// GETTERS //////////////////////////////////////////////////
    public int getAge() {
        return this.age;
    }
    
    public long getSpecimenID() {
        return this.specimenID;
    }
    
    public int getLifeExp() {
        return this.lifeExp;
    }
    
    @Nullable
    public Genotype getGenotype() {
        return this.genotype;                
    }
    /////////////////////////////////// STATIC GETTERS ///////////////////////////////////////////
    public static long getSpecimenNumberToDate() {
        return specimenNumberToDate;
    }
    
    public static long getSpecimenDead() {
        return specimenDead;
    }
    
    public static int getAverageLifeExp() {
        return averageLifeExp;
    }

    /////////////////////////////////// SPECIAL METHODS //////////////////////////////////////////
    /**
     * Ages the specimen.
     * @return true if specimen died, false otherwise
     */
    public boolean ageSpecimenAndMaybeDie() {
        throwExceptionIfDead();
        ++this.age;
        if (this.age >= this.lifeExp) {
            this.die();
            return true;
        }
        return false;
    }
    
    /**
     * Kills the specimen. NOTE: doesn't remove it from the population!
     */
    public void die() {
        ++specimenDead;
        this.alive = false;
    }
    
    private void throwExceptionIfDead() throws IllegalStateException {
        if (this.alive) {
            throw new IllegalStateException("The specimen is dead!");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder desc = new StringBuilder();
        desc.append("Specimen # ");
        desc.append(this.specimenID);
        return desc.toString();
    }
    
    public String toStringDetailed() {
        StringBuilder desc = new StringBuilder();
        desc.append(this.toString());
        desc.append("\nStatus: ");
        if (this.alive) {
            desc.append("alive");
            desc.append("\nAge: ");
            desc.append(this.age);
            desc.append("\nETTL: ");
            desc.append(this.lifeExp - this.age);
        } else {
            desc.append("deceased");
            desc.append("\nAge at the time of death: ");
            desc.append(this.age);
        }
        return desc.toString();
    }
}
