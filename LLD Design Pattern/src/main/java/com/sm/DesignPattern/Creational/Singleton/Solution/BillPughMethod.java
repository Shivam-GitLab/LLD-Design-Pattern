package com.sm.DesignPattern.Creational.Singleton.Solution;

public class BillPughMethod {
    // Private constructor
    private BillPughMethod() {}

    // Static inner class to hold the Singleton instance
    private static class Holder {
        private static final BillPughMethod INSTANCE = new BillPughMethod();
    }

    // Public method to return the Singleton instance
    public static BillPughMethod getInstance() {
        return Holder.INSTANCE;
    }
}
