/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.org.generations.population;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Izabela
 */
public class PopulationTest {
    public PopulationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void populationPrintTest() {
        ExamplePopulation pop = new ExamplePopulation();
        assertEquals(pop.print(), 
                "Population: Tribbles\n" +
                "Specimen: \n" +
                "Specimen # 1\n" +
                "Status: alive\n" +
                "Age: 0\n" +
                "ETTL: 4\n" +
                "Specimen # 2\n" +
                "Status: alive\n" +
                "Age: 0\n" +
                "ETTL: 6\n" +
                "Specimen # 3\n" +
                "Status: alive\n" +
                "Age: 0\n" +
                "ETTL: 5\n");
    }
}
