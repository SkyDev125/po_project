package hva.app.search;

import hva.core.Hotel;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all animals of a given habitat.
 **/
class DoShowWorkersOverResponsibilities extends Command<Hotel> {

  DoShowWorkersOverResponsibilities(Hotel receiver) {
    super("Funcionários com mais responsabilidades", receiver);
    addIntegerField("number", "Number of responsibilities: ");
  }

  @Override
  protected void execute() throws CommandException {
    _display.addAll(_receiver.workers().stream()
        .filter(w -> w.totalResponsibilities() > integerField("number")).toList());
    _display.display();
  }
}
