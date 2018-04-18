package com.bizrun.IgniteJavaAdvance;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.affinity.AffinityUuid;
import org.apache.ignite.stream.StreamTransformer;

public class StreamWords {
	  public static void main(String[] args) throws Exception {
	    // Mark this cluster member as client.
	    Ignition.setClientMode(true);
	 
	    try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
	      // The cache is configured with sliding window holding 1 second of the streaming data.
	      IgniteCache<String,Long> stmCache = ignite.getOrCreateCache(CacheConfig.wordCache());
	 
	      try (IgniteDataStreamer<AffinityUuid, String> stmr = ignite.dataStreamer(stmCache.getName())) {
	        // Stream words from "alice-in-wonderland" book.
	        while (true) {
	          InputStream in = StreamWords.class.getResourceAsStream("wonderland.txt");
	 
	          try (LineNumberReader rdr = new LineNumberReader(new InputStreamReader(in))) {
	            for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
	              for (String word : line.split(" "))
	                if (!word.isEmpty())
	                  // Stream words into Ignite.
	                  // By using AffinityUuid as a key, we ensure that identical
	                  // words are processed on the same cluster node.
	                  stmr.addData(new AffinityUuid(word), word);
	            }
	          }
	        }
	      }
	    }
	  }
	}
