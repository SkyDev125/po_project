package hva.app.habitat;

import hva.core.Hotel;

import hva.core.exception.HabitatNotFoundException;

import hva.app.exception.UnknownHabitatKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all trees in a given habitat.
 **/
class DoShowAllTreesInHabitat extends Command<Hotel> {

  DoShowAllTreesInHabitat(Hotel receiver) {
    super(Label.SHOW_TREES_IN_HABITAT, receiver);
    addStringField("habitatKey", hva.app.habitat.Prompt.habitatKey());
  }

  @Override
  protected void execute() throws CommandException {
    try {
      _display.addAll(_receiver.habitatTrees(stringField("habitatKey")).stream().sorted().toList());
      _display.display();
    } catch (HabitatNotFoundException e) {
      throw new UnknownHabitatKeyException(e.id());
    }
  }
}
