package com.coderfocus.model;

import java.util.Optional;

public class House {
    public Optional<String> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;
}
