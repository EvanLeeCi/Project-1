
import org.junit.*;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;

/**
 * Extra tests for Project1.
 */
public class Project1ExamplesTest {

    private Project1 runner;

    @Before
    public void setup() {
        runner = new Project1(); // hits default constructor
    }

    /**
     * ex1: 5x5 with a 2x2 block in corner and one stray cell.
     */
    @Test
    public void testExample1() {
        String input =
            "5 5\n" +
            "OO...\n" +
            "OO...\n" +
            ".....\n" +
            "O....\n" +
            ".....\n";
        runner.loadFromString(input);

        assertEquals(5, runner.numRows());
        assertEquals(5, runner.numCols());

        assertTrue(runner.isAlive(0,0));
        assertTrue(runner.isAlive(1,1));
        assertTrue(runner.isAlive(3,0));

        runner.nextGeneration();
        assertFalse(runner.isAlive(3,0));
    }

    /**
     * ex2: blinker in the middle row.
     */
    @Test
    public void testExample2Blinker() {
        String input =
            "5 5\n" +
            ".....\n" +
            ".....\n" +
            ".OOO.\n" +
            ".....\n" +
            ".....\n";
        runner.loadFromString(input);

        runner.nextGeneration();

        assertTrue(runner.isAlive(1,2));
        assertTrue(runner.isAlive(2,2));
        assertTrue(runner.isAlive(3,2));

        assertFalse(runner.isAlive(2,1));
        assertFalse(runner.isAlive(2,3));
    }

    /**
     * ex3: 7x7 irregular pattern (not still life).
     */
    @Test
    public void testExample3() {
        String input =
            "7 7\n" +
            ".......\n" +
            ".OO....\n" +
            ".O.....\n" +
            "....O..\n" +
            "...OO..\n" +
            ".......\n" +
            ".......\n";
        runner.loadFromString(input);

        assertTrue(runner.isAlive(1,1));
        assertTrue(runner.isAlive(1,2));
        assertTrue(runner.isAlive(2,1));
        assertTrue(runner.isAlive(3,4));
        assertTrue(runner.isAlive(4,3));
        assertTrue(runner.isAlive(4,4));

        runner.nextGeneration();
        assertFalse(runner.isStillLife());
    }

    /**
     * ex4: diamond shaped still life.
     */
    @Test
    public void testExample4StillLife() {
        String input =
            "5 5\n" +
            ".....\n" +
            "..OO.\n" +
            ".O..O\n" +
            "..OO.\n" +
            ".....\n";
        runner.loadFromString(input);

        runner.nextGeneration();
        assertTrue(runner.isStillLife());
    }

    /**
     * ex5: 2x2 block still life.
     */
    @Test
    public void testExample5Block() {
        String input =
            "5 5\n" +
            ".....\n" +
            ".OO..\n" +
            ".OO..\n" +
            ".....\n" +
            ".....\n";
        runner.loadFromString(input);

        for (int i = 0; i < 3; i++) {
            runner.nextGeneration();
            assertTrue(runner.isAlive(1,1));
            assertTrue(runner.isAlive(1,2));
            assertTrue(runner.isAlive(2,1));
            assertTrue(runner.isAlive(2,2));
            assertTrue(runner.isStillLife());
        }
    }

    /**
     * ex6: empty grid stays empty and is still life.
     */
    @Test
    public void testExample6Empty() {
        String input =
            "3 3\n" +
            "...\n" +
            "...\n" +
            "...\n";
        runner.loadFromString(input);

        runner.nextGeneration();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertFalse(runner.isAlive(i,j));
            }
        }
        assertTrue(runner.isStillLife());
    }

    /**
     * ex7: full 3x3 becomes four corners alive, others dead.
     */
    @Test
    public void testExample7FullGrid() {
        String input =
            "3 3\n" +
            "OOO\n" +
            "OOO\n" +
            "OOO\n";
        runner.loadFromString(input);

        runner.nextGeneration();

        // corners alive
        assertTrue(runner.isAlive(0,0));
        assertTrue(runner.isAlive(0,2));
        assertTrue(runner.isAlive(2,0));
        assertTrue(runner.isAlive(2,2));

        // center and edges dead
        assertFalse(runner.isAlive(1,1));
        assertFalse(runner.isAlive(0,1));
        assertFalse(runner.isAlive(1,0));
        assertFalse(runner.isAlive(1,2));
        assertFalse(runner.isAlive(2,1));
    }

    /**
     * ex8: randomInitialize with 1.0 makes all cells alive.
     */
    @Test
    public void testExample8RandomInitializeAllAlive() {
        runner = new Project1(4, 4);
        runner.randomInitialize(1.0);

        for (int i = 0; i < runner.numRows(); i++) {
            for (int j = 0; j < runner.numCols(); j++) {
                assertTrue(runner.isAlive(i,j));
            }
        }
    }

    /**
     * ex9: loadFromFile using provided empty file.
     */
    @Test
    public void testExample9LoadFromFile() throws FileNotFoundException {
        runner.loadFromFile("../data/empty.txt"); // note ../
        runner.nextGeneration();
        assertTrue(runner.isStillLife());
    }

    /**
     * ex10: randomInitialize with 0.0 makes all cells dead.
     */
    @Test
    public void testExample10RandomAllDead() {
        runner = new Project1(4, 4);
        runner.randomInitialize(0.0);

        for (int i = 0; i < runner.numRows(); i++) {
            for (int j = 0; j < runner.numCols(); j++) {
                assertFalse(runner.isAlive(i,j));
            }
        }
    }
}
