package com.pweb.utils;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.springframework.context.annotation.Configuration;

public class Metrics {
    /*public static final Counter COUNTER = Counter.build()
            .name("get_all_offers")
            .help("Get all offers requests")
            .create()
            .register();
    public static final Histogram COMMAND_LATENCY = Histogram.build()
            .name("command_latency")
            .help("Time it takes for a command to process.")
            .create()
            .register();*/

    public static final io.micrometer.core.instrument.Counter offerCounter = new SimpleMeterRegistry().counter("get_all_offers", "offers");

//    public MeterRegistry getMeterRegistry() {
//        MeterRegistry registry = ;
//        return registry;
//    }
}
