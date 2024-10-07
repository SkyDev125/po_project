package hva.app.vaccine;

import hva.core.Hotel;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.Display;

/**
 * Show all vaccines.
 **/
class DoShowAllVaccines extends Command<Hotel> {

  DoShowAllVaccines(Hotel receiver) {
    super(Label.SHOW_ALL_VACCINES, receiver);
  }

  @Override
  protected final void execute() {
    _display.addAll(_receiver.vaccines());
    _display.display();
  }
}
