package hva.app.employee;

import hva.core.Hotel;
import hva.core.exception.WorkerNotFoundException;

import hva.app.exception.UnknownEmployeeKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the satisfaction of a given employee.
 **/
class DoShowSatisfactionOfEmployee extends Command<Hotel> {

  DoShowSatisfactionOfEmployee(Hotel receiver) {
    super(Label.SHOW_SATISFACTION_OF_EMPLOYEE, receiver);
    addStringField("workerKey", Prompt.employeeKey());
  }

  @Override
  protected void execute() throws CommandException {
    try {
      _display.add(Math.round(_receiver.workerSatisfaction(stringField("workerKey"))));
      _display.display();
    } catch (WorkerNotFoundException e) {
      throw new UnknownEmployeeKeyException(e.id());
    }
  }
}
