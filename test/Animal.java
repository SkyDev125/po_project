abstract class Animal {
    protected final String _species;

    Animal() {
        this._species = this.getClass().getName();
    }

}
