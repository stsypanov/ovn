package com.tsypanov.ovn;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class ResourceLoadBenchmark {

  private Resource resource;

  @Setup
  public void setUp(){
    DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
    resource = defaultResourceLoader.getResource("classpath:org/springframework/boot/autoconfigure/orm/jpa/DataSourceInitializedPublisher$Registrar.class");
  }

  @Benchmark
  public Object read() throws IOException {
    try (InputStream is = resource.getInputStream()) {
      return new ClassReader(is);
    }
  }

  @Benchmark
  public Object readBuffered() throws IOException {
    try (InputStream is = new BufferedInputStream(resource.getInputStream())) {
      return new ClassReader(is);
    }
  }

}
