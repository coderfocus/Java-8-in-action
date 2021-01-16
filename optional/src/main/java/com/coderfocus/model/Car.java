package com.coderfocus.model;

import java.util.Optional;

public class Car {
    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    private Optional<Insurance> insurance;

    public void setInsurance(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }
}
