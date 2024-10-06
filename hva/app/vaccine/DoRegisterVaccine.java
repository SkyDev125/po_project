package hva.app.vaccine;

import hva.core.Hotel;
import hva.core.exception.DuplicateVaccineException;
import hva.core.exception.SpeciesNotFoundException;

import hva.app.exception.UnknownSpeciesKeyException;
import hva.app.exception.DuplicateVaccineKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Apply a vaccine to a given animal.
 **/
class DoRegisterVaccine extends Command<Hotel> {

  DoRegisterVaccine(Hotel receiver) {
    super(Label.REGISTER_VACCINE, receiver);
    addStringField("vaccineKey", Prompt.vaccineKey());
    addStringField("vaccineName", Prompt.vaccineName());
    addStringField("speciesKeys", Prompt.listOfSpeciesKeys());
  }

  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.addVaccine(
          stringField("vaccineKey"),
          stringField("vaccineName"),
          stringField("speciesKeys"));
    } catch (DuplicateVaccineException e) {
      throw new DuplicateVaccineKeyException(e.id());
    } catch (SpeciesNotFoundException e) {
      throw new UnknownSpeciesKeyException(e.id());
    }
  }
}
