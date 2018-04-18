package com.bizrun.IgniteJavaAdvance;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.junit.jupiter.api.Test;

public class HelloWorldCompute {
	
	public static void main(String[] args) throws IgniteException {
		helloWorldCompute();
	}
	
	@Test
	static void helloWorldCompute() {
		try (Ignite ignite = Ignition.start()) {
			ignite.cluster().active(true);
			
			ignite.compute().broadcast(() -> System.out.println("Hello World"));
		}
	}

}
