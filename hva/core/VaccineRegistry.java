package hva.core;

import hva.core.enumf.VaccineDamage;


public class VaccineRegistry {
    private final String _id;
    private final String _name;
    private final Animal _animal;
    private final Vaccine _vaccine;
    private final Vet _vet;
    private final VaccineDamage _vaccineDamage;


    public VaccineRegistry(String id, String name, Animal animal, Vaccine vaccine, Vet vet) {
        _id = id;
        _name = name;
        _vaccine = vaccine;
        _vet = vet;

        // comparar animal.name() com nomes na vaccine
    }
    
    public VaccineDamage vaccineDamage() {
        return _vaccineDamage;
    }
}
