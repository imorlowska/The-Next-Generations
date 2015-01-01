/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generations.api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import org.generations.examples.ExamplePopulation;
import org.generations.population.exceptions.IncompatibleCharacteristicsException;
import org.generations.population.exceptions.IncompatibleGenderBreedingException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Iza
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

    /**
     * PUT method for updating or creating an instance of PopulationAPI
     * @param content representation for the resource
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
