package com.sm.DesignPattern.Creational.Singleton.Solution;

public class Client1 {
    public void run() {

        System.out.println("Lazy Cache Manager 01 - Client1");
        LazyCacheManager cache1 = LazyCacheManager.getInstance();
        System.out.println(cache1);
        cache1.addToCache("username", "Rohan");
        System.out.println("Client1 added key: username -> Rohan");

        System.out.println();

        System.out.println("Eager Cache Manager 02 - Client1");
        EagerCacheManager cache2 = EagerCacheManager.getInstance();
        System.out.println(cache2);
        cache2.addToCache("username", "Shiva");
        System.out.println("Client1 added key: username -> Shiva");

        System.out.println();
        System.out.println("Synchronized Method 03 - Client1");
        SynchronizedMethod cache3 = SynchronizedMethod.getInstance();
        System.out.println(cache3);

        System.out.println();
        System.out.println("Double Checked Locking 04 - Client1");
        DoubleCheckedLocking cache4 = DoubleCheckedLocking.getInstance();
        System.out.println(cache4);

        System.out.println();
        System.out.println("Bill Pugh Method 05 - Client1");
        BillPughMethod cache5 = BillPughMethod.getInstance();
        System.out.println(cache5);
    }
}