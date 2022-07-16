package com.dg.ocp4.local.client.controller;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final RemoteCacheManager cacheMgrRemote;

    @Autowired
    public Controller(RemoteCacheManager cacheManager) {
        this.cacheMgrRemote = cacheManager;
    }


    @GetMapping(value = "/update-cache/{cacheName}/{cacheKey}/{cacheValue}", produces = {
            "application/json; charset=UTF-8" })
    public String updateCache(@PathVariable String cacheName, @PathVariable String cacheKey,
                              @PathVariable String cacheValue) {

        cacheMgrRemote.getCache(cacheName).put(cacheKey, cacheValue);
        RemoteCache<String, String> cache = cacheMgrRemote.getCache(cacheName);
        String returnedValue = cache.get(cacheKey).toString();
        return "SUCCESS "+returnedValue;
    }

    @GetMapping(value = "/get-cache-value/{cacheName}/{cacheKey}", produces = {
            "application/json; charset=UTF-8" })
    public String getValueCache(@PathVariable String cacheName, @PathVariable String cacheKey) {
        RemoteCache<String, String> cache = cacheMgrRemote.getCache(cacheName);
        String returnedValue = cache.get(cacheKey).toString();
        return "The value for key: " + cacheKey + " is: " + returnedValue;
    }

}
