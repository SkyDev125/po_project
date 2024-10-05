package hva.core;

import java.io.Serial;
import java.io.Serializable;

import hva.core.enumf.VaccineDamage;

public class VaccineRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Vaccine _vaccine;
    private final Vet _vet;
    private final Species _species;
    private final Animal _animal;
    private final VaccineDamage _vaccineDamage;

    /*
     * <------------------------ Constructor ------------------------>
     */
    
    public VaccineRegistry(Vaccine vaccine, Vet vet, Animal animal, VaccineDamage vaccineDamage) {
        _vaccine = vaccine;
        _vet = vet;
        _species = animal.species();
        _animal = animal;
        _vaccineDamage = vaccineDamage;
    }

    /*
     * <------------------------ Gets ------------------------>
     */

    /**
     * Returns the animal whose vaccination is registered in this instance.
     * @return the vaccinated animal 
     */
    public Animal animal() {
        return _animal;
    }

    /**
     * Returns the damage dealt by the vaccine that is registered in this instance
     * @return the vaccine damage dealt
     */
    public VaccineDamage vaccineDamage() {
        return _vaccineDamage;
    }

    /*
     * <------------------------ Others ------------------------>
     */

    /**
     * Returns the vaccine registry in the format:
     * REGISTO-VACINA|idVacina|idVeterinario|idEspecie
     * @return the vaccine registry in format // TODO: should it be the format itself?
     */
    public String toString() {
        return "REGISTO-VACINA|" + _vaccine.id() + "|" + _vet.id() + "|" + _species.id();
    }
}
