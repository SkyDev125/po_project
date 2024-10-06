package hva.app.animal;

import hva.core.Hotel;
import hva.core.exception.AnimalNotFoundException;
import hva.core.exception.HabitatNotFoundException;

import hva.app.exception.UnknownAnimalKeyException;
import hva.app.exception.UnknownHabitatKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Transfers a given animal to a new habitat of this zoo hotel.
 */
class DoTransferToHabitat extends Command<Hotel> {

  DoTransferToHabitat(Hotel hotel) {
    super(Label.TRANSFER_ANIMAL_TO_HABITAT, hotel);
    addStringField("animalKey", Prompt.animalKey());
    addStringField("habitatKey", hva.app.habitat.Prompt.habitatKey());
  }

  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.transferAnimal(stringField("animalKey"), stringField("habitatKey"));
    } catch (AnimalNotFoundException e) {
      throw new UnknownAnimalKeyException(e.id());
    } catch (HabitatNotFoundException e) {
      throw new UnknownHabitatKeyException(e.id());
    }
  }
}
