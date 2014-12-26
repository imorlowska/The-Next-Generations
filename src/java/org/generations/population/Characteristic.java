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

/**
 * A class containing information about a single characteristic.
 * There can be many types of characteristics, e.g. colour, texture, height.
 * The characteristic can be allele (2x, 3x, etc) based or custom.
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
public abstract class Characteristic {
    private String name;
    
    public Characteristic(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
