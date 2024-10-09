package hva.app.employee;

import hva.app.exception.DuplicateEmployeeKeyException;

import hva.core.Hotel;

import hva.core.exception.DuplicateWorkerException;
import hva.core.exception.UnrecognizedWorkerTypeException;

import pt.tecnico.uilib.forms.Form;

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
    addStringField("workerType", Prompt.employeeType());
  }

  @Override
  protected void execute() throws CommandException {
    String workerType = stringField("workerType");

    while (true) {
      try {
        _receiver.addWorker(stringField("workerKey"), stringField("workerName"), workerType);
        break;
      } catch (DuplicateWorkerException e) {
        throw new DuplicateEmployeeKeyException(e.id());
      } catch (UnrecognizedWorkerTypeException e) {
        workerType = Form.requestString(Prompt.employeeType());
      }
    }

  }
}
