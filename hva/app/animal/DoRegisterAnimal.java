package hva.app.animal;

import hva.core.Hotel;

import hva.core.exception.DuplicateAnimalException;
import hva.core.exception.DuplicateSpeciesException;
import hva.core.exception.HabitatNotFoundException;
import hva.core.exception.SpeciesNotFoundException;

import hva.app.exception.DuplicateAnimalKeyException;
import hva.app.exception.UnknownHabitatKeyException;

import pt.tecnico.uilib.forms.Form;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register a new animal in this zoo hotel.
 */
class DoRegisterAnimal extends Command<Hotel> {

  DoRegisterAnimal(Hotel receiver) {
    super(Label.REGISTER_ANIMAL, receiver);
    addStringField("animalKey", Prompt.animalKey());
    addStringField("animalName", Prompt.animalName());
    addStringField("speciesKey", Prompt.speciesKey());
    addStringField("habitatKey", hva.app.habitat.Prompt.habitatKey());
  }

  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.addAnimal(stringField("animalKey"), stringField("animalName"),
          stringField("speciesKey"), stringField("habitatKey"));
    } catch (SpeciesNotFoundException e) {
      try {
        _receiver.addSpecies(stringField("speciesKey"), Form.requestString(Prompt.speciesName()));
      } catch (DuplicateSpeciesException e1) {
        // This should never happen given the context
        // (cause we check if the species exists before adding it)
        // TODO: change this check with teacher
        assert false : "This should never happen";
      }
      execute();
    } catch (DuplicateAnimalException e) {
      throw new DuplicateAnimalKeyException(e.id());
    } catch (HabitatNotFoundException e) {
      throw new UnknownHabitatKeyException(e.id());
    }
  }
}
