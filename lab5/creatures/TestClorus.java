package creatures;
import huglife.Direction;
import huglife.Empty;
import huglife.Occupant;
import org.junit.Test;
import static org.junit.Assert.*;
import huglife.Action;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.001);
        assertEquals(new Color(34, 0, 231), c.color());
        assertEquals(c.name(), "clorus");
    }

    @Test
    public void testMove() {
        Clorus c = new Clorus(2);
        c.move();
        assertEquals(c.energy(), 1.97, 0.001);
    }

    @Test
    public void testStay() {
        Clorus c = new Clorus(2);
        c.stay();
        assertEquals(c.energy(), 1.99, 0.001);
    }

    @Test
    public void testReplicate() {
        Clorus parent = new Clorus(2);
        Clorus baby = parent.replicate();
        assertEquals(parent.energy(), 1, 0.001);
        assertEquals(baby.energy(), 1, 0.001);
    }

    @Test
    public void testAttack() {
        Clorus c = new Clorus(2);
        Plip victim = new Plip(2);
        c.attack(victim);
        assertEquals(c.energy(), 4, 0.001);
    }

    @Test
    public void testChooseActionStaySurrounded() {
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.LEFT, new Plip(2));
        neighbors.put(Direction.RIGHT, new Plip(2));
        neighbors.put(Direction.TOP, new Plip(2));
        neighbors.put(Direction.BOTTOM, new Plip(2));
        Clorus c = new Clorus(2);
        Action act = c.chooseAction(neighbors);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(act, expected);
    }

    @Test
    public void testChooseActionAttack() {
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.LEFT, new Plip(2));
        neighbors.put(Direction.RIGHT, new Empty());
        neighbors.put(Direction.TOP, new Empty());
        neighbors.put(Direction.BOTTOM, new Empty());
        Clorus c = new Clorus(2);
        Action act = c.chooseAction(neighbors);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.LEFT);
        assertEquals(act, expected);
    }

    @Test
    public void testChooseActionMove() {
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.LEFT, new Empty());
        neighbors.put(Direction.RIGHT, new Empty());
        neighbors.put(Direction.TOP, new Empty());
        neighbors.put(Direction.BOTTOM, new Empty());
        Clorus c = new Clorus(0.5);
        Action act = c.chooseAction(neighbors);
        Action expected = new Action(Action.ActionType.MOVE, Direction.LEFT);
        assertEquals(act.type, expected.type);
    }

    @Test
    public void testChooseActionReplicate() {
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.LEFT, new Empty());
        neighbors.put(Direction.RIGHT, new Empty());
        neighbors.put(Direction.TOP, new Empty());
        neighbors.put(Direction.BOTTOM, new Empty());
        Clorus c = new Clorus(1);
        Action act = c.chooseAction(neighbors);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.LEFT);
        assertEquals(act.type, expected.type);
    }

}
