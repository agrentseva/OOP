package ru.nsu.ga.grentseva.pizzeria.config;

import java.util.List;

public class Config {

    private int warehouseCapacity;
    private int workingTime;

    private List<BakerConfig> bakers;
    private List<CourierConfig> couriers;

    public int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    public int getWorkingTime() {
        return workingTime;
    }

    public List<BakerConfig> getBakers() {
        return bakers;
    }

    public List<CourierConfig> getCouriers() {
        return couriers;
    }
}