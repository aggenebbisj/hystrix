package nl.rdj.hystrix.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheClient {

    private final Map<String, String> cache;

    public CacheClient() {
        this.cache = new ConcurrentHashMap<>();
    }
    
    public String get(String key) {
        return cache.get(key);
    }
    
    public void add(String key, String value) {
        cache.put(key, value);
    }
    
}
