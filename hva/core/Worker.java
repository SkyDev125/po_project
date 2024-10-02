package hva.core;

abstract public class Worker {
    private final String _id;
    private final String _name;

    // constructor
    public Worker(String id, String name) {
        _id = id;
        _name = name;
    }

    // gets
    public String id() {
        return _id;
    }

    String name() {
        return _name;
    }

    // methods
    abstract protected void addResponsibility(String id);

    abstract protected void removeResponsibility(String id);

    abstract protected int satisfaction();

    abstract public String toString();
}
