package com.tsypanov;

import com.tsypanov.ovn.LoopyBenchmarks;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class BenchmarkRunner {

  public static void main(String[] args) throws Exception {

    Options opt = new OptionsBuilder()
            .include(LoopyBenchmarks.class.getSimpleName())
            .warmupIterations(10)
            .warmupTime(TimeValue.seconds(1))
            .measurementIterations(10)
            .measurementTime(TimeValue.seconds(1))
            .forks(4) //0 makes debugging possible
            .shouldFailOnError(true)
            .addProfiler(GCProfiler.class)
            .build();

    new Runner(opt).run();
  }
}
