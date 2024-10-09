package hva.app.habitat;

import hva.core.Hotel;

import hva.core.enumerator.Influence;

import hva.core.exception.HabitatNotFoundException;
import hva.core.exception.SpeciesNotFoundException;

import hva.app.exception.UnknownHabitatKeyException;
import hva.app.exception.UnknownSpeciesKeyException;

import pt.tecnico.uilib.forms.Form;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Associate (positive or negatively) a species to a given habitat.
 **/
class DoChangeHabitatInfluence extends Command<Hotel> {

  DoChangeHabitatInfluence(Hotel receiver) {
    super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
    addStringField("habitatKey", Prompt.habitatKey());
    addStringField("speciesKey", hva.app.animal.Prompt.speciesKey());
    addStringField("influence", Prompt.habitatInfluence());
  }

  @Override
  protected void execute() throws CommandException {
    Influence influence = null;
    String influenceString = stringField("influence");

    // Convert to Enum
    while (true) {
      switch (influenceString) {
        case "POS":
          influence = Influence.POS;
          break;
        case "NEG":
          influence = Influence.NEG;
          break;
        case "NEU":
          influence = Influence.NEU;
          break;
        default:
          influenceString = Form.requestString(Prompt.habitatInfluence());
          continue;
      }
      break;
    }

    try {
      _receiver.changeHabitatSuitability(stringField("habitatKey"), stringField("speciesKey"),
          influence);
    } catch (HabitatNotFoundException e) {
      throw new UnknownHabitatKeyException(e.id());
    } catch (SpeciesNotFoundException e) {
      throw new UnknownSpeciesKeyException(e.id());
    }
  }
}
