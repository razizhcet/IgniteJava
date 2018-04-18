package com.bizrun.IgniteJavaAdvance;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.junit.Test;

public class HelloWorldCache {
	
	public static void main(String[] args) throws IgniteException {
		HelloWorldCache hwc = new HelloWorldCache();
		hwc.helloWorldCache();
		hwc.cacheTransaction();
	}
	
	@Test
	public void helloWorldCache() {
		
		try(Ignite ignite = Ignition.start()){
			
			ignite.cluster().active(true);
			
			//create a cache
			IgniteCache<String, String> cache = ignite.getOrCreateCache("cache");
			
			//put cache entry
			String key = "key";
			String val = "Hello";
			cache.put(key, val);
			
			//modify cache entry
			cache.put(key, cache.get(key) + " World");
			
			//read cache entry
			System.out.println(cache.get(key));
		}
	}
	
	@Test
	public void cacheTransaction() {
		
		try (Ignite ignite = Ignition.start()){
			ignite.cluster().active(true);
			
			//create a cache
			CacheConfiguration<String, String> config = new CacheConfiguration<>();
			config.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
			IgniteCache<String, String> cache = ignite.getOrCreateCache(config);
			
			//put cache entry
			String key = "key";
			String val = "Hello";
			cache.put(key, val);
			
			//Modify cache entry
			try (Transaction tx = ignite.transactions().txStart()){
				cache.put(key, cache.get(key) + " World");
				tx.commit();
			}
			
			//Read cache entry
			System.out.println(cache.get(key));
			
		}
	}

}
