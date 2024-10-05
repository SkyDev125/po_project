package hva.app.main;

import hva.core.HotelManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.Display;

/**
 * Command for show the global satisfation of the current zoo hotel.
 **/
class DoShowGlobalSatisfaction extends Command<HotelManager> {
  DoShowGlobalSatisfaction(HotelManager receiver) {
    super(hva.app.main.Label.SHOW_GLOBAL_SATISFACTION, receiver);
  }

  @Override
  protected final void execute() {
    Display display = new Display();
    display.add(Math.round(_receiver.satisfaction()));
    display.display();
  }
}
