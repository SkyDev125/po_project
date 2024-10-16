package hva.app.employee;

import hva.app.exception.DuplicateEmployeeKeyException;

import hva.core.Hotel;

import hva.core.exception.DuplicateWorkerException;
import hva.core.exception.UnrecognizedWorkerTypeException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Adds a new employee to this zoo hotel.
 **/
class DoRegisterEmployee extends Command<Hotel> {

  DoRegisterEmployee(Hotel receiver) {
    super(Label.REGISTER_EMPLOYEE, receiver);
    addStringField("workerKey", Prompt.employeeKey());
    addStringField("workerName", Prompt.employeeName());
    addOptionField("workerType", Prompt.employeeType(), "VET", "TRT");
  }

  @Override
  protected void execute() throws CommandException {
    try {
      _receiver.addWorker(stringField("workerKey"), stringField("workerName"),
          optionField("workerType"), "default");
    } catch (DuplicateWorkerException e) {
      throw new DuplicateEmployeeKeyException(e.id());
    } catch (UnrecognizedWorkerTypeException e) {
      e.printStackTrace();
    }

  }
}
