package com.sm.DesignPattern.Creational.Singleton.Problem;

public class WithoutSingletonApp {
    public static void main(String[] args) {
        Client1 c1 = new Client1();
        Client2 c2 = new Client2();

        c1.run();  // Adds to its own cache
        c2.run();  // Tries to read from a different cache
    }
}