package hva.app.employee;

import hva.core.Hotel;
import hva.core.exception.ResponsibilityNotFoundException;
import hva.core.exception.WorkerNotFoundException;

import hva.app.exception.NoResponsibilityException;
import hva.app.exception.UnknownEmployeeKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Remove a given responsability from a given employee of this zoo hotel.
 **/
class DoRemoveResponsibility extends Command<Hotel> {

  DoRemoveResponsibility(Hotel receiver) {
    super(Label.REMOVE_RESPONSABILITY, receiver);
    addStringField("workerKey", Prompt.employeeKey());
    addStringField("responsibilityKey", Prompt.responsibilityKey());
  }

  @Override
  protected void execute() throws CommandException {
    try {
      _receiver.removeResponsibilityFromWorker(
          stringField("workerKey"),
          stringField("responsibilityKey"));
    } catch (WorkerNotFoundException e) {
      throw new UnknownEmployeeKeyException(e.id());
    } catch (ResponsibilityNotFoundException e) {
      throw new NoResponsibilityException(e.idWorker(), e.idResponsibility());
    }
  }
}
