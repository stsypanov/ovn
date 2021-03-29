package com.tsypanov.ovn;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ByteArrayInputStreamBenchmark {

  @Benchmark
  public void read(Data data, Blackhole bh) {
    int value;
    var in = data.bais;
    while ((value = in.read()) != -1) {
      bh.consume(value);
    }
  }

  @Benchmark
  public void readBuffered(Data data, Blackhole bh) throws IOException {
    int value;
    var in = new BufferedInputStream(data.bais);
    while ((value = in.read()) != -1) {
      bh.consume(value);
    }
  }

  @Benchmark
  public Object readAllBytes(Data data) {
    var in = data.bais;
    return in.readAllBytes();
  }

  @Benchmark
  public Object readAllBytesBuffered(Data data) throws IOException {
    var in = data.bais;
    return new BufferedInputStream(in).readAllBytes();
  }

  @State(Scope.Thread)
  public static class Data {

    @Param({"8", "128", "512", "1024"})
    private int length;

    private byte[] bytes;
    private ByteArrayInputStream bais;

    @Setup(Level.Iteration)
    public void setUp() {
      bytes = new byte[length];
      ThreadLocalRandom.current().nextBytes(bytes);
    }

    @Setup(Level.Invocation)
    public void setUpBais() {
      bais = new ByteArrayInputStream(bytes);
    }
  }
}
