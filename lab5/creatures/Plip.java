package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;
import static huglife.HugLifeUtils.randomInt;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;
    /**
     * fraction of energy to retain when replicating
     */
    private double repEnergyRetained = 0.5;
    /**
     * fraction of energy to bestow upon offspring.
     */
    private double repEnergyGiven = 1;

    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 99;
        g = 0;
        b = 76;
        energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        g = (int) (96 * energy + 63);
        g = Math.min(g, 255); // a color should never be > 255
        return color(r, g, b);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        this.energy -= 0.15;
        this.energy = Math.max(energy, 0); // Energy should never be < 0
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        this.energy += 0.2;
        this.energy = Math.min(energy, 2); // Energy should never be > 2
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        energy = energy * repEnergyRetained;
        double babyEnergy = energy * repEnergyGiven;
        return new Plip(babyEnergy);
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1 - If there are no empty spaces, the Plip should STAY.
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        boolean anyClorus = false;

        neighbors.forEach((dir, occ) -> {
            if (occ.name().equals("empty")) {
                emptyNeighbors.add(dir);
            }
        });

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2 - If Plip energy >= 1.0, then replicate.
        if (this.energy >= 1.0) {
            Direction dir = randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.REPLICATE, dir);
        }

        // Rule 3 - If there's a clorus, move 50% of the time
        for (Direction dir : Direction.values()) {
            if (neighbors.get(dir).name().equals("clorus")) {
                anyClorus = true;
            }
        }
        if (anyClorus && Math.random() < 0.5) {
            Direction move_dir = randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.MOVE, move_dir);
        }

        // Rule 4
        return new Action(Action.ActionType.STAY);
    }
}
