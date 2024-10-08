package hva.core;

import java.io.Serial;
import java.io.Serializable;

import java.util.HashMap;

public class Species implements Serializable, Comparable<Species> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private final HashMap<String, Animal> _animals = new HashMap<String, Animal>();
  private final HashMap<String, Vet> _vets = new HashMap<String, Vet>();

  // constructor
  Species(String id, String name) {
    _id = id;
    _name = name;
  }

  // gets
  String id() {
    return _id;
  }

  String name() {
    return _name;
  }

  // methods
  void addAnimal(Animal animal) {
    _animals.put(animal.id(), animal);
  }

  void addVet(Vet vet) {
    _vets.put(vet.id(), vet);
  }

  void removeVet(Vet vet) {
    _vets.remove(vet.id());
  }

  int animalCount() {
    return _animals.size();
  }

  int vetCount() {
    return _vets.size();
  }

  @Override
  public int compareTo(Species species) {
    return _id.compareTo(species.id()) + _name.compareTo(species.name());
  }
}
