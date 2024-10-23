package hva.app.habitat;

import hva.core.Habitat;
import hva.core.Hotel;

import hva.core.exception.HabitatNotFoundException;

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
    for (Habitat habitat : _receiver.habitats().stream().sorted().toList()) {
      _display.addLine(habitat.toString());
      try {
        _display.addAll(_receiver.habitatTrees(habitat.id()).stream().sorted().toList());
      } catch (HabitatNotFoundException e) {
        // Should never happen
        throw new RuntimeException(e.getMessage());
      }
    }
    _display.display();
  }
}
