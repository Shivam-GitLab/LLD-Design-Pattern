package com.sm.DesignPattern.Creational.Factory.Problem;

public class HogRider extends Troop {
    public HogRider() {
        super("Hog Rider", 200, 60);
    }

    @Override
    public void attack() {
        System.out.println(name + " smashes defenses causing " + damage + " damage!");
    }

    @Override
    public void move() {
        System.out.println(name + " moves fast and jumps over walls");
    }
}
