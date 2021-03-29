package com.tsypanov.ovn;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.asm.ClassReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BaisResourceLoadBenchmark {

  private byte[] bytes;

  @Setup
  public void setUp() throws IOException {
    DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
    Resource resource = defaultResourceLoader.getResource(
              "classpath:org/springframework/boot/autoconfigure/orm/jpa/DataSourceInitializedPublisher$Registrar.class");
    bytes = resource.getInputStream().readAllBytes();
  }

  @Benchmark
  public Object read() throws IOException {
    return new ClassReader(new ByteArrayInputStream(bytes));
  }

  @Benchmark
  public Object readBuffered() throws IOException {
     return new ClassReader(new BufferedInputStream(new ByteArrayInputStream(bytes)));
  }

}
