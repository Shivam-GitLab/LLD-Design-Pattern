package com.sm.DesignPattern.Creational.Factory.Solution;

public class TroopFactory {
    public static Troop createTroop(String type) {
        if (type.equalsIgnoreCase("Barbarian")) {
            return new Barbarian();
        } else if (type.equalsIgnoreCase("Archer")) {
            return new Archer();
        } else if (type.equalsIgnoreCase("Wizard")) {
            return new Wizard();
        } else if (type.equalsIgnoreCase("HogRider")) {
            return new HogRider();
        } else {
            throw new IllegalArgumentException("Unknown troop type: " + type);
        }
    }
}
