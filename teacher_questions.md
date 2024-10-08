In Hotel.addAnimal, what do we do if the habitat doesnt exist? do we create it? do we ask if the user wants to create it? do we cancel the process like we do if the id of the animal is duplicate? Throw error

In Hotel.addVaccine, do we return instantly after we find the first species id that doesnt exist and just return that one? or do we keep going and test the others and then return all of the ids that dont exist together? Just the first

in the Set commands (where we create and add tree's, habitats, workers, etc) should we return the object that was created? this is helpful for the tree creation that requires it to be printed. should the others do the same as this? Always return the object on those

In hotel.vaccinateAnimal where we need to check if the vet exists, we have the workers map, that throws the exception, yet then we need to downcast it into a vet right? should we have 2 independent maps for vets and caretakers? or try catch the exception that java might throw if it fails to downcast the object to vet? and then throw our own exception? or should we do an if isinstanceof to verify if it is vet. and throw the exception right there? 

RE: Separate the maps.

in Hotel.errorVaccines do we get all vaccines that caused damage? or only the ones that caused ERROR level damage? 

RE: speak to teacher