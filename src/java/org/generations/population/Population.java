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

/**
 * A class containing all information about the population.
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public class Population {
    private List<Specimen> population;
    private String name;
    private int ageCycles;

    public Population(String name) {
        this.name = name;
        this.population = new ArrayList<>();
        this.ageCycles = 0;
    }
    
    public Population(String name, List<Specimen> population, int ageCycles) {
        this.name = name;
        this.population = population;
        this.ageCycles = ageCycles;
    }
    
    public void addSpecimen(Specimen specimen) {
        this.population.add(specimen);
    }
    
    /**
     * Remove a specimen from the population.
     * @param specimen to remove
     * @throws IllegalAccessException if specimen not in population
     */
    public void removeSpecimen(Specimen specimen) throws IllegalAccessException {
        if (this.population.contains(specimen)) {
            this.population.remove(specimen);
        } else {
            throw new IllegalAccessException(specimen.toString() + " does not exist!");
        }
    }
    
    /**
     * @return the population
     */
    public List<Specimen> getPopulation() {
        return population;
    }

    /**
     * @param population the population to set
     */
    public void setPopulation(List<Specimen> population) {
        this.population = population;
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
}
