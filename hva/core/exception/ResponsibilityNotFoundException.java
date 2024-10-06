package hva.core.exception;

public class ResponsibilityNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String _idWorker;
    private final String _idResponsibility;

    public ResponsibilityNotFoundException(String idWorker, String idResponsibility) {
        super("Responsibility with id " + idResponsibility + "for the worker: " + idWorker + " not found");
        _idWorker = idWorker;
        _idResponsibility = idResponsibility;
    }

    public String idWorker() {
        return _idWorker;
    }

    public String idResponsibility() {
        return _idResponsibility;
    }

}
