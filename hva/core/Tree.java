package hva.core;

import hva.core.enumf.LeafState;
import hva.core.enumf.SeasonType;
import java.math.*;
import java.io.*;

public abstract class Tree implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

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
        return -1;//(_cleaningDifficulty * seasonalEffort() * Math.log(_age * 1));
    }

    protected abstract int seasonalEffort();

    protected abstract LeafState leafState();

    public abstract String toString();
}
