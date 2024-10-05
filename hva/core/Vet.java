package hva.core;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;

import hva.core.enumf.VaccineDamage;

public class Vet extends Worker {
    private HashMap<String, Species> _responsibilities;
    private ArrayList<VaccineRegistry> _vaccineRegistry;

    // constructor
    public Vet(String id, String name, Hotel hotel) {
        super(id, name, hotel);
        _responsibilities = new HashMap<String, Species>();
    }

    // gets
    public Collection<VaccineRegistry> vaccineRegistry() {
        return Collections.unmodifiableList(_vaccineRegistry);
    }

    // methods
    protected void addResponsibility(String id) {
        _responsibilities.put(id, hotel().speciesExists(id));
    }

    protected void removeResponsibility(String id) {
        _responsibilities.remove(id);
    }

    protected int satisfaction() {
        int satisfactionPerSpecies = 0;

        // calcular satisfacao

        return (20 - satisfactionPerSpecies);
    }

    protected VaccineRegistry vaccinate(Animal animal, Vaccine vaccine) {
        VaccineDamage vaccineDamage = calculateVaccineDamage(animal, vaccine);

        VaccineRegistry vaccineRegistry = new VaccineRegistry(vaccine, this, animal, vaccineDamage);

        addVaccineRegistry(vaccineRegistry);

        return vaccineRegistry;
    }

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

            for (HashMap.Entry<Character, Integer> entry :speciesCharCount.entrySet()) {
                char speciesChar = entry.getKey();
                
                if (vaccineCharCount.containsKey(speciesChar)) {
                    nameSameChar += Math.min(entry.getValue(), vaccineCharCount.get(speciesChar));
                }
            }

            // calculation of the damage
            damage = Math.max(damage, (nameLength - nameSameChar));
        }
        
        if (damage  == 0) {
            return VaccineDamage.CONFUSION;
        }

        if (damage < 5) {
            return VaccineDamage.ACCIDENT;
        }

        return VaccineDamage.ERROR;
    }

    private void addVaccineRegistry(VaccineRegistry vaccineRegistry) {
        _vaccineRegistry.add(vaccineRegistry);
        vaccineRegistry.animal().addVaccineRegistration(vaccineRegistry);
        // TODO: criar metodo para adicionar vaccineRegistry ao hotel
    }

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
