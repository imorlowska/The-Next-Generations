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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.generations.other.Pair;
import org.generations.population.exceptions.InvalidCustomCharacteristicTypeException;

/**
 * A custom characteristic class. How to use:
 * 1. Add all the possible types for the characteristic
 * 2. Specify the type of the current instance. Make sure it is included in the
 *      list from step 1.
 * 3. Specify the probabilities by adding list of probability pairs
 *      (type, percentage) to key <type1, type2>. Make sure the types are 
 *      included in the list from step 1.
 * 
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public class CustomCharacteristic extends Characteristic {
    private static final Random rand = new Random();
    private String type;
    private final List<String> types;
    private Map<Pair<String, String>, List<Pair<String, Integer>>> 
            probabilities;
    
    public CustomCharacteristic(String name) {
        super(name);
        types = new ArrayList();
        type = "Not set";
        probabilities = new HashMap();
    }
    
    public void addType(String type) 
            throws InvalidCustomCharacteristicTypeException {
        if (types.contains(type)) {
            throw new InvalidCustomCharacteristicTypeException();
        }
        types.add(type);
    }
    
    public void addTypes(List<String> types)
            throws InvalidCustomCharacteristicTypeException {
        for (String t : types) {
            this.addType(t);
        }
    }
    
    public List<String> getTypes() {
        return this.types;
    }
    
    public void setType(String type)
            throws InvalidCustomCharacteristicTypeException {
        if (!types.contains(type)) {
            throw new InvalidCustomCharacteristicTypeException();
        }
        this.type = type;
    }
    
    public void addProbability(
            Pair<String, String> key, List<Pair<String, Integer>> values)
            throws InvalidCustomCharacteristicTypeException {
        if ( (!types.contains(key.getFirst())) 
                || (!types.contains(key.getSecond()))) {
            // invalid key
            throw new InvalidCustomCharacteristicTypeException();
        }
        int sum = 0;
        for (Pair<String, Integer> value : values) {
            if (!types.contains(value.getFirst())
                    || value.getSecond() < 0
                    || value.getSecond() > 100) {
                // invalid type in values
                throw new InvalidCustomCharacteristicTypeException();
            }
            sum += value.getSecond();
        }
        if (sum != 100) {
            // probability values should sum up to a 100
            throw new InvalidCustomCharacteristicTypeException();
        }
        if (values.size() != types.size()) {
            // invalid number of values for key
            throw new InvalidCustomCharacteristicTypeException();
        }
        
        probabilities.put(key, values);
        probabilities.put(new Pair(key.getSecond(), key.getFirst()), values);
    }
    
    private void addProbabilities(
            Map<Pair<String, String>, List<Pair<String, Integer>>> map) {
        this.probabilities = map;
    }
    
    public Map<Pair<String, String>, List<Pair<String, Integer>>> 
        getProbabilities() {
        return this.probabilities;
    }

    @Override
    public String getCharacteristicType() {
        return this.type;
    }
    
    @Override
    public Characteristic produceChildWith(Characteristic mate)
            throws IncompatibleCharacteristicsException, 
            InvalidCustomCharacteristicTypeException {
        if (!(mate instanceof CustomCharacteristic)
                 || !(mate.getName().equals(this.getName()))) {
            throw new IncompatibleCharacteristicsException();
        }
        Pair<String, String> key = 
                new Pair(this.type, mate.getCharacteristicType());
        List<Pair<String, Integer>> probability = probabilities.get(key);
        return getChild(probability);
    }
    
    private CustomCharacteristic getChild(
            List<Pair<String, Integer>> probability) 
            throws InvalidCustomCharacteristicTypeException {
        CustomCharacteristic child = new CustomCharacteristic(this.name);
        child.addTypes(this.types);
        child.addProbabilities(probabilities);
        
        int percent = rand.nextInt(101);
        for (Pair<String, Integer> p : probability) {
            percent -= p.getSecond();
            if (percent <= 0) {
                child.setType(p.getFirst());
                break;
            }
        }
        if (percent > 0) {
            // didn't find type
            throw new InvalidCustomCharacteristicTypeException();
        }
        return child;
    }
}
