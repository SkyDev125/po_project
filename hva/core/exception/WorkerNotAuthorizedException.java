package hva.core.exception;

public class WorkerNotAuthorizedException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String _idWorker;
    private final String _idResponsibility;

    public WorkerNotAuthorizedException(String idWorker, String idResponsibility) {
        super("Worker with id " + idWorker + " is not authorized to perform this action at:" + idResponsibility);
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
