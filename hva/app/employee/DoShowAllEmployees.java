package hva.app.employee;

import hva.core.Hotel;

import pt.tecnico.uilib.menus.Command;

/**
 * Show all employees of this zoo hotel.
 **/
class DoShowAllEmployees extends Command<Hotel> {

  DoShowAllEmployees(Hotel receiver) {
    super(Label.SHOW_ALL_EMPLOYEES, receiver);
  }

  @Override
  protected void execute() {
    _display.addAll(_receiver.workers().stream().sorted().toList());
    _display.display();
  }
}
