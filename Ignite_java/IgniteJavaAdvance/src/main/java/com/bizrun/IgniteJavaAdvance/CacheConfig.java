package com.bizrun.IgniteJavaAdvance;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;

import org.apache.ignite.configuration.CacheConfiguration;

public class CacheConfig {
	  public static CacheConfiguration<String, Long> wordCache() {
	    CacheConfiguration<String, Long> cfg = new CacheConfiguration<>("words");
	 
	    // Index individual words.
	    cfg.setIndexedTypes(AffinityUuid.class, /*word type*/String.class);
	 
	    // Sliding window of 1 seconds.
	    cfg.setExpiryPolicyFactory(FactoryBuilder.factoryOf(
	      new CreatedExpiryPolicy(new Duration(SECONDS, 1))));
	 
	    return cfg;
	  }
	}
