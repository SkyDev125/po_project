package hva.core;

import java.io.Serial;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Field;

import java.util.Map;

/**
 * Class representing an species of animals in the zoo hotel.
 * 
 * <p>
 * A species is defined by its id, name, and keeps record of its {@link Animal}s and {@link Vet}s.
 * 
 * <p>
 * The species can return its {@link #animalCount()} and {@link #vetCount()}.
 */
public class Species implements Serializable, Comparable<Species> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private final Map<String, Animal> _animals = new CaseInsensitiveHashMap<Animal>();
  private final Map<String, Vet> _vets = new CaseInsensitiveHashMap<Vet>();

  /*
   * <------------------------ Constructor ------------------------>
   */

  /**
   * Constructor which creates this species.
   * 
   * @param id the identifier of this species
   * @param name the name of this species
   */
  Species(String id, String name) {
    _id = id;
    _name = name;
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  /**
   * Retrieves the identifier of this species.
   * 
   * <p>
   * The identifier of this species is an unique String by which this species is identified.
   *
   * @return the identifier of this species
   */
  String id() {
    return _id;
  }

  /**
   * Retrieves the name of this species.
   * 
   * <p>
   * The name of this species is an unique String.
   *
   * @return the name of this species
   */
  String name() {
    return _name;
  }

  /*
   * <------------------------ Sets ------------------------>
   */

  /**
   * Adds an animal to this species.
   * 
   * @param animal The animal to be added.
   * 
   * @see Hotel#addAnimal(String, String, String, String)
   * @see Animal
   */
  void addAnimal(Animal animal) {
    _animals.put(animal.id(), animal);
  }

  /**
   * Adds a vet to this species, becoming his responsibility.
   * 
   * @param vet The vet to be added.
   * 
   * @see Vet#addResponsibility(String)
   * @see Vet
   */
  void addVet(Vet vet) {
    _vets.put(vet.id(), vet);
  }

  /**
   * Removes a vet from this species, it will no longer be his responsibility.
   * 
   * @param vet The vet to be removed.
   * 
   * @see Vet#removeResponsibility(String)
   * @see Vet
   */
  void removeVet(Vet vet) {
    _vets.remove(vet.id());
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Counts the number of animals of this species.
   * 
   * @return the number of animals of this species
   * 
   * @see Animal
   */
  int animalCount() {
    return _animals.size();
  }

  /**
   * Counts the number of vets responsible for this species.
   * 
   * @return the number of vets of this species
   * 
   * @see Vet
   */
  int vetCount() {
    return _vets.size();
  }

  /**
   * Default method of comparison between two species.
   * 
   * <p>
   * This method compares two species by their identifier in a case-insensitive manner. Returns a
   * negative integer, zero, or a positive integer as this object is less than, equal to, or greater
   * than the specified object.
   * 
   * @param species The species to be compared.
   * 
   * @return An integer value representing the comparison between the two species.
   * 
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Species species) {
    return _id.compareToIgnoreCase(species.id());
  }

  /**
   * Returns a hash code value for this species.
   * 
   * <p>
   * This method returns a hash code value for the object. This method is supported for the benefit
   * of hash tables such as those provided by {@link java.util.HashMap}.
   * 
   * @return A hash code value for this species
   */
  @Override
  public int hashCode() {
    return _id.toLowerCase().hashCode();
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   * 
   * <p>
   * This method compares two species by their identifier in a case-insensitive manner. Returns
   * {@code true} if and only if the argument is not {@code null} and is a {@link Species} object
   * that has the same identifier as this object.
   * 
   * @param obj The reference object with which to compare.
   * 
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Species species = (Species) obj;
    return _id.equalsIgnoreCase(species.id());
  }

  /**
   * Custom deSerialization of the Species class. (necessary due to bug in Java)
   * 
   * <p>
   * This method is called by the serialization runtime when reading the object from the stream. It
   * reads the object's non-transient and non-static fields from the ObjectInputStream.
   * 
   * @param in the ObjectInputStream object used to read the object
   * 
   * @throws ClassNotFoundException if the class of a serialized object cannot be found
   * @throws IOException if an I/O error occurs while reading the object
   * 
   * @see Serializable
   * @see ObjectInputStream
   */
  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    try {
      // Use reflection to set the final field _name
      Field idField = Species.class.getDeclaredField("_id");
      idField.setAccessible(true);
      idField.set(this, in.readObject());

      // Use reflection to set the final field _name
      Field nameField = Species.class.getDeclaredField("_name");
      nameField.setAccessible(true);
      nameField.set(this, in.readObject());

      // Use reflection to set the final field _animals
      Field animalsField = Species.class.getDeclaredField("_animals");
      animalsField.setAccessible(true);
      animalsField.set(this, in.readObject());

      // Use reflection to set the final field _vets
      Field vetsField = Species.class.getDeclaredField("_vets");
      vetsField.setAccessible(true);
      vetsField.set(this, in.readObject());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new IOException("Error deserializing Species", e);
    }
  }

  /**
   * Custom serialization of the Species class. (necessary due to bug in Java)
   * 
   * <p>
   * This method is called by the serialization runtime when writing the object to the stream. It
   * writes the object's non-transient and non-static fields to the ObjectOutputStream.
   * 
   * @param out the ObjectOutputStream object used to write the object
   * 
   * @throws IOException if an I/O error occurs while writing the object
   * 
   * @see Serializable
   * @see ObjectOutputStream
   */
  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeObject(_id);
    out.writeObject(_name);
    out.writeObject(_animals);
    out.writeObject(_vets);
  }

  @Override
  public String toString() {
    return _id;
  }
}
