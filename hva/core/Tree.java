package hva.core;

import java.io.Serial;
import java.io.Serializable;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

public abstract class Tree implements Serializable, Comparable<Tree> {

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

  Tree(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
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
  String id() {
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
  double totalCleaningEffort() {
    return _cleaningDifficulty * seasonalEffort() * Math.log(_age + 1);
  }

  void grow() {
    if (_birthSeason == _hotel.season()) {
      _age++;
    }
  }

  abstract int seasonalEffort();

  abstract LeafState leafState();

  @Override
  public abstract String toString();

  /**
   * Default method of comparison between two Trees.
   * 
   * <p>
   * This method compares two {@link Tree}s by their identifier in a case-insensitive manner.
   * returning a negative integer, zero, or a positive integer as this object is less than, equal
   * to, or greater than the specified object.
   * 
   * @param tree the tree to be compared.
   * @return an integer value representing the comparison between the two Trees.
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Tree tree) {
    return _id.compareToIgnoreCase(tree.id());
  }
}
