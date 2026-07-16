package com.sm.DesignPattern.Creational.Singleton.Solution;

public class SynchronizedMethod {
    // Object declaration
    private static SynchronizedMethod instance;

    // Private constructor
    private SynchronizedMethod() {}

    // Synchronized keyword used
    public static synchronized SynchronizedMethod getInstance() {
        if (instance == null) {
            instance = new SynchronizedMethod();
        }
        return instance;
    }
}
