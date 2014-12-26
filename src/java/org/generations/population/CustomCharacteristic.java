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
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.generations.other.Pair;

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
    private List<String> types;
    private Map<Pair<String, String>, List<Pair<String, Integer>>> 
            probabilities;
    
    public CustomCharacteristic(String name) {
        super(name);
        types = new ArrayList();
    }

    @Override
    public Characteristic produceChildWith(Characteristic mate)
            throws IncompatibleCharacteristicsException {
        return null;
    }

    @Override
    public String getCharacteristicType() {
        return null;
    }
}
