package com.sm.DesignPattern.Creational.Singleton.Solution;

public class Client2 {
    public void run() {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println();

        System.out.println("Lazy Cache Manager 01 - Client2");
        LazyCacheManager cache1 = LazyCacheManager.getInstance();
        System.out.println(cache1);
        String value1 = cache1.getFromCache("username");
        System.out.println("Client2 tried to get 'username': " + value1);

        System.out.println();

        System.out.println("Eager Cache Manager 02 - Client2");
        EagerCacheManager cache2 = EagerCacheManager.getInstance();
        System.out.println(cache2);
        String value2 = cache2.getFromCache("username");
        System.out.println("Client2 tried to get 'username': " + value2);

        System.out.println();
        System.out.println("Synchronized Method 03 - Client2");
        SynchronizedMethod cache3 = SynchronizedMethod.getInstance();
        System.out.println(cache3);

        System.out.println();
        System.out.println("Double Checked Locking 04 - Client2");
        DoubleCheckedLocking cache4 = DoubleCheckedLocking.getInstance();
        System.out.println(cache4);

        System.out.println();
        System.out.println("Bill Pugh Method 05 - Client2");
        BillPughMethod cache5 = BillPughMethod.getInstance();
        System.out.println(cache5);
    }
}