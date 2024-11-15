package hva.app.search;

import hva.core.Hotel;

import hva.core.exception.WorkerNotFoundException;

import hva.app.exception.UnknownVeterinarianKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all medical acts of a given veterinarian.
 **/
class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

  DoShowMedicalActsByVeterinarian(Hotel receiver) {
    super(Label.MEDICAL_ACTS_BY_VET, receiver);
    addStringField("vetKey", hva.app.employee.Prompt.employeeKey());
  }

  @Override
  protected void execute() throws CommandException {
    try {
      _display.addAll(_receiver.vetVaccinations(stringField("vetKey")));
    } catch (WorkerNotFoundException e) {
      throw new UnknownVeterinarianKeyException(e.id());
    }
  }
}
