package hva.app.habitat;

import hva.core.Hotel;
import hva.core.exception.HabitatNotFoundException;

import hva.app.exception.UnknownHabitatKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Change the area of a given habitat.
 **/
class DoChangeHabitatArea extends Command<Hotel> {

  DoChangeHabitatArea(Hotel receiver) {
    super(Label.CHANGE_HABITAT_AREA, receiver);
    addStringField("habitatKey", Prompt.habitatKey());
    addIntegerField("habitatArea", Prompt.habitatArea());
  }

  @Override
  protected void execute() throws CommandException {
    try {
      _receiver.changeHabitatArea(stringField("habitatKey"), integerField("habitatArea"));
    } catch (HabitatNotFoundException e) {
      throw new UnknownHabitatKeyException(e.id());
    }
  }
}
