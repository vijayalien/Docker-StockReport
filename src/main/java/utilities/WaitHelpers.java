package utilities;

import org.testng.Reporter;

import java.util.Random;
import java.util.concurrent.*;

public class WaitHelpers {
    public static void randomWaits(int minMiliseconds, int maxMiliseconds) {
        Random random = new Random();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable() {
                                                                                public Object call() throws Exception {
                                                                                    return "Random wait stopped...";
                                                                                }
                                                                            },
                random.ints(minMiliseconds, maxMiliseconds).findFirst().getAsInt(),
                TimeUnit.MILLISECONDS);
    }

    public static void wait(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
