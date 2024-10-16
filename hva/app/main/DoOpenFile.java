package hva.app.main;

import hva.core.HotelManager;

import hva.core.exception.MissingFileAssociationException;
import hva.core.exception.UnavailableFileException;

import java.io.IOException;

import hva.app.exception.FileOpenFailedException;

import pt.tecnico.uilib.forms.Form;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<HotelManager> {
  DoOpenFile(HotelManager receiver) {
    super(Label.OPEN_FILE, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    try {

      // Verify if the hotel has been modified and save it if necessary
      if (_receiver.hotelModified() && Form.confirm(Prompt.saveBeforeExit())) {
        _receiver.save();
      }
      _receiver.load(Form.requestString(Prompt.openFile()));

    } catch (MissingFileAssociationException e) {

      // Retry with a new file path
      try {
        _receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
        _receiver.load(Form.requestString(Prompt.openFile()));
      } catch (MissingFileAssociationException | IOException | UnavailableFileException e1) {
        throw new FileOpenFailedException(e1);
      }

    } catch (IOException | UnavailableFileException e) {
      throw new FileOpenFailedException(e);
    }
  }
}
