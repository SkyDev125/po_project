package hva.app.main;

import hva.core.*;

import hva.core.HotelManager;
import hva.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import java.io.FileOutputStream;
import java.io.IOException;
// FIXME add more imports if needed
import java.io.ObjectOutputStream;

/**
 * Save to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<HotelManager> {
  DoSaveFile(HotelManager receiver) {
    super(Label.SAVE_FILE, receiver, r -> r.getHotel() != null);
  }

  @Override
  protected final void execute() {
    // FIXME implement command and create a local Form
    try (FileOutputStream fileOut = new FileOutputStream(_receiver.filePath());
        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

      for (Animal animal : _receiver.getHotel().animals()) {
        out.writeObject(animal);
      }

      for (Worker worker : _receiver.getHotel().workers()) {
        out.writeObject(worker);
      }

      for (Habitat habitat : _receiver.getHotel().habitats()) {
        out.writeObject(habitat);
      }

      for (Vaccine vaccine : _receiver.getHotel().vaccines()) {
        out.writeObject(vaccine);
      }

      for (VaccineRegistry vaccineRegistry : _receiver.getHotel().vaccineRegistry()) {
        out.writeObject(vaccineRegistry);
      }

      } catch (IOException e) {
        e.printStackTrace();
      }
  }
}
