package hva.core;

import hva.core.enumf.LeafState;

public class Evergreen extends Tree {

    // constructor
    public Evergreen(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
        super(id, name, age, cleaningDifficulty, hotel);
    }

    // others
    private int seasonalEffort() {

        // TODO

        return 20;
    }

    private LeafState leafState() {

        // TODO

        return LeafState.FALLINGLEAVES;
    }

    public String toString() {

        // TODO

        return "ola";
    }
}
