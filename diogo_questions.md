In the comments for toString, should it be:
    @return REGISTO-VACINA|idVacina|idVeterinario|idEspecie
    @return the vaccine registry in format

RE: Havent looked at the specification for commenting yet, was planning on doing it after having the code done for the most part (its what I usually do). I add comments in the middle of the code, but those big ones are usually done afterwards.

But this is an example: 

/**
 * Returns the vet's information in a specific format.
 * 
 * The format is as follows:
 * - If the vet has responsibilities: "tipo|id|nome|idResponsabilidades"
 * - If the vet doesn't have responsibilities: "tipo|id|nome"
 * 
 * @return a String representing the vet's information in the specified format
 */

that I think makes sense.


In the comments for methods with boolean return type, what should be the @return 

RE: question above.


Faltam métodos no hotel para adicionar objectos às ArrayLists no hotel, não?

RE: for the vaccineRegistry, I was gonna delegate that as a return of vet, so I can just add it in hotel when it returns the VaccineReg through the function vaccinate.




The constructors should have comments, certo?

RE: Probably yes.


unmodifiableList or unmodifiableCollection?

RE: unmodifiableCollection when we arent sure if it is in order, unmodifiableList when we are sure it is in order.


Vi um comentário em que punhas "hotel in this instance" ou algo do género, tenho feito o mesmo, o que achas?

RE: 
