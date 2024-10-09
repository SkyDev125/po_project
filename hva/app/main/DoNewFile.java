package hva.app.main;

import java.io.IOException;
import java.io.FileNotFoundException;

import hva.core.HotelManager;

import hva.core.exception.MissingFileAssociationException;

import pt.tecnico.uilib.forms.Form;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for creating a new zoo hotel.
 **/
class DoNewFile extends Command<HotelManager> {
  DoNewFile(HotelManager receiver) {
    super(Label.NEW_FILE, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    try {

      // Verify if the hotel has been modified and save it if necessary
      if (_receiver.hotelModified() && Form.confirm(Prompt.saveBeforeExit())) {
        _receiver.save();
      }
      _receiver.create();

    } catch (MissingFileAssociationException e) {

      // Retry with a new file path
      try {
        _receiver.saveAs(Form.requestString(Prompt.saveAs()));
      } catch (MissingFileAssociationException | IOException e1) {
        e1.printStackTrace();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
