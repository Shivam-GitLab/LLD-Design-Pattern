package com.sm.DesignPattern.Creational.Factory.Solution;

public class Archer extends Troop {
    private final int range;

    public Archer() {
        super("Archer", 100, 40);
        this.range = 5; // Units
    }

    @Override
    public void attack() {
        System.out.println(name + " shoots an arrow from " + range + " units away causing " + damage + " damage!");
    }

    @Override
    public void move() {
        System.out.println(name + " moves stealthily into shooting position.");
    }
}
