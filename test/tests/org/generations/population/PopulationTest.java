package tests.org.generations.population;

import org.generations.population.AlleleCharacteristic;
import org.generations.population.exceptions.IncompatibleCharacteristicsException;
import org.generations.population.exceptions.IncompatibleGenderBreedingException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    
    /*@Test
    public void writerTest() throws IncompatibleGenderBreedingException,
            IncompatibleCharacteristicsException {
        ExamplePopulation pop = new ExamplePopulation();
        JSONObject jsonObject = new JSONObject(pop.population);
        System.out.println("Population:" + jsonObject.toString());
    }*/
    
    @Test
    public void genderCrossTest() throws IncompatibleCharacteristicsException {
        AlleleCharacteristic female = AlleleCharacteristic.FEMALE;
        AlleleCharacteristic male = AlleleCharacteristic.MALE;
        
        int females = 0;
        int males = 0;
        for (int i = 0; i < 10000; ++i) {
            AlleleCharacteristic child =
                    (AlleleCharacteristic) female.produceChildWith(male);
            if (child.isDominant()) {
                ++males;
            } else {
                ++ females;
            }
        }
        System.out.println("Females: " + females);
        System.out.println("Males: " + males);
    }
    
    @Test
    public void jsonTest() throws IncompatibleGenderBreedingException,
            IncompatibleCharacteristicsException {
        ExamplePopulation pop = new ExamplePopulation();
        JSONObject o = new JSONObject(pop.population);
        System.out.println(o.toString());
    }
}