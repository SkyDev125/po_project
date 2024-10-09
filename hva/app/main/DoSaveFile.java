package hva.app.main;

import hva.core.HotelManager;

import hva.core.exception.MissingFileAssociationException;

import pt.tecnico.uilib.forms.Form;

import pt.tecnico.uilib.menus.Command;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Save to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<HotelManager> {
  DoSaveFile(HotelManager receiver) {
    super(Label.SAVE_FILE, receiver, r -> r.getHotel() != null);
  }

  @Override
  protected final void execute() {
    try {
      _receiver.save();
    } catch (MissingFileAssociationException | FileNotFoundException e) {

      // Retry with a new file path
      try {
        _receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
      } catch (MissingFileAssociationException | IOException e1) {
        e1.printStackTrace();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
