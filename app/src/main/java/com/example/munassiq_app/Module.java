package com.example.munassiq_app;

public class Module {
    private String name, address;

    public Module() {
    }

    public Module(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

}
