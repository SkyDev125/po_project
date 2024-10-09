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
    String filePath = _receiver.filePath();
    while (true) {
      try {
        if (_receiver.hotelModified() && Form.confirm(Prompt.saveBeforeExit())) {
          _receiver.saveAs(filePath);
        }
        _receiver.create();
        break;
      } catch (MissingFileAssociationException | FileNotFoundException e) {
        filePath = Form.requestString(Prompt.saveAs());
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
    }
  }
}
