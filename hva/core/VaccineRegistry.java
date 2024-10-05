package hva.core;

import java.util.HashMap;

import hva.core.enumf.VaccineDamage;


public class VaccineRegistry {
    private final Vaccine _vaccine;
    private final Vet _vet;
    private final Species _species;
    private final Animal _animal;
    private final VaccineDamage _vaccineDamage;

    // constructor
    public VaccineRegistry(Vaccine vaccine, Vet vet, Animal animal, VaccineDamage vaccineDamage) {
        _vaccine = vaccine;
        _vet = vet;
        _species = animal.species();
        _animal = animal;
        _vaccineDamage = vaccineDamage;
    }
    
    // gets
    public Animal animal() {
        return _animal;
    }
    
    public VaccineDamage vaccineDamage() {
        return _vaccineDamage;
    }
}
