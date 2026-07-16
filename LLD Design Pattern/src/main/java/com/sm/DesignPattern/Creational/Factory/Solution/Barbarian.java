package com.sm.DesignPattern.Creational.Factory.Solution;

public class Barbarian extends Troop {

    public Barbarian() {
        super("Barbarian", 150, 50);
    }

    @Override
    public void attack() {
        System.out.println(name + " swings sword causing " + damage + " damage!");
    }

    @Override
    public void move() {
        System.out.println(name + " charges quickly towards the enemy!");
    }
}
