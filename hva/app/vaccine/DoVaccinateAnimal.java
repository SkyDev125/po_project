package hva.app.vaccine;

import hva.core.Hotel;
import hva.core.VaccineRegistry;
import hva.core.exception.AnimalNotFoundException;
import hva.core.exception.VaccineNotFoundException;
import hva.core.exception.WorkerNotAuthorizedException;
import hva.core.exception.WorkerNotFoundException;

import hva.app.exception.UnknownAnimalKeyException;
import hva.app.exception.UnknownVaccineKeyException;
import hva.app.exception.UnknownVeterinarianKeyException;
import hva.app.exception.VeterinarianNotAuthorizedException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Vaccinate by a given veterinarian a given animal with a given vaccine.
 **/
class DoVaccinateAnimal extends Command<Hotel> {
  DoVaccinateAnimal(Hotel receiver) {
    super(Label.VACCINATE_ANIMAL, receiver);
    addStringField("vaccineKey", Prompt.vaccineKey());
    addStringField("vetKey", Prompt.veterinarianKey());
    addStringField("animalKey", hva.app.animal.Prompt.animalKey());
  }

  @Override
  protected final void execute() throws CommandException {
    String idAnimal = stringField("animalKey");
    String idVaccine = stringField("vaccineKey");

    try {
      VaccineRegistry vaccineRegistry = _receiver.vaccinateAnimal(
          idAnimal,
          idVaccine,
          stringField("vetKey"));

      switch (vaccineRegistry.vaccineDamage()) {
        case ACCIDENT:
        case CONFUSION:
        case ERROR:
          Message.wrongVaccine(idVaccine, idAnimal);
          break;
        default:
          break;
      }
    } catch (AnimalNotFoundException e) {
      throw new UnknownAnimalKeyException(e.id());
    } catch (VaccineNotFoundException e) {
      throw new UnknownVaccineKeyException(e.id());
    } catch (WorkerNotFoundException e) {
      throw new UnknownVeterinarianKeyException(e.id());
    } catch (WorkerNotAuthorizedException e) {
      throw new VeterinarianNotAuthorizedException(e.idWorker(), e.idResponsibility());
    }
  }
}
