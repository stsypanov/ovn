package com.tsypanov.ovn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
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

@SuppressWarnings("StringOperationCanBeSimplified")
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class ByteArrayOutputStreamBenchmark {

  @Benchmark
  public String toString(Data data) {
    return data.baos.toString(data.charset);
  }

  @Benchmark
  public String newString(Data data) {
    return new String(data.baos.toByteArray(), data.charset);
  }

  @State(Scope.Thread)
  public static class Data {

    @Param({"0", "10", "100", "1000"})
    private int length;

    private final Charset charset = Charset.defaultCharset();

    private ByteArrayOutputStream baos;

    @Setup
    public void setup() throws IOException {
      byte[] bytes = "a".repeat(length).getBytes(charset);

      baos = new ByteArrayOutputStream(length);
      baos.write(bytes);
    }
  }

}
