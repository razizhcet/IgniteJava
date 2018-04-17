package IgniteBizJava.IgniteJavaFirst;

import java.util.Scanner;

import javax.cache.event.CacheEntryEvent;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.ContinuousQuery;

public class MessageDeliver {
	
	public static void main(String[] args) 
	{
		try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml"))
	    {
			ignite.cluster().active(true);
			
			System.out.print("Hi, enter your name: ");
			Scanner consoleScanner = new Scanner(System.in);
			String name = consoleScanner.nextLine();
			
			// Get or create cache
			IgniteCache<Long, Message> cache = ignite.getOrCreateCache("chat");
			
			// Initialize unique ID sequence
			IgniteAtomicSequence messageId = ignite.atomicSequence("chatId", 0, true);
			
			// Set up continuous query
			ContinuousQuery<Long, Message> qry = new ContinuousQuery<>();
			qry.setLocalListener(iterable -> 
			{
				// This will be invoked immediately on each cache update
			    for (CacheEntryEvent<? extends Long, ? extends Message> evt : iterable)
			        System.out.println(evt.getValue().author + ": " + evt.getValue().text);
			});
			
			cache.query(qry);
			while (true) 
			{
			    System.out.print(">");
			    String msgText = consoleScanner.nextLine();
			    Long msgId = messageId.incrementAndGet();
			    cache.put(msgId, new Message(name, msgText));
			}
	    }
	}

}
