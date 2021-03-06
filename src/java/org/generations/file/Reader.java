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

import java.util.ArrayList;
import java.util.List;
import org.generations.logs.LogsHandler;
import org.generations.offspring.Parents;
import org.generations.other.Pair;
import org.generations.population.Allele;
import org.generations.population.AlleleCharacteristic;
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
    public static Population parseJSON2Population(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        
        Population population = new Population(json.getString("name"));
        population.setAgeCycles(json.getInt("ageCycles"));
        population.setAverageLifeExp(json.getInt("averageLifeExp"));
        population.setSpecimenDead(json.getInt("specimenDead"));
        population.setSpecimenNumberToDate(json.getInt("specimenNumberToDate"));
        
        JSONArray malesArray = json.getJSONArray("males");
        for (int i = 0; i < malesArray.length(); ++i) {
            JSONObject sObject = malesArray.getJSONObject(i);
            Genotype g = new Genotype(Gender.MALE);
            try {
                JSONArray characteristics = sObject.getJSONObject("genotype").getJSONArray("characteristics");
                for (int j = 0; j < characteristics.length(); ++j) {
                    JSONObject chObject = characteristics.getJSONObject(j);
                    AlleleCharacteristic ch = new AlleleCharacteristic(chObject.getString("name"));
                    ch.setRecessiveName(chObject.getString("recessiveName"));
                    ch.setDominantName(chObject.getString("dominantName"));
                    if (chObject.getBoolean("recessive")) {
                        ch.setAllele(Allele.RECESSIVE, Allele.RECESSIVE);
                    } else if (chObject.getBoolean("stronglyDominant")) {
                        ch.setAllele(Allele.DOMINANT, Allele.DOMINANT);
                    } else {
                        ch.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
                    }
                    g.addCharacteristic(ch);
                }
            } catch (Exception e) {
                //just leave characteristics empty
            }
            
            List<Pair<String,String>> pref = new ArrayList<>();
            try {
              JSONArray preferences = sObject.getJSONArray("preferences");
              for (int j = 0; j < preferences.length(); ++j) {
                  JSONObject pObject = preferences.getJSONObject(j);
                  String name = pObject.getString("first");
                  String recdom = pObject.getString("second");
                  pref.add(new Pair(name,recdom));
              }
            } catch (Exception e) {
                // just leave preferences empty
            }
            
            Specimen specimen = Specimen.createSpecimen()
                    .setAge(sObject.getInt("age"))
                    .setLifeExp(sObject.getInt("lifeExp"))
                    .setSpecimenID(sObject.getLong("specimenID"))
                    .setGenotype(g)
                    .setIsAlive(sObject.getBoolean("alive"))
                    .setPreferences(pref);
            population.addSpecimen(specimen);
        }
        
        JSONArray femalesArray = json.getJSONArray("females");
        for (int i = 0; i < femalesArray.length(); ++i) {
            JSONObject sObject = femalesArray.getJSONObject(i);
            Genotype g = new Genotype(Gender.FEMALE);
            try {
                JSONArray characteristics = sObject.getJSONObject("genotype").getJSONArray("characteristics");
                for (int j = 0; j < characteristics.length(); ++j) {
                    JSONObject chObject = characteristics.getJSONObject(j);
                    AlleleCharacteristic ch = new AlleleCharacteristic(chObject.getString("name"));
                    ch.setRecessiveName(chObject.getString("recessiveName"));
                    ch.setDominantName(chObject.getString("dominantName"));
                    if (chObject.getBoolean("recessive")) {
                        ch.setAllele(Allele.RECESSIVE, Allele.RECESSIVE);
                    } else if (chObject.getBoolean("stronglyDominant")) {
                        ch.setAllele(Allele.DOMINANT, Allele.DOMINANT);
                    } else {
                        ch.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
                    }
                    g.addCharacteristic(ch);
                }
            } catch (Exception e) {
                // just leave characteristics empty
            }
            
            List<Pair<String,String>> pref = new ArrayList<>();
            try {
              JSONArray preferences = sObject.getJSONArray("preferences");
              for (int j = 0; j < preferences.length(); ++j) {
                  JSONObject pObject = preferences.getJSONObject(j);
                  String name = pObject.getString("first");
                  String recdom = pObject.getString("second");
                  pref.add(new Pair(name,recdom));
              }
            } catch (Exception e) {
                // just leave preferences empty
            }
            
            Specimen specimen = Specimen.createSpecimen()
                    .setAge(sObject.getInt("age"))
                    .setLifeExp(sObject.getInt("lifeExp"))
                    .setSpecimenID(sObject.getLong("specimenID"))
                    .setGenotype(g)
                    .setIsAlive(sObject.getBoolean("alive"))
                    .setPreferences(pref);
            population.addSpecimen(specimen);
        }
        
        return population;
    }

    public static Parents parseJSON2Parents(String jsonString) {
        System.out.println(jsonString);
        JSONObject json = new JSONObject(jsonString);
        JSONObject jMother = json.getJSONObject("mother");
        JSONObject jFather = json.getJSONObject("father");
        
        Genotype mG = new Genotype(Gender.FEMALE);
        try {
            JSONArray characteristics = jMother.getJSONObject("genotype").getJSONArray("characteristics");
            for (int j = 0; j < characteristics.length(); ++j) {
                JSONObject chObject = characteristics.getJSONObject(j);
                AlleleCharacteristic ch = new AlleleCharacteristic(chObject.getString("name"));
                ch.setRecessiveName(chObject.getString("recessiveName"));
                ch.setDominantName(chObject.getString("dominantName"));
                if (chObject.getBoolean("recessive")) {
                    ch.setAllele(Allele.RECESSIVE, Allele.RECESSIVE);
                } else if (chObject.getBoolean("stronglyDominant")) {
                    ch.setAllele(Allele.DOMINANT, Allele.DOMINANT);
                } else {
                    ch.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
                }
                mG.addCharacteristic(ch);
            }
        } catch (Exception e) {
            // just leave characteristics empty
        }
        
        Genotype fG = new Genotype(Gender.MALE);
        try {
            JSONArray characteristics = jFather.getJSONObject("genotype").getJSONArray("characteristics");
            for (int j = 0; j < characteristics.length(); ++j) {
                JSONObject chObject = characteristics.getJSONObject(j);
                AlleleCharacteristic ch = new AlleleCharacteristic(chObject.getString("name"));
                ch.setRecessiveName(chObject.getString("recessiveName"));
                ch.setDominantName(chObject.getString("dominantName"));
                if (chObject.getBoolean("recessive")) {
                    ch.setAllele(Allele.RECESSIVE, Allele.RECESSIVE);
                } else if (chObject.getBoolean("stronglyDominant")) {
                    ch.setAllele(Allele.DOMINANT, Allele.DOMINANT);
                } else {
                    ch.setAllele(Allele.DOMINANT, Allele.RECESSIVE);
                }
                fG.addCharacteristic(ch);
            }
        } catch (Exception e) {
            //just leave characteristics empty
        }
        
        Specimen mother = Specimen.createSpecimen().setGenotype(mG);
        Specimen father = Specimen.createSpecimen().setGenotype(fG);
        
        return new Parents(mother, father);
    }
}
