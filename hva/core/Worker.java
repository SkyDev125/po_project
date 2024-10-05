package hva.core;

abstract public class Worker {
    private final String _id;
    private final String _name;
    private final Hotel _hotel;

    // constructor
    public Worker(String id, String name, Hotel hotel) {
        _id = id;
        _name = name;
        _hotel = hotel;
    }

    // gets
    public String id() {
        return _id;
    }

    String name() {
        return _name;
    }

    protected Hotel hotel() {
        return _hotel;
    }

    // methods
    abstract protected void addResponsibility(String id);

    abstract protected void removeResponsibility(String id);

    abstract protected int satisfaction();

    abstract public String toString();
}
