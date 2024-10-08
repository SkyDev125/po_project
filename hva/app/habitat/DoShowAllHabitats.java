package hva.app.habitat;

import hva.core.Hotel;

import pt.tecnico.uilib.menus.Command;

/**
 * Show all habitats of this zoo hotel.
 **/
class DoShowAllHabitats extends Command<Hotel> {

  DoShowAllHabitats(Hotel receiver) {
    super(Label.SHOW_ALL_HABITATS, receiver);
  }

  @Override
  protected void execute() {
    _display.addAll(_receiver.habitats().stream().sorted().toList());
    _display.display();
  }
}
