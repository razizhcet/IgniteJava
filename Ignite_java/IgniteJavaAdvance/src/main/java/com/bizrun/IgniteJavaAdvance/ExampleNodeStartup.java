package com.bizrun.IgniteJavaAdvance;

import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;

public class ExampleNodeStartup {
    public static void main(String[] args) throws IgniteException {
        Ignition.start();
    }
}