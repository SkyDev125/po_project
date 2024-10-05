package hva.core;

import java.io.Serial;

import hva.core.enumf.LeafState;

public class Evergreen extends Tree {

    @Serial
    private static final long serialVersionUID = 1L;

    // constructor
    public Evergreen(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
        super(id, name, age, cleaningDifficulty, hotel);
    }

    // others
    protected int seasonalEffort() {

        // TODO

        return 20;
    }

    protected LeafState leafState() {

        // TODO

        return LeafState.FALLINGLEAVES;
    }

    public String toString() {

        // TODO

        return "ola";
    }
}
