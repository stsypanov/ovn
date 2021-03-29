package com.tsypanov.ovn;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Borrowed from http://psy-lob-saw.blogspot.com/2014/08/the-volatile-read-suprise.html
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class LoopyBenchmarks {
  @Param({ "32", "1024", "32768" })
  int size;

  byte[] bunn;

  @Setup
  public void prepare() {
    bunn = new byte[size];
  }

  @Benchmark
  public void goodOldLoop(Blackhole fox) {
    for (int y = 0; y < bunn.length; y++) { // good old C style for (the win?)
      fox.consume(bunn[y]);
    }
  }

  @Benchmark
  public void sweetLoop(Blackhole fox) {
    for (byte bunny : bunn) { // syntactic sugar loop goodness
      fox.consume(bunny);
    }
  }

  @Benchmark
  public void goodOldLoopReturns(Blackhole fox) {
    byte[] funn = this.bunn;
    for (int y = 0; y < funn.length; y++) {
      fox.consume(funn[y]);
    }
  }
}
