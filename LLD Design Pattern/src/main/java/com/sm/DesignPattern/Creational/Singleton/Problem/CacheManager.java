package com.sm.DesignPattern.Creational.Singleton.Problem;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private final Map<String, String> cache = new HashMap<>();

    public void addToCache(String key, String value) {
        cache.put(key, value);
    }

    public String getFromCache(String key) {
        return cache.get(key);
    }

}