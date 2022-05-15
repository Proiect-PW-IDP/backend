package com.pweb.utils;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;

public class Metrics {
    public static final Counter COUNTER = Counter.build()
            .name("get_all_offers")
            .help("Get all offers requests")
            .create()
            .register();
    public static final Histogram COMMAND_LATENCY = Histogram.build()
            .name("command_latency")
            .help("Time it takes for a command to process.")
            .create()
            .register();
}
