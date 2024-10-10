package hva.core;

import java.io.Serial;
import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.List;

public class Vaccine implements Serializable, Comparable<Vaccine> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private int _applyCount;
  private final Map<String, Species> _species = new CaseInsensitiveHashMap<Species>();

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
   * @return the vaccines in format
   */
  @Override
  public String toString() {
    String species = "";

    if (!_species.isEmpty()) {
      species = "|" + String.join(",", _species.values().stream().map(Species::id).toList());
    }

    return String.format("VACINA|%s|%s|%d%s", _id, _name, _applyCount, species.toString());
  }

  /**
   * Default method of comparison between two vaccines.
   * 
   * <p>
   * This method compares two {@link Vaccine}s by their identifier in a case-insensitive manner.
   * returning a negative integer, zero, or a positive integer as this object is less than, equal
   * to, or greater than the specified object.
   * 
   * @param vaccine the vaccine to be compared.
   * @return an integer value representing the comparison between the two vaccines.
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Vaccine vaccine) {
    return _id.compareToIgnoreCase(vaccine.id());
  }
}
