package hva.core;

import java.io.Serial;
import java.io.Serializable;

import hva.core.enumerator.LeafState;
import hva.core.enumerator.SeasonType;

/**
 * Abstract class representing a tree in the zoo hotel.
 * 
 * <p>
 * A tree is defined by its id, name, age, birth season, cleaning dificulty and hotel.
 * 
 * <p>
 * The worker can {@link #grow()} and calculate its {@link #totalCleaningEffort()},
 * {@link #seasonalEffort()} and {@link #leafState()}.
 * 
 * @see Evergreen
 * @see Deciduos
 */
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

  /**
   * The constructor of this tree.
   * 
   * @param id the identifier of this tree
   * @param name the name of this tree
   * @param age the age of this tree
   * @param cleaningDifficulty the cleaning dificulty of this tree
   * @param hotel the hotel of this tree
   * 
   * @see Evergreen#Evergreen(String, String, int, int, Hotel)
   * @see Deciduos#Deciduos(String, String, int, int, Hotel)
   * @see Hotel
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
   * Retrieves the identifier of this tree.
   * 
   * <p>
   * The identifier of this tree is an unique String by which this tree is identified.
   *
   * @return the identifier of this tree
   */
  protected String id() {
    return _id;
  }

  /**
   * Retrieves the name of this tree.
   * 
   * <p>
   * The tree of this worker is a non unique String.
   *
   * @return the name of this tree
   */
  protected String name() {
    return _name;
  }

  /**
   * Retrieves the age of this tree.
   * 
   * @return the age of this tree
   */
  protected int age() {
    return _age;
  }

  /**
   * Retrieves the cleaning difficulty of this tree.
   * 
   * @return the cleaning difficulty of this tree
   */
  protected int cleaningDifficulty() {
    return _cleaningDifficulty;
  }

  /**
   * Retrieves the hotel of this tree.
   *
   * @return the hotel of this tree
   */
  protected Hotel hotel() {
    return _hotel;
  }

  /*
   * <------------------------ Others ------------------------>
   */

  /**
   * Returns the total cleaning effort needed to care for this tree.
   * 
   * @return the total cleaning effort of this tree
   */
  double totalCleaningEffort() {
    return _cleaningDifficulty * seasonalEffort() * Math.log(_age + 1);
  }

  /**
   * Increases the age of this tree.
   * 
   * <p>
   * This method increases the age of the tree yearly in the season of its birth.
   * 
   * @see SeasonType
   */
  void grow() {
    if (_birthSeason == _hotel.season()) {
      _age++;
    }
  }

  /**
   * Returns the seasonal effort of this tree.
   * 
   * @return the seasonal effort of this tree
   * 
   * @see Evergreen#seasonalEffort()
   * @see Deciduos#seasonalEffort()
   * @see SeasonType
   */
  abstract int seasonalEffort();

  /**
   * Returns the leaf state of this tree according to the current season, depends on the type of
   * tree.
   * 
   * @return the leaf state of this tree
   * 
   * @see Evergreen#leafState()
   * @see Deciduos#leafState()
   * @see SeasonType
   */
  abstract LeafState leafState();

  /**
   * Returns a String representation of this tree, the "tipoArvore" depends on the type of tree.
   * 
   * <p>
   * This method follows the format:
   * <p>
   * ARVORE|idArvore|nomeArvore|idadeArvore|dificuldadeBaseLimpeza|tipoArvore|cicloBiologico
   * 
   * @return the String representation of this tree
   * 
   * @see Object#toString()
   * @see Evergreen#toString()
   * @see Deciduos#toString()
   */
  @Override
  public abstract String toString();

  /**
   * Default method of comparison between two Trees.
   * 
   * <p>
   * This method compares two trees by their identifier in a case-insensitive manner. Returns a
   * negative integer, zero, or a positive integer as this object is less than, equal to, or greater
   * than the specified object.
   * 
   * @param tree The tree to be compared.
   * 
   * @return An integer value representing the comparison between the two Trees.
   * 
   * @see String#compareToIgnoreCase(String)
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Tree tree) {
    return _id.compareToIgnoreCase(tree.id());
  }
}
