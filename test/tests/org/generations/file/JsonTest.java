package tests.org.generations.file;

import org.generations.examples.ExampleParents;
import org.generations.file.Reader;
import org.generations.offspring.Parents;
import org.generations.population.Allele;
import org.generations.population.AlleleCharacteristic;
import org.generations.population.Genotype;
import org.generations.population.Population;
import org.generations.population.Specimen;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iza
 */
public class JsonTest {
    
    public JsonTest() {
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
    public void readTest() {
        Reader reader = new Reader();
        Population pop = reader.parseJSON2Population("{"
            + "\"specimenDead\":0,"
            + "\"specimenNumberToDate\":0,"
            + "\"averageLifeExp\":5,"
            + "\"ageCycles\":100,"
            + "\"name\":\"Tribbles2\","
            + "\"males\":["
            + "{"
                + "\"specimenID\":2,"
                + "\"male\":true,"
                + "\"age\":0,"
                + "\"averageLifeExp\":5,"
                + "\"lifeExp\":6,"
                + "\"genotype\":{"
                    + "\"characteristics\":["
                    + "{"
                        + "\"recessive\":false,"
                        + "\"recessiveName\":\"Rec\","
                        + "\"name\":\"test\","
                        + "\"stronglyDominant\":false,"
                        + "\"firstAllele\":{"
                        + "},"
                        + "\"characteristicType\":\"Dom\","
                        + "\"secondAllele\":{"
                        + "},"
                        + "\"dominant\":true,"
                        + "\"dominantName\":\"Dom\""
                    + "}"
                    + "],"
                + "\"gender\":{"
                + "}"
            + "},"
            + "\"alive\":true"
            +"},"
            + "],"
            + "\"females\":["
            + "{"
                + "\"specimenID\":1,"
                + "\"male\":false,"
                + "\"age\":0,"
                + "\"averageLifeExp\":5,"
                + "\"lifeExp\":4,"
                + "\"genotype\":{"
                + "\"characteristics\":["
                + "{"
                    + "\"recessive\":false,"
                    + "\"recessiveName\":\"Rec\","
                    + "\"name\":\"test\","
                    + "\"stronglyDominant\":false,"
                    + "\"firstAllele\":{"
                    + "},"
                    + "\"characteristicType\":\"Dom\","
                    + "\"secondAllele\":{"
                    + "},"
                    + "\"dominant\":true,"
                    + "\"dominantName\":\"Dom\""
                + "}"
                + "],"
                + "\"gender\":{"
                + "}"
            + "},"
            + "\"alive\":true"
        + "}"
        + "]"
        + "}");
        assertEquals("Tribbles2", pop.getName());
        assertEquals(100, pop.getAgeCycles());
        
        assertEquals(1, pop.getMales().size());
        Specimen male = pop.getMales().get(0);
        assertEquals(0, male.getAge());
        assertEquals(6, male.getLifeExp());
        assertEquals(2, male.getSpecimenID());
        assertEquals(true, male.isAlive());
        assertEquals(true, male.isMale());
        
        assertEquals(1, pop.getFemales().size());
        Specimen female = pop.getFemales().get(0);
        assertEquals(0, female.getAge());
        assertEquals(4, female.getLifeExp());
        assertEquals(1, female.getSpecimenID());
        assertEquals(true, female.isAlive());
        assertEquals(false, female.isMale());
        
        System.out.println("Read JSON test passed.");
    }

    @Test
    public void parentsTest() {
        Parents parents = ExampleParents.get();
        JSONObject jObject = new JSONObject(parents);
        
        Parents output = Reader.parseJSON2Parents(jObject.toString());
        
        assertEquals(jObject.toString(), (new JSONObject(output)).toString());
        System.out.println("parents parsing passed");
    }
}
