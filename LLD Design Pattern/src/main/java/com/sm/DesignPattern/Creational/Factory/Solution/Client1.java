package com.sm.DesignPattern.Creational.Factory.Solution;

import java.util.Scanner;


public class Client1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter troop type (Barbarian, Archer, Wizard, HogRider):");
        String type = scanner.nextLine();

        Troop troop = TroopFactory.createTroop(type); // Centralized object creation

        troop.move();
        troop.attack();

        scanner.close();
    }
}
