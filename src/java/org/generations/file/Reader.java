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
package org.generations.file;

import org.generations.population.Genotype;
import org.generations.population.Genotype.Gender;
import org.generations.population.Population;
import org.generations.population.Specimen;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Parses a JSON into a Population or a Specimen.
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public class Reader {
    public Population parseJSON2Population(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        
        Population population = new Population(json.getString("name"));
        population.setAgeCycles(json.getInt("ageCycles"));
        
        JSONArray malesArray = json.getJSONArray("males");
        for (int i = 0; i < malesArray.length(); ++i) {
            JSONObject sObject = malesArray.getJSONObject(i);
            Genotype g = new Genotype(Gender.MALE);
            Specimen specimen = Specimen.createSpecimen()
                    .setAge(sObject.getInt("age"))
                    .setLifeExp(sObject.getInt("lifeExp"))
                    .setSpecimenID(sObject.getLong("specimenID"))
                    .setGenotype(g)
                    .setIsAlive(sObject.getBoolean("alive"));
            population.addSpecimen(specimen);
        }
        
        JSONArray femalesArray = json.getJSONArray("females");
        for (int i = 0; i < femalesArray.length(); ++i) {
            JSONObject sObject = femalesArray.getJSONObject(i);
            Genotype g = new Genotype(Gender.FEMALE);
            Specimen specimen = Specimen.createSpecimen()
                    .setAge(sObject.getInt("age"))
                    .setLifeExp(sObject.getInt("lifeExp"))
                    .setSpecimenID(sObject.getLong("specimenID"))
                    .setGenotype(g)
                    .setIsAlive(sObject.getBoolean("alive"));
            population.addSpecimen(specimen);
        }
        
        return population;
    }
}
