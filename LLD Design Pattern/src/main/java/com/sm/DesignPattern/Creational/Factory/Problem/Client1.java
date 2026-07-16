package com.sm.DesignPattern.Creational.Factory.Problem;

import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter troop type (Barbarian, Archer, Wizard, HogRider):");
        String type = scanner.nextLine();

        Troop troop;

        if (type.equalsIgnoreCase("Barbarian")) {
            troop = new Barbarian();
        } else if (type.equalsIgnoreCase("Archer")) {
            troop = new Archer();
        } else if (type.equalsIgnoreCase("Wizard")) {
            troop = new Wizard();
        } else if (type.equalsIgnoreCase("HogRider")) {
            troop = new HogRider();
        } else {
            throw new IllegalArgumentException("Unknown troop type: " + type);
        }

        troop.move();
        troop.attack();

        scanner.close();
    }
}
