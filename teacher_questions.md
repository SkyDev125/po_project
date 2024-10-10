What does the teacher think of the serialization we did, and the way we check for any changes
by comparing the serializables.

How should we handle the create/open/save menu, should we keep asking until it works?
should we only try once? and let it go. what exceptions should we throw if it does fail then?
any message passed onto the user to tell them it didnt work?

Check what he thinks of the comments.

in Import should we serialize the empty hotel so even if there arent any changes, as this is an import the user would be asked to link this to a file when they try to save it or create a new one?
or should we serialize it after importing so it only asks that when there are any changes made to the original state of the imported hotel?