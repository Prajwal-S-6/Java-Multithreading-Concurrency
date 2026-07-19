package multithreading.course260711;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MapWorker implements Runnable {

    private Map<String, Integer> integerMap;

    public MapWorker(Map<String, Integer> integerMap) {
        this.integerMap = integerMap;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            integerMap.compute("A", (k, v) -> v == null ? 1 : v + 1);
        }
    }

    public Map<String, Integer> getIntegerMap() {
        return integerMap;
    }
}
public class MapConcurrecny {
    static void main() throws InterruptedException {
        MapWorker mapWorker = new MapWorker(new ConcurrentHashMap<>());

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(mapWorker);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println(mapWorker.getIntegerMap().get("A"));

    }
}
