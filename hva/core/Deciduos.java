package hva.core;

import java.io.Serial;

import hva.core.enumf.LeafState;

public class Deciduos extends Tree {

    @Serial
    private static final long serialVersionUID = 1L;

    public Deciduos(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
        super(id, name, age, cleaningDifficulty, hotel);
    }

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
