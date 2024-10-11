package hva.core;

import java.io.Serial;
import java.io.Serializable;

import hva.core.exception.HabitatNotFoundException;
import hva.core.exception.SpeciesNotFoundException;

/**
 * Class representing a worker in the zoo hotel.
 * 
 * <p>
 * A worker is defined by its id, name and hotel. On top of that, he keeps record of its responsabilities, which are species to care for, 
 * and vaccine registries of the vaccines he has applied.
 * 
 * <p>
 * The worker can calculate its {@link #satisfaction()}.
 * 
 * @see Vet
 * @see CareTaker
 */
abstract public class Worker implements Serializable, Comparable<Worker> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private final Hotel _hotel;

  /*
   * <------------------------ Constructor ------------------------>
   */

  /**
   * The constructor of this worker.
   * 
   * @param id the identifier of this worker
   * @param name the name of this worker
   * @param hotel the hotel of this worker
   * 
   * @see Hotel
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
   * Retrieves the identifier of this worker.
   * 
   * <p>
   * The identifier of this worker is an unique String by which this worker is identified.
   *
   * @return the identifier of this worker
   */
  String id() {
    return _id;
  }

  /**
   * Retrieves the name of this worker.
   * 
   * <p>
   * The name of this worker is a non unique String.
   *
   * @return the name of this worker
   */
  String name() {
    return _name;
  }

  /**
   * Retrieves the hotel of this worker.
   *
   * @return the hotel of this worker
   */
  Hotel hotel() {
    return _hotel;
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  /**
   * This abstract method adds a responsibility to this worker, depends on the type of worker.
   * 
   * @param id the identifier of the responsibility
   * 
   * @throws SpeciesNotFoundException If the given species does not exist.
   * @throws HabitatNotFoundException If the given habitat does not exist.
   * 
   * @see Vet#addResponsibility(String)
   * @see CareTaker#addResponsibility(String)
   * @see Species
   * @see Habitat
   */
  abstract void addResponsibility(String id)
      throws SpeciesNotFoundException, HabitatNotFoundException;

  /**
   * This abstract method removes a responsibility to this worker, depends on the type of worker.
   * 
   * @param id the identifier of the responsibility
   * 
   * @throws SpeciesNotFoundException If the given species does not exist.
   * @throws HabitatNotFoundException If the given habitat does not exist.
   * 
   * @see Vet#removeResponsibility(String)
   * @see CareTaker#removeResponsibility(String)
   * @see Species
   * @see Habitat
   */
  abstract void removeResponsibility(String id)
      throws SpeciesNotFoundException, HabitatNotFoundException;

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Calculates the satisfaction of this worker, the formula depends on the type of worker.

   * @return the satisfaction of this worker
   * 
   * @see Vet#satisfaction()
   * @see CareTaker#satisfaction()
   */
  abstract float satisfaction();

  /**
   * Returns a String representation of this worker, the "tipo" depends on the type of worker.
   * 
   * <p>
   * This method follows the format:
   * <p>
   * tipo|id|nome|idResponsabilidades
   * <p>
   * If the worker does not have any responsibility, the format is:
   * <p>
   * tipo|id|nome
   * 
   * @return the String representation of this worker
   * 
   * @see Object#toString()
   * @see Vet#toString()
   * @see CareTaker#toString()
   * @see Species
   * @see Habitat
   */
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
    return _id.compareToIgnoreCase(worker.id());
  }
}
