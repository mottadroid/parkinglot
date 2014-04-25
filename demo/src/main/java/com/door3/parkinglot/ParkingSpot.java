package com.door3.parkinglot;

public class ParkingSpot {

    public enum Type {STANDART, HANDICAPPED}

    private boolean isEmpty;

    private Type type;

    public ParkingSpot(boolean isEmpty, Type type) {
        this.isEmpty = isEmpty;
        this.type = type;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Type getType() {
        return type;
    }


}
