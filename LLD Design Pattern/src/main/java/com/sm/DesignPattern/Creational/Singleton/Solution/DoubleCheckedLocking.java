package com.sm.DesignPattern.Creational.Singleton.Solution;
//@SuppressWarnings("InstantiationOfUtilityClass")
public class DoubleCheckedLocking {
    // Volatile object declaration
    private static volatile DoubleCheckedLocking instance;

    // Private constructor
    private DoubleCheckedLocking() {}

    // Thread-safe method using double-checked locking
    public static DoubleCheckedLocking getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLocking();
                }
            }
        }
        return instance;
    }
}
