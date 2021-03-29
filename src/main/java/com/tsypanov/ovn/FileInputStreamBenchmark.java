package com.tsypanov.ovn;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class FileInputStreamBenchmark {

    private final String file = "/home/s.tsypanov/.bashrc";
//    private final String file = "/home/s.tsypanov/Downloads/IMAG2549.jpg";

    @Benchmark
    public Object readAllBytes() throws IOException {
        try (var fileInputStream = new FileInputStream(file)) {
            return fileInputStream.readAllBytes();
        }
    }

    @Benchmark
    public Object readAllBytesBuffered() throws IOException {
        try (var in = new FileInputStream(file)) {
            return new BufferedInputStream(in).readAllBytes();
        }
    }
}
