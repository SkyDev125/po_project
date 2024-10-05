package hva.core;

import java.io.Serial;

import hva.core.enumf.LeafState;
import hva.core.enumf.SeasonType;

public class Deciduos extends Tree {

    @Serial
    private static final long serialVersionUID = 1L;

    /*
     * <------------------------ Constructor ------------------------>
     */

    public Deciduos(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
        super(id, name, age, cleaningDifficulty, hotel);
    }

    /*
     * <------------------------ Others ------------------------>
     */

    /**
     * Returns the seasonal effort of the tree in this instance.
     * @return the seasonal effort
     */
    protected int seasonalEffort() {
        SeasonType currentSeason = hotel().season();

        if (currentSeason == SeasonType.SPRING) {
            return 1;
        }

        if (currentSeason == SeasonType.SUMMER) {
            return 2;
        }

        if (currentSeason == SeasonType.FALL) {
            return 5;
        }

        return 0;
    }

    /**
     * Returns the leaf state of the tree in this instance.
     * @return the leaf state
     */
    protected LeafState leafState() {
        SeasonType currentSeason = hotel().season();
        if (currentSeason == SeasonType.SPRING) {
            return LeafState.GENERATELEAVES;
        }

        if (currentSeason == SeasonType.SUMMER) {
            return LeafState.WITHLEAVES;
        }

        if (currentSeason == SeasonType.FALL) {
            return LeafState.FALLINGLEAVES;
        }

        return LeafState.WITHOUTLEAVES;
    }

    /**
     * Returns the tree in the format:
     * ́ARVORE|idArvore|nomeArvore|idadeArvore|dificuldadeBaseLimpeza|tipoArvore|cicloBiologico
     * @return the tree in format 
     */
    public String toString() {
        LeafState bioCycle = leafState();

        return "ARVORE|" + id() + "|" + name() + "|" + Integer.toString(age()) + "|" + Integer.toString(cleaningDifficulty()) + "|CADUCA|" + bioCycle;
    }
}
