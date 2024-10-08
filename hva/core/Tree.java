package hva.core;

import java.io.Serial;
import java.io.Serializable;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public abstract class Tree implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String _id;
  private final String _name;
  private int _age;
  private final SeasonType _birthSeason;
  private final int _cleaningDifficulty;
  private final Hotel _hotel;

  /*
   * <------------------------ Constructor ------------------------>
   */

  public Tree(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
    _id = id;
    _name = name;
    _age = age;
    _cleaningDifficulty = cleaningDifficulty;
    _hotel = hotel;
    _birthSeason = _hotel.season();
  }

  /*
   * <------------------------ Gets ------------------------>
   */

  /**
   * Returns the id of the tree in this instance.
   * 
   * @return the id of the tree
   */
  public String id() {
    return _id;
  }

  /**
   * Returns the name of the tree in this instance.
   * 
   * @return the name of the tree
   */
  protected String name() {
    return _name;
  }

  /**
   * Returns the age of the tree in this instance.
   * 
   * @return the age of the tree
   */
  protected int age() {
    return _age;
  }

  /**
   * Returns the cleaning difficulty of the tree in this instance.
   * 
   * @return the cleaning difficulty of the tree
   */
  protected int cleaningDifficulty() {
    return _cleaningDifficulty;
  }

  /**
   * Returns the hotel of the tree in this instance.
   * 
   * @return the hotel of the tree
   */
  protected Hotel hotel() {
    return _hotel;
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Returns the total cleaning effort needed to care for the tree in this instance.
   * 
   * @return the total cleaning effort of the tree
   */
  int totalCleaningEffort() {
    return (int) (_cleaningDifficulty * seasonalEffort() * Math.log(_age * 1));
  }

  void grow() {
    if (_birthSeason == _hotel.season()) {
      _age++;
    }
  }

  protected abstract int seasonalEffort();

  protected abstract LeafState leafState();

  @Override
  public abstract String toString();

  /**
   * Returns true if the tree in this instance and the given tree are equal.
   * 
   * @param tree to be compared
   * @return true or false // TODO: what do I put here?
   */
  public boolean equals(Tree tree) {
    // TODO: define equals method
    return _id.equals(tree.id());
  }

  /**
   * INCOMPLETO Returns the hashcode of the vaccine in this instance.
   * 
   * @return the hashcode of the vaccine
   */
  public int hashCode() {
    // TODO: implement hashCode
    return 20;
  }
}
