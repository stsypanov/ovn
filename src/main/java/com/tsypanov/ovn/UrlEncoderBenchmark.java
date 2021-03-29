package com.tsypanov.ovn;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class UrlEncoderBenchmark {
  private static final Charset charset = Charset.defaultCharset();
  private static final String utf8Url = "https://ru.wikipedia.org/wiki/Организация_Объединённых_Наций";

  @Benchmark
  public String encode() {
    return URLEncoder.encode(utf8Url, charset);
  }
}
