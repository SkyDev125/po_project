package hva.core;

import java.util.*;
import java.io.*;

public class Animal implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    private final String _id;
    private final String _name;
    private Species _species;
    private Habitat _habitat;
    private ArrayList<VaccineRegistry> _vaccineRegistry;

    // constructor
    public Animal(String id, String name, Species species, Habitat habitat) {
        _id = id;
        _name = name;
        _species = species;
        _habitat = habitat;
        _vaccineRegistry = new ArrayList<VaccineRegistry>();
    }

    // gets
    public String id() {
        return _id;
    }

    public String name() {
        return _name;
    }

    public Collection<VaccineRegistry> vaccineRegistry() {
        return Collections.unmodifiableCollection(_vaccineRegistry);
    }

    // method
    protected void transferAnimal(Habitat habitat) {
        _habitat.removeAnimal(this);
        habitat.addAnimal(this);
        _habitat = habitat;
    }

    protected void addVaccineRegistration(VaccineRegistry vaccineReg) {
        _vaccineRegistry.add(vaccineReg);
    }

    protected int satisfaction() {
        return 20; // calcular
    }

    public String toString() {
        String health = "VOID";

        if (_vaccineRegistry != null) {
            health = "";

            for (VaccineRegistry vaccineRegistry : _vaccineRegistry) {
                health += vaccineRegistry.vaccineDamage() + ","; // ver forma diferente de fazer
            }

            health.substring(0, health.length() - 1);
        }

        return "ANIMAL|" + _id + "|" + _name + "|" + _species.id() + "|" + health + "|" + _habitat.id();
    }
}