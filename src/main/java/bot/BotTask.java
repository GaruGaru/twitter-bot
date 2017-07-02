package bot;

import twitter.TwitterApi;

import java.util.concurrent.TimeUnit;

/**
 * Created by garu on 02/07/17.
 */
public class BotTask {

    private BotTask(BotTaskRunnable taskRunnable, long repeatDelay, TimeUnit repeatTimeUnit) {
        this.taskRunnable = taskRunnable;
        this.repeatTimeUnit = repeatTimeUnit;
        this.repeatDelay = repeatDelay;
    }

    public static Builder builder() {
        return new Builder();
    }

    private BotTaskRunnable taskRunnable;

    private TimeUnit repeatTimeUnit;

    private long repeatDelay;


    public BotTaskRunnable getTaskRunnable() {
        return taskRunnable;
    }

    public long getRepeatDelay() {
        return repeatDelay;
    }

    public TimeUnit getRepeatTimeUnit() {
        return repeatTimeUnit;
    }

    public Runnable toRunnable(TwitterApi api) {
        return () -> getTaskRunnable().run(api);
    }

    public static class Builder {

        private Builder() {
        }

        private BotTaskRunnable taskRunnable;
        private long repeatDelay;
        private TimeUnit repeatUnit;

        public Builder task(BotTaskRunnable taskRunnable) {
            this.taskRunnable = taskRunnable;
            return this;
        }

        public Builder repeatEvery(long value, TimeUnit timeUnit) {
            this.repeatDelay = value;
            this.repeatUnit = timeUnit;
            return this;
        }

        public BotTask build() {
            return new BotTask(this.taskRunnable, this.repeatDelay, this.repeatUnit);
        }

    }

}
