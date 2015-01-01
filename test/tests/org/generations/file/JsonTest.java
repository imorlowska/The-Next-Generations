package tests.org.generations.file;

import org.generations.file.Reader;
import org.generations.population.Population;
import org.generations.population.Specimen;
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
            + "\"testValue\":42,"
            + "\"ageCycles\":100,"
            + "\"name\":\"Tribbles2\","
            + "\"males\":["
            + "{"
                + "\"specimenDead\":0,"
                + "\"specimenID\":2,"
                + "\"specimenNumberToDate\":4,"
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
                + "\"specimenDead\":0,"
                + "\"specimenID\":1,"
                + "\"specimenNumberToDate\":4,"
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
}
