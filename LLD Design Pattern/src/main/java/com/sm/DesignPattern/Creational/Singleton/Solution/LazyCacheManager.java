package com.sm.DesignPattern.Creational.Singleton.Solution;

import java.util.HashMap;
import java.util.Map;

public class LazyCacheManager {

    private static LazyCacheManager instance;

    // private constructor
    private LazyCacheManager(){}

    private final Map<String, String> cache = new HashMap<>();

    public void addToCache(String key, String value) {
        cache.put(key, value);
    }

    public String getFromCache(String key) {
        return cache.get(key);
    }

    public static LazyCacheManager getInstance() {
        if (instance == null){
            instance = new LazyCacheManager();
        }
        return instance;
    }
}