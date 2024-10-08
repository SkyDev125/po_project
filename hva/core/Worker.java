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
   * Returns true if the worker in this instance is equal to the given worker.
   * 
   * @param worker to be compared
   * @return true or false // TODO: what do I put here?
   */
  public boolean equals(Worker worker) {
    return (_id == worker.id());
  }

  /**
   * INCOMPLETO Returns the hashcode of the worker in this instance.
   * 
   * @return the hashcode of the worker
   */
  public int hashCode() {
    // TODO: ainda nao vi como se faz, vou tratar do resto primeiro, se precisares
    // avisa
    return 20;
  }

  @Override
  public int compareTo(Worker worker) {
    return _id.compareTo(worker.id());
  }
}
