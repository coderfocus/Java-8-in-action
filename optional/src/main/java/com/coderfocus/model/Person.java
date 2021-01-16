package com.coderfocus.model;

import java.util.Optional;

public class Person {
    public Optional<Car> getCar() {
        return car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }

    private Optional<Car> car;

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    private House house;
}
