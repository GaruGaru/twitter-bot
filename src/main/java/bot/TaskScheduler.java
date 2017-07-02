package bot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by garu on 02/07/17.
 */
public class TaskScheduler {

    private static final int DEFAULT_POOL_SIZE = 1;

    public static TaskScheduler create(){
        return new TaskScheduler();
    }

    public static TaskScheduler create(int poolSize){
        return new TaskScheduler(poolSize);
    }

    private ScheduledExecutorService executor;

    private TaskScheduler(int poolSize) {
        this.executor = Executors.newScheduledThreadPool(poolSize);
    }

    private TaskScheduler() {
        this(DEFAULT_POOL_SIZE);
    }

    public void scheduleRepeat(Runnable runnable, long unit, TimeUnit timeUnit) {
        executor.scheduleAtFixedRate(runnable, 0, unit, timeUnit);
    }
}
