package hva.core;

import java.util.HashMap;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;

import hva.core.enumf.VaccineDamage;

public class Vet extends Worker {

    @Serial
    private static final long serialVersionUID = 1L;

    private HashMap<String, Species> _responsibilities;
    private ArrayList<VaccineRegistry> _vaccineRegistry;

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
     * Returns the collection of vaccine registries that the vet in this instance has apllied.
     * @return the collection of vaccine registries
     */
    public Collection<VaccineRegistry> vaccineRegistry() {
        return Collections.unmodifiableList(_vaccineRegistry);
    }

    /*
     * <------------------------ Others ------------------------>
     */

    /**
     * Adds a species as a responsability for the vet in this instance to care.
     * @param id of the species
     */
    protected void addResponsibility(String id) {
        _responsibilities.put(id, hotel().speciesExists(id));
    }

    /**
     * Removes a species as a responsability for the vet in this instance to care.
     * @param id of the species
     */
    protected void removeResponsibility(String id) {
        _responsibilities.remove(id);
    }

    /**
     * Calculates the satisfaction of the vet in this instance.
     * @return the satisfaction
     */
    protected int satisfaction() {
        int satisfactionPerSpecies = 0;

        for (HashMap.Entry<String, Species> entry : _responsibilities.entrySet()) {
            Species currentSpecies = entry.getValue();

            satisfactionPerSpecies += (currentSpecies.animalCount() / currentSpecies.vetCount());
        }

        return (20 - satisfactionPerSpecies);
    }

    /**
     * Registers the vaccination of an animal with a vaccine by the vet in this instance.
     * @param animal that was vaccinated
     * @param vaccine that was applied
     * @return the vaccine registry
     */
    protected VaccineRegistry vaccinate(Animal animal, Vaccine vaccine) {
        VaccineDamage vaccineDamage = calculateVaccineDamage(animal, vaccine);

        VaccineRegistry vaccineRegistry = new VaccineRegistry(vaccine, this, animal, vaccineDamage);

        addVaccineRegistry(vaccineRegistry);

        return vaccineRegistry;
    }

    /**
     * Returns the vaccine damage dealt by the vet in this instance to an animal by a vaccine application.
     * @param animal that was vaccinated
     * @param vaccine that was applied
     * @return the vaccine damage dealt
     */
    private VaccineDamage calculateVaccineDamage(Animal animal, Vaccine vaccine) {
        int damage = 0;

        // defining fixed values for later
        int length = animal.species().name().length();

        HashMap<Character, Integer> speciesCharCount = new HashMap<Character, Integer>();

        for (char speciesChar : animal.species().name().toCharArray()) {
            speciesCharCount.put(speciesChar, speciesCharCount.getOrDefault(speciesChar, 0) + 1);
        }

        // comparison of the species to each species of the vaccine
        for (Species vaccineSpecies : vaccine.species()) {
            // checking if the vaccine is for this species
            if (animal.species().equals(vaccineSpecies)) {
                return VaccineDamage.NORMAL;
            }

            // calculation of the largest name
            int nameLength = Math.max(vaccineSpecies.name().length(), length);

            // calculation of the characters in common
            int nameSameChar = 0;
            HashMap<Character, Integer> vaccineCharCount = new HashMap<Character, Integer>();

            for (char vaccineChar : vaccineSpecies.name().toCharArray()) {
                vaccineCharCount.put(vaccineChar, vaccineCharCount.getOrDefault(vaccineChar, 0) + 1);
            }

            for (HashMap.Entry<Character, Integer> entry : speciesCharCount.entrySet()) {
                char speciesChar = entry.getKey();

                if (vaccineCharCount.containsKey(speciesChar)) {
                    nameSameChar += Math.min(entry.getValue(), vaccineCharCount.get(speciesChar));
                }
            }

            // calculation of the damage
            damage = Math.max(damage, (nameLength - nameSameChar));
        }

        if (damage == 0) {
            return VaccineDamage.CONFUSION;
        }

        if (damage < 5) {
            return VaccineDamage.ACCIDENT;
        }

        return VaccineDamage.ERROR;
    }

    /**
     * Adds the vaccine registry to the lists and maps of the vet in the instance, the animal and the hotel.
     * @param vaccineRegistry to be added
     */
    private void addVaccineRegistry(VaccineRegistry vaccineRegistry) {
        _vaccineRegistry.add(vaccineRegistry);
        vaccineRegistry.animal().addVaccineRegistration(vaccineRegistry);
        // TODO: criar metodo para adicionar vaccineRegistry ao hotel
    }

    /**
     * Returns the vet in the format:
     * tipo|id|nome|idResponsabilidades
     * If the vet doesn't have responsibilities, it's in this format:
     * tipo|id|nome
     * @return the vaccine registry in format // TODO: should it be the format itself?
     */
    public String toString() {
        String responsibilities = "";

        if (responsibilities != null) {

            responsibilities = "|";

            for (HashMap.Entry<String, Species> set : _responsibilities.entrySet()) {
                responsibilities += set.getKey() + ",";
            }
            responsibilities.substring(0, responsibilities.length() - 1);
        }
        return "VET|" + super.id() + "|" + super.name() + responsibilities;
    }
}
