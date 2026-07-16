package com.sm.DesignPattern.Creational.Singleton.Solution;

import java.util.HashMap;
import java.util.Map;

public class EagerCacheManager {

    private static final EagerCacheManager instance = new EagerCacheManager();

    // private constructor
    private EagerCacheManager(){}

    private final Map<String, String> cache = new HashMap<>();

    public void addToCache(String key, String value) {
        cache.put(key, value);
    }

    public String getFromCache(String key) {
        return cache.get(key);
    }

    public static EagerCacheManager getInstance() {
        return instance;
    }
}

