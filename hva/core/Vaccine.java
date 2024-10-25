package hva.core;

import java.io.Serial;
import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.List;

/**
 * Class representing a vaccine in the zoo hotel.
 * 
 * <p>
 * A vaccine is defined by its id, name, count of applications, and keeps record of the species to
 * which it can be safely apllied.
 */
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

  /**
   * The constructor of this vaccine.
   * 
   * @param id the identifier of the vaccine
   * @param name the name of the vaccine
   * @param species the species to which the vaccine can be safely applied
   * 
   * @see Vaccine
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
   * Retrieves the identifier of this vaccine.
   * 
   * <p>
   * The identifier of this vaccine is an unique String by which this vaccine is identified.
   *
   * @return the identifier of this vaccine
   */
  String id() {
    return _id;
  }

  /**
   * Retrieves all the species to which this vaccine can be safely applied to.
   * 
   * <p>
   * This method provides a way to access the collection of species without allowing modifications
   * to the underlying collection. The returned collection is a read-only view, and any attempts to
   * modify it will result in an {@code UnsupportedOperationException}.
   * 
   * @return an unmodifiable collection of the species
   * 
   * @see Collections#unmodifiableCollection(Collection)
   * @see Species
   */
  Collection<Species> species() {
    return Collections.unmodifiableCollection(_species.values());
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Increases the count of applications of this vaccine.
   */
  void apply() {
    _applyCount++;
  }

  /**
   * Returns a String representation of this vaccine.
   * 
   * <p>
   * This method follows the format:
   * <p>
   * VACINA|idVacina|nomeVacina|numeroAplicacoes|especies
   * 
   * @return the String representation of this vaccine
   * 
   * @see Object#toString()
   * @see Species
   */
  @Override
  public String toString() {
    String species = "";

    if (!_species.isEmpty()) {
      species =
          "|" + String.join(",", _species.values().stream().sorted().map(s -> s.id()).toList());
    }

    return String.format("VACINA|%s|%s|%d%s", _id, _name, _applyCount, species.toString());
  }

  /**
   * Default method of comparison between two vaccines.
   * 
   * <p>
   * This method compares two vaccines by their identifier in a case-insensitive manner. Returns a
   * negative integer, zero, or a positive integer as this object is less than, equal to, or greater
   * than the specified object.
   * 
   * @param vaccine The vaccine to be compared.
   * 
   * @return An integer value representing the comparison between the two vaccines.
   * 
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Vaccine vaccine) {
    return _id.compareToIgnoreCase(vaccine.id());
  }
}
