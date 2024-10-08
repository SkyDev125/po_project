package hva.core;

import hva.core.enumerator.SeasonType;
import hva.core.exception.*;

import java.io.*;

/**
 * Class representing the manager of this application. It manages the current zoo hotel.
 **/
public class HotelManager {
  /** The current zoo hotel */
  private Hotel _hotel = new Hotel();
  private String _filePath;

  public SeasonType progressSeason() {
    return _hotel.progressSeason();
  }

  public float satisfaction() {
    return _hotel.satisfaction();
  }

  /**
   * Saves the serialized application's state into the file associated to the current network.
   *
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   **/
  public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
    saveAs(_filePath);
  }

  /**
   * Saves the serialized application's state into the specified file. The current network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   **/
  public void saveAs(String filePath)
      throws FileNotFoundException, MissingFileAssociationException, IOException {

    if (filePath.isEmpty()) {
      throw new MissingFileAssociationException();
    }

    try (FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
      out.writeObject(_hotel);
    }
  }

  /**
   * @param filename name of the file containing the serialized application's state to load.
   * @throws UnavailableFileException if the specified file does not exist or there is an error
   *         while processing this file.
   **/
  public void load(String filePath) throws UnavailableFileException {
    try (FileInputStream fileIn = new FileInputStream(filePath);
        ObjectInputStream in = new ObjectInputStream(fileIn)) {
      _hotel = (Hotel) in.readObject();
      _filePath = filePath;
    } catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filePath);
    }
  }

  /**
   * Read text input file and initializes the current zoo hotel (which should be empty) with the
   * domain entitiesi representeed in the import file.
   *
   * @param filename name of the text input file
   * @throws ImportFileException if some error happens during the processing of the import file.
   **/
  public void importFile(String filename) throws ImportFileException {
    try {
      _hotel.importFile(filename);
    } catch (IOException | UnrecognizedEntryException e) {
      throw new ImportFileException(filename, e);
    }
  }

  /**
   * Returns the zoo hotel managed by this instance.
   *
   * @return the current zoo hotel
   **/
  public final Hotel getHotel() {
    return _hotel;
  }
}
