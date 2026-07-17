package multithreading.course260711.stream;

import java.util.stream.IntStream;

public class CountPrime {

    static void main() {
        var startTime = System.currentTimeMillis();
        long serialCount = IntStream.range(1, 100_000_000).filter(CountPrime::IsPrime).count();
        System.out.printf("Serial count is %d and time is %d ms \n", serialCount, (System.currentTimeMillis() - startTime));


        startTime = System.currentTimeMillis();
        long parallelCount = IntStream.range(1, 100_000_000).parallel().filter(CountPrime::IsPrime).count();
        System.out.printf("Parallel count is %d and time is %d ms \n", parallelCount, (System.currentTimeMillis() - startTime));
    }

    static boolean IsPrime(int n) {
        if(n <=1) return false;
        if(n == 2) return true;
        if(n % 2 == 0) return false;

        for (int i = 3; i <= Math.sqrt(n); i+=2) {
            if(n % i == 0) return false;
        }
        return true;
    }
}
