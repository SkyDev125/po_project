In Hotel.addAnimal, what do we do if the habitat doesnt exist? do we create it? do we ask if the user wants to create it? do we cancel the process like we do if the id of the animal is duplicate?

In Hotel.addVaccine, do we return instantly after we find the first species id that doesnt exist and just return that one? or do we keep going and test the others and then return all of the ids that dont exist together?

in the Set commands (where we create and add tree's, habitats, workers, etc) should we return the object that was created? this is helpful for the tree creation that requires it to be printed. should the others do the same as this?