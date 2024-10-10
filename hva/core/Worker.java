package hva.core;

import java.io.Serial;
import java.io.Serializable;

import hva.core.exception.HabitatNotFoundException;
import hva.core.exception.SpeciesNotFoundException;

abstract public class Worker implements Serializable, Comparable<Worker> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private final Hotel _hotel;

  /*
   * <------------------------ Constructor ------------------------>
   */

  Worker(String id, String name, Hotel hotel) {
    _id = id;
    _name = name;
    _hotel = hotel;
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  /**
   * Returns the id of the worker in this instance.
   * 
   * @return the id of the worker
   */
  String id() {
    return _id;
  }

  /**
   * Returns the name of the worker in this instance.
   * 
   * @return the name of the worker
   */
  String name() {
    return _name;
  }

  /**
   * Returns the hotel of the worker in this instance.
   * 
   * @return the hotel of the worker
   */
  Hotel hotel() {
    return _hotel;
  }

  /*
   * <------------------------ Others ------------------------>
   */

  abstract void addResponsibility(String id)
      throws SpeciesNotFoundException, HabitatNotFoundException;

  abstract void removeResponsibility(String id)
      throws SpeciesNotFoundException, HabitatNotFoundException;

  abstract float satisfaction();

  @Override
  abstract public String toString();

  /**
   * Default method of comparison between two workers.
   * 
   * <p>
   * This method compares two {@link Worker}s by their identifier in a case-insensitive manner.
   * returning a negative integer, zero, or a positive integer as this object is less than, equal
   * to, or greater than the specified object.
   * 
   * @param worker the worker to be compared.
   * @return an integer value representing the comparison between the two workers.
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Worker worker) {
    return _id.compareTo(worker.id());
  }
}
