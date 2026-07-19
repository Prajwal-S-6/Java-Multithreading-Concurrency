package multithreading.course260711.stream;

import java.util.stream.LongStream;

public class SumTillN {

    static void main() {
        var startTime = System.currentTimeMillis();
        var serialSum = LongStream.range(1, 1_000_000_000).reduce(Long::sum);
        System.out.printf("Serial sum is %d and time taken is %d ms \n", serialSum.getAsLong(), (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        var parallelSum = LongStream.range(1, 1_000_000_000).parallel().reduce(Long::sum);
        System.out.printf("Parallel sum is %d and time taken is %d ms \n", parallelSum.getAsLong(), (System.currentTimeMillis() - startTime));

    }
}
