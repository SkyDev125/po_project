package hva.app.search;

import hva.core.Hotel;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all animals of a given habitat.
 **/
class DoShowMoreSatisfiedAnimal extends Command<Hotel> {

  DoShowMoreSatisfiedAnimal(Hotel receiver) {
    super("Animal mais satisfeito", receiver);
  }

  @Override
  protected void execute() throws CommandException {
    _display.add(_receiver.moreSatisfiedAnimal());
    _display.display();
  }
}
