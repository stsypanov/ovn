package com.tsypanov.ovn;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class BufferedReaderBenchmark {

  private URL url;
  private File file;

  @Setup
  public void setUp() throws IOException, URISyntaxException {
    URL resource = getClass()
      .getClassLoader()
      .getResource("com/tsypanov/ovn/BufferedReaderBenchmark.class");
    String path = resource.getFile();
    url = resource.toURI().toURL();
    file = new File(path);
    if (!file.exists()) {
      throw new FileNotFoundException(file.getPath());
    }
  }

  @Benchmark
  public void readFromFile(Blackhole bh) throws IOException {
    int value;
    try (InputStream is = new FileInputStream(file)) {
      while ((value = is.read()) != -1) {
        bh.consume(value);
      }
    }
  }

  @Benchmark
  public void bufferedReadFromFile(Blackhole bh) throws IOException {
    int value;
    try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
      while ((value = is.read()) != -1) {
        bh.consume(value);
      }
    }
  }

  @Benchmark
  public void readFromURL(Blackhole bh) throws IOException {
    int value;
    try (InputStream is = url.openStream()) {
      while ((value = is.read()) != -1) {
        bh.consume(value);
      }
    }
  }

  @Benchmark
  public void readBufferedFromURL(Blackhole bh) throws IOException {
    int value;
    try (InputStream original = url.openStream();
         InputStream is = new BufferedInputStream(original)) {
      while ((value = is.read()) != -1) {
        bh.consume(value);
      }
    }
  }

}
