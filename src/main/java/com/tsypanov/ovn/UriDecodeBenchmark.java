package com.tsypanov.ovn;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.springframework.util.StringUtils;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class UriDecodeBenchmark {
    private static final String encoded =
            "https%3A%2F%2Fru.wikipedia.org%2Fwiki%2F%D0%9E%D1%80%D0%B3%D0%B0%D0"
            + "%BD%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F_%D0%9E%D0%B1%D1%8A%D0%B5%D0"
            + "%B4%D0%B8%D0%BD%D1%91%D0%BD%D0%BD%D1%8B%D1%85_%D0%9D%D0%B0%D1%86%D0"
            + "%B8%D0%B9";

    @Benchmark
    public String uriDecode() {
        return StringUtils.uriDecode(encoded, Charset.defaultCharset());
    }
}
