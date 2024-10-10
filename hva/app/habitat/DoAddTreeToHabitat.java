package hva.app.habitat;

import hva.core.Hotel;

import hva.core.exception.DuplicateTreeException;
import hva.core.exception.HabitatNotFoundException;
import hva.core.exception.UnrecognizedTreeTypeException;

import hva.app.exception.UnknownHabitatKeyException;
import hva.app.exception.DuplicateTreeKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a new tree to a given habitat of the current zoo hotel.
 **/
class DoAddTreeToHabitat extends Command<Hotel> {

  DoAddTreeToHabitat(Hotel receiver) {
    super(Label.ADD_TREE_TO_HABITAT, receiver);
    addStringField("habitatKey", Prompt.habitatKey());
    addStringField("treeKey", Prompt.treeKey());
    addStringField("treeName", Prompt.treeName());
    addIntegerField("treeAge", Prompt.treeAge());
    addIntegerField("treeDifficulty", Prompt.treeDifficulty());
    addOptionField("treeType", Prompt.treeType(), "CADUCA", "PERENE");
  }

  @Override
  protected void execute() throws CommandException {
    try {
      _display.add(_receiver.addTreeToHabitat(stringField("habitatKey"), stringField("treeKey"),
          stringField("treeName"), integerField("treeAge"), integerField("treeDifficulty"),
          optionField("treeType")));
      _display.display();
    } catch (HabitatNotFoundException e) {
      throw new UnknownHabitatKeyException(e.id());
    } catch (DuplicateTreeException e) {
      throw new DuplicateTreeKeyException(e.id());
    } catch (UnrecognizedTreeTypeException e) {
      // This should never happen unless the optionField method is badly configured.
      throw new RuntimeException(e.getMessage());
    }
  }
}
