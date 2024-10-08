package hva.core;

import java.util.HashMap;
import java.util.List;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import hva.core.enumerator.VaccineDamage;
import hva.core.exception.SpeciesNotFoundException;
import hva.core.exception.WorkerNotAuthorizedException;

public class Vet extends Worker {

    @Serial
    private static final long serialVersionUID = 1L;

    private HashMap<String, Species> _responsibilities = new HashMap<String, Species>();
    private ArrayList<VaccineRegistry> _vaccineRegistry = new ArrayList<VaccineRegistry>();

    /*
     * <------------------------ Constructor ------------------------>
     */

    public Vet(String id, String name, Hotel hotel) {
        super(id, name, hotel);
        _responsibilities = new HashMap<String, Species>();
    }

    /*
     * <------------------------ Gets ------------------------>
     */

    /**
     * Returns the collection of vaccine registries that the vet in this instance
     * has apllied.
     * 
     * @return the collection of vaccine registries
     */
    public List<VaccineRegistry> vaccineRegistry() {
        return Collections.unmodifiableList(_vaccineRegistry);
    }

    /*
     * <------------------------ Others ------------------------>
     */

    /**
     * Adds a species as a responsability for the vet in this instance to care.
     * 
     * @param id of the species
     */
    protected void addResponsibility(String id) throws SpeciesNotFoundException {
        // No need to check if it's already there, as the species object is always the
        // same.
        _responsibilities.put(id, hotel().speciesExistsWithException(id));
    }

    /**
     * Removes a species as a responsability for the vet in this instance to care.
     * 
     * @param id of the species
     */
    protected void removeResponsibility(String id) throws SpeciesNotFoundException {
        if (_responsibilities.remove(id) == null) {
            throw new SpeciesNotFoundException(id);
        }
    }

    /**
     * Calculates the satisfaction of the vet in this instance.
     * 
     * @return the satisfaction
     */
    protected float satisfaction() {
        int satisfactionPerSpecies = 0;

        for (HashMap.Entry<String, Species> entry : _responsibilities.entrySet()) {
            Species currentSpecies = entry.getValue();

            satisfactionPerSpecies += (currentSpecies.animalCount() / currentSpecies.vetCount());
        }

        return (20 - satisfactionPerSpecies);
    }

    /**
     * Registers the vaccination of an animal with a vaccine by the vet in this
     * instance.
     * 
     * @param animal  that was vaccinated
     * @param vaccine that was applied
     * @return the vaccine registry
     * @throws WorkerNotAuthorizedException
     */
    protected VaccineRegistry vaccinate(Animal animal, Vaccine vaccine) throws WorkerNotAuthorizedException {
        if (!_responsibilities.containsValue(animal.species())) {
            throw new WorkerNotAuthorizedException(id(), animal.species().id());
        }

        VaccineDamage vaccineDamage = calculateVaccineDamage(animal, vaccine);
        VaccineRegistry vaccineRegistry = new VaccineRegistry(vaccine, this, animal, vaccineDamage);
        addVaccineRegistry(vaccineRegistry);
        return vaccineRegistry;
    }

    private int countSameChars(HashMap<Character, Integer> animalSpeciesName, char[] speciesName) {
        int count = 0;

        for (char speciesChar : speciesName) {
            if (animalSpeciesName.containsKey(speciesChar) && animalSpeciesName.get(speciesChar) > 0) {
                count++;
                animalSpeciesName.put(speciesChar, animalSpeciesName.get(speciesChar) - 1);
            }
        }

        return count;
    }

    /**
     * Returns the vaccine damage dealt by the vet in this instance to an animal by
     * a vaccine application.
     * 
     * @param animal  that was vaccinated
     * @param vaccine that was applied
     * @return the vaccine damage dealt
     */
    private VaccineDamage calculateVaccineDamage(Animal animal, Vaccine vaccine) {

        // Early Check for correct Vaccines
        if (vaccine.species().contains(animal.species())) {
            return VaccineDamage.NORMAL;
        }

        int damage = 0;
        char[] animalSpeciesName = animal.species().name().toCharArray();
        HashMap<Character, Integer> animalSpeciesNameCharCount = new HashMap<Character, Integer>();

        // Count the number of each character in the animal species name
        for (char speciesChar : animalSpeciesName) {
            animalSpeciesNameCharCount.put(
                    speciesChar,
                    animalSpeciesNameCharCount.getOrDefault(speciesChar, 0) + 1);
        }

        // Calculate the max damage
        for (Species species : vaccine.species()) {
            char[] vaccineSpeciesName = species.name().toCharArray();
            int tempDamage = Math.max(vaccineSpeciesName.length, animalSpeciesName.length)
                    - countSameChars(animalSpeciesNameCharCount, vaccineSpeciesName);
            damage = Math.max(tempDamage, damage);
        }

        // Convert damage to Enum
        switch (damage) {
            case 0:
                return VaccineDamage.CONFUSION;
            case 1:
            case 2:
            case 3:
            case 4:
                return VaccineDamage.ACCIDENT;
            default:
                return VaccineDamage.ERROR;
        }
    }

    /**
     * Adds the vaccine registry to the lists and maps of the vet in the instance,
     * the animal and the hotel.
     * 
     * @param vaccineRegistry to be added
     */
    private void addVaccineRegistry(VaccineRegistry vaccineRegistry) {
        _vaccineRegistry.add(vaccineRegistry);
        vaccineRegistry.animal().addVaccineRegistration(vaccineRegistry);
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

        return String.format("VET|%s|%s|%s", super.id(), super.name(), responsibilities.toString());
    }
}
