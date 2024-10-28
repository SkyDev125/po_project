package hva.app.habitat;

import hva.core.Hotel;

import hva.core.exception.DuplicateHabitatException;
import hva.core.exception.HabitatNotFoundException;
import hva.app.exception.DuplicateHabitatKeyException;
import hva.app.exception.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a new habitat to this zoo hotel.
 **/
class DoRegisterHabitat extends Command<Hotel> {

  DoRegisterHabitat(Hotel receiver) {
    super(Label.REGISTER_HABITAT, receiver);
    addStringField("habitatKey", Prompt.habitatKey());
    addStringField("habitatName", Prompt.habitatName());
    addIntegerField("habitatArea", Prompt.habitatArea());
  }

  @Override
  protected void execute() throws CommandException {
    try {
      _receiver.addHabitat(stringField("habitatKey"), stringField("habitatName"),
          integerField("habitatArea"));
    } catch (DuplicateHabitatException e) {
      throw new DuplicateHabitatKeyException(e.id());
    } catch (HabitatNotFoundException e) {
      throw new UnknownHabitatKeyException(e.id());
    }
  }
}
