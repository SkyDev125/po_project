package hva.app.animal;

import hva.core.Hotel;
import hva.core.exception.AnimalNotFoundException;

import hva.app.exception.UnknownAnimalKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Shows the satisfaction of a given animal.
 */
class DoShowSatisfactionOfAnimal extends Command<Hotel> {

  DoShowSatisfactionOfAnimal(Hotel receiver) {
    super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
    addStringField("animalKey", Prompt.animalKey());
  }

  @Override
  protected final void execute() throws CommandException {
    try {
      _display.add(Math.round(_receiver.animalSatisfaction(stringField("animalKey"))));
      _display.display();
    } catch (AnimalNotFoundException e) {
      throw new UnknownAnimalKeyException(e.id());
    }
  }
}
