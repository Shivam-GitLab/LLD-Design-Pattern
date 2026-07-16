package com.sm.DesignPattern.Creational.Singleton.Problem;

import com.sm.DesignPattern.Creational.Singleton.Problem.*;

public class Client1 {
    public void run() {

        System.out.println("Cache Manager 01 - Client1");
        CacheManager cache = new  CacheManager();
        System.out.println(cache);
        cache.addToCache("username", "Rohan");
        System.out.println("Client1 added key: username -> Rohan");
    }
}