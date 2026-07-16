package com.sm.DesignPattern.Creational.Factory.Problem;

public class Wizard extends Troop {

    public Wizard() {
        super("Wizard", 120, 70);
    }

    @Override
    public void attack() {
        System.out.println(name + " casts a fireball causing " + damage + " magical damage!");
    }

    @Override
    public void move() {
        System.out.println(name + " teleports short distances!");
    }
}
