package hva.core;

import java.io.Serial;
import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Vaccine implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private int _applyCount;
  private final HashMap<String, Species> _species = new HashMap<String, Species>();

  /*
   * <------------------------ Constructor ------------------------>
   */

  Vaccine(String id, String name, List<Species> species) {
    _id = id;
    _name = name;

    species.forEach(currentSpecies -> _species.put(currentSpecies.id(), currentSpecies));
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  /**
   * Returns the id of the vaccine in this instance.
   * 
   * @return the id of the vaccine
   */
  String id() {
    return _id;
  }

  /**
   * Returns the collection of species to which the vaccine in this instance works.
   * 
   * @return the collection of species
   */
  Collection<Species> species() {
    return Collections.unmodifiableCollection(_species.values());
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Increases the count of applications of the vaccine in this instance.
   */
  void apply() {
    _applyCount++;
  }

  /**
   * Returns the vaccine in the format: VACINA|idVacina|nomeVacina|numeroAplicacoes|especies
   * 
   * @return the vaccines in format // TODO: should it be the format itself?
   */
  @Override
  public String toString() {
    StringBuilder species = new StringBuilder();

    if (!_species.isEmpty()) {
      species.append(String.join(",", _species.keySet()));
    }

    return String.format("VACINA|%s|%s|%d|%s", _id, _name, _applyCount, species.toString());
  }

  /**
   * Returns true if the vaccine in this instance is equal to the given vaccine.
   * 
   * @param vaccine to be compared
   * @return true or false // TODO: what do I put here?
   */
  public boolean equals(Vaccine vaccine) {
    // TODO: define equals method

    return _id.equals(vaccine.id());
  }

  /**
   * INCOMPLETO Returns the hashcode of the vaccine in this instance.
   * 
   * @return the hashcode of the vaccine
   */
  public int hashCode() {
    // TODO: ainda nao vi como se faz, vou tratar do resto primeiro, se precisares
    // avisa
    return 20;
  }
}
