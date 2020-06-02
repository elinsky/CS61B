package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.random;
import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {

    public Clorus() {
        super("clorus");
        energy = 1;
    }

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
        energy = Math.max(energy, 0); // energy should never drop below 0
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy = energy * 0.5;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy -= 0.01;
        energy = Math.max(energy, 0); // energy should never drop below 0
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1 - If no empty squares, STAY
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        neighbors.forEach((dir, occ) -> {
            if (occ.name().equals("empty")) {
                emptyNeighbors.add(dir);
            }
        });

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2 - Otherwise, if Plips near, attack
        Deque<Direction> attackVectors = new ArrayDeque<>();
        for (Direction dir : Direction.values()) {
            if (neighbors.get(dir).name().equals("plip")) {
                attackVectors.add(dir);
            }
        }
        if (attackVectors.size() > 0) {
            Direction att_dir = randomEntry(attackVectors);
            return new Action(Action.ActionType.ATTACK, att_dir);
        }

        // Rule 3 - If energy >= 1, then replicate
        if (this.energy() >= 1) {
            Deque<Direction> replicateVectors = new ArrayDeque<>();
            for (Direction dir : Direction.values()) {
                if (neighbors.get(dir).name().equals("empty")) {
                    replicateVectors.add(dir);
                }
            }
            Direction rep_dir = randomEntry(replicateVectors);
            return new Action(Action.ActionType.REPLICATE, rep_dir);
        }

        Deque<Direction> moveVectors = new ArrayDeque<>();
        for (Direction dir : Direction.values()) {
            if (neighbors.get(dir).name().equals("empty")) {
                moveVectors.add(dir);
            }
        }
        Direction move_dir = randomEntry(moveVectors);
        return new Action(Action.ActionType.MOVE, move_dir);
    }

    @Override
    public Color color() {
        return color(34, 0, 231);
    }
}

