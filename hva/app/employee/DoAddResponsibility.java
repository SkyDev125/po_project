package hva.app.employee;

import hva.core.Hotel;

import hva.core.exception.ResponsibilityNotFoundException;
import hva.core.exception.WorkerNotFoundException;

import hva.app.exception.UnknownEmployeeKeyException;
import hva.app.exception.NoResponsibilityException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a new responsability to an employee: species to veterinarians and habitats to zookeepers.
 **/
class DoAddResponsibility extends Command<Hotel> {

  DoAddResponsibility(Hotel receiver) {
    super(Label.ADD_RESPONSABILITY, receiver);
    addStringField("workerKey", Prompt.employeeKey());
    addStringField("responsibilityKey", Prompt.responsibilityKey());
  }

  @Override
  protected void execute() throws CommandException {
    try {
      _receiver.addResponsibilityToWorker(stringField("workerKey"),
          stringField("responsibilityKey"));
    } catch (WorkerNotFoundException e) {
      throw new UnknownEmployeeKeyException(e.id());
    } catch (ResponsibilityNotFoundException e) {
      throw new NoResponsibilityException(e.idWorker(), e.idResponsibility());
    }
  }
}
