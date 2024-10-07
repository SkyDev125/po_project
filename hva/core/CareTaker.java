package hva.core;

import java.io.Serial;
import java.util.HashMap;

import hva.core.exception.HabitatNotFoundException;

public class CareTaker extends Worker {

    @Serial
    private static final long serialVersionUID = 1L;

    private HashMap<String, Habitat> _responsibilities = new HashMap<String, Habitat>();

    /*
     * <------------------------ Constructor ------------------------>
     */

    public CareTaker(String id, String name, Hotel hotel) {
        super(id, name, hotel);
        _responsibilities = new HashMap<String, Habitat>();
    }

    /*
     * <------------------------ Others ------------------------>
     */

    /**
     * Adds an habitat as a responsability for the care taker in this instance to
     * care.
     * 
     * @param id of the habitat
     */
    protected void addResponsibility(String id) throws HabitatNotFoundException {
        _responsibilities.put(id, hotel().habitatExistsWithException(id));
    }

    /**
     * Removes an habitat as a responsability for the care taker in this instance to
     * care.
     * 
     * @param id of the habitat
     */
    protected void removeResponsibility(String id) throws HabitatNotFoundException {
        if (_responsibilities.remove(id) == null) {
            throw new HabitatNotFoundException(id);
        }
    }

    /**
     * Calculates the satisfaction of the vet in this instance.
     * 
     * @return the satisfaction
     */
    protected float satisfaction() {
        int satisfactionPerHabitat = 0;
        int workInHabitat;

        for (HashMap.Entry<String, Habitat> entry : _responsibilities.entrySet()) {
            Habitat currentHabitat = entry.getValue();

            workInHabitat = currentHabitat.area() + 3 * currentHabitat.animals().size();

            for (Tree currentTree : currentHabitat.trees()) {
                workInHabitat += currentTree.totalCleaningEffort();
            }

            satisfactionPerHabitat += (workInHabitat / currentHabitat.careTakers().size());
        }

        return (300 - satisfactionPerHabitat);
    }

    /**
     * Returns the vet in the format:
     * tipo|id|nome|idResponsabilidades
     * If the vet doesn't have responsibilities, it's in this format:
     * tipo|id|nome
     * 
     * @return the vaccine registry in format // TODO: should it be the format
     *         itself?
     */
    @Override
    public String toString() {
        StringBuilder responsibilities = new StringBuilder();

        if (_responsibilities != null && !_responsibilities.isEmpty()) {
            responsibilities.append(String.join(",", _responsibilities.keySet()));
        }

        return String.format("TRT|%s|%s|%s", id(), name(), responsibilities.toString());
    }
}
