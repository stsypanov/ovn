package com.tsypanov.ovn;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class CharArrayWriterBenchmark {

  @Benchmark
  public String toString(Data data ) {
    return data.writer.toString();
  }

  @Benchmark
  public String toCharArray(Data data ) {
    return new String(data.writer.toCharArray());
  }

  @State(Scope.Thread)
  public static class Data {

    private CharArrayWriter writer;

    @Param({"5", "10", "50", "100", "1000"})
    private int length;

    @Setup
    public void setup() throws IOException {
      String alphabet = "abcdefghijklmnopqrstuvwxyz";

      RandomStringGenerator generator = new RandomStringGenerator();

      String string = generator.randomString(alphabet, length);

      writer = new CharArrayWriter(length);
      writer.write(string);
    }
  }
}
