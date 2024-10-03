package hva.core;

import hva.core.enumf.LeafState;
import hva.core.enumf.SeasonType;
import java.math.*;

public abstract class Tree {
    private final String _id;
    private final String _name;
    private int _age;
    private final SeasonType _birthSeason;
    private final int _cleaningDifficulty;
    private final Hotel _hotel;

    // constructor
    public Tree(String id, String name, int age, int cleaningDifficulty, Hotel hotel) {
        _id = id;
        _name = name;
        _age = age;
        _cleaningDifficulty = cleaningDifficulty;
        _hotel = hotel;
        _birthSeason = _hotel.season();
    }

    // gets
    protected String name() {
        return _name;
    }

    protected int age() {
        return _age;
    }

    protected int cleaningDifficulty() {
        return _cleaningDifficulty;
    }

    protected Hotel hotel() {
        return _hotel;
    }

    // sets

    // methods
    int totalCleaningEffort() {
        return (_cleaningDifficulty * seasonalEffort() * Math.log(_age * 1));
    }

    private abstract int seasonalEffort();

    private abstract LeafState leafState();

    public abstract String toString();
}
