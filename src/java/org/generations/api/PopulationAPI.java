/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generations.api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import org.generations.examples.ExampleFamily;
import org.generations.examples.ExamplePopulation;
import org.generations.file.Reader;
import org.generations.offspring.Family;
import org.generations.offspring.Generator;
import org.generations.offspring.Parents;
import org.generations.population.Population;
import org.generations.population.Specimen;
import org.generations.population.exceptions.IncompatibleCharacteristicsException;
import org.generations.population.exceptions.IncompatibleGenderBreedingException;
import org.json.JSONObject;

/**
 * REST Web Service
 * @author Izabela Orlowska <imorlowska@gmail.com>
 */
@Path("api")
public class PopulationAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PopulationAPI
     */
    public PopulationAPI() {
    }

    /**
     * Retrieves representation of an instance of org.generations.api.PopulationAPI
     * @return an instance of java.lang.String
     * @throws org.generations.population.exceptions.IncompatibleGenderBreedingException
     * @throws org.generations.population.exceptions.IncompatibleCharacteristicsException
     */
    @GET
    @Produces("application/json")
    public String getJson() throws IncompatibleGenderBreedingException,
            IncompatibleCharacteristicsException {
        ExamplePopulation pop = new ExamplePopulation();
        JSONObject popObject = new JSONObject(pop.population);
        return popObject.toString();
        //return "{\"testValue\":42,\"ageCycles\":100,\"name\":\"Tribbles2\"}";
    }
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String postJson(String population)
            throws IncompatibleGenderBreedingException, IncompatibleCharacteristicsException {
        Population pop = Reader.parseJSON2Population(population);
        pop.nextStep();
        JSONObject popObject = new JSONObject(pop);
        return popObject.toString();
    }
    
    @GET
    @Produces("application/json")
    @Path("offspring")
    public String getOffspring() throws IncompatibleGenderBreedingException,
            IncompatibleCharacteristicsException {
        Family family = ExampleFamily.get();
        JSONObject popObject = new JSONObject(family);
        return popObject.toString();
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("offspring")
    public String postOffspring(String jparents)
            throws IncompatibleGenderBreedingException, IncompatibleCharacteristicsException {
        Parents parents = Reader.parseJSON2Parents(jparents);
        Specimen child = Generator.produceChild(parents);
        Family family = new Family(parents, child);
        JSONObject fObject = new JSONObject(family);
        return fObject.toString();
    }
}
