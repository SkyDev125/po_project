package hva.core.exception;

public class WorkerNotFoundException extends Exception {

    private static final long serialVersionUID = 202407081733L;

    public WorkerNotFoundException(String id) {
        super("Worker with id " + id + " not found");
    }

}
