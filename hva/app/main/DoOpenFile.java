package hva.app.main;

import hva.core.HotelManager;

import hva.core.exception.MissingFileAssociationException;
import hva.core.exception.UnavailableFileException;

import java.io.FileNotFoundException;
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
    addStringField("filePath", Prompt.openFile());
  }

  @Override
  protected final void execute() throws CommandException {
    String filePath = _receiver.filePath();
    while (true) {
      try {
        if (_receiver.hotelModified() && Form.confirm(Prompt.saveBeforeExit())) {
          _receiver.saveAs(filePath);
        }
        _receiver.load(stringField("filePath"));
        break;
      } catch (MissingFileAssociationException | FileNotFoundException e) {
        filePath = Form.requestString(Prompt.saveBeforeExit());
      } catch (IOException | UnavailableFileException e) {
        throw new FileOpenFailedException(e);
      }
    }
  }
}
