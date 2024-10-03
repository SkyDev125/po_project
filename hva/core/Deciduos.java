package hva.core;

import hva.core.enumf.LeafState;

public class Deciduos extends Tree {
    public Deciduos(String id, String name, int age, int cleaningDifficulty) {
        super(id, name, age, cleaningDifficulty);
    }

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
