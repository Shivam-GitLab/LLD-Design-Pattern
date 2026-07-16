package com.sm.DesignPattern.Creational.Singleton.Problem;

import com.sm.DesignPattern.Creational.Singleton.Problem.*;

public class Client2 {
    public void run() {
        System.out.println();
        System.out.println("=================================");
        System.out.println();
        System.out.println("Cache Manager 01 - Client2");
        CacheManager cache = new CacheManager();
        System.out.println(cache);
        String value = cache.getFromCache("username");
        System.out.println("Client2 tried to get 'username': " + value);


    }
}