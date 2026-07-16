package com.sm.DesignPattern.Creational.Factory.Problem;

import lombok.Getter;

public abstract class Troop {
    @Getter
    protected String name;
    @Getter
    protected int health;
    protected int damage;

    public Troop(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public abstract void attack();
    public abstract void move();
}