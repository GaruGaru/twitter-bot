package bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter.TwitterApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by garu on 02/07/17.
 */
public class TwitterBot {

    private static final Logger logger = LoggerFactory.getLogger(TwitterBot.class.getSimpleName());

    public static Builder builder() {
        return new Builder();
    }

    private TaskScheduler taskScheduler;

    private TwitterApi twitterApi;

    private List<BotTask> botTaskList;

    public TwitterBot(TwitterApi twitterApi, List<BotTask> botTaskList) {
        this.twitterApi = twitterApi;
        this.botTaskList = botTaskList;
        this.taskScheduler = TaskScheduler.create(botTaskList.size());
    }

    public void executeTask(BotTaskRunnable task) {
        logger.info("Running task: {}", task.getClass().getSimpleName());
        task.run(twitterApi);
    }

    public void run() {
        logger.info("TwitterBot starting with {} tasks", botTaskList.size());
        logger.info("Tasks: " + botTaskList.stream().map(t -> t.getClass().getSimpleName()).collect(Collectors.joining(", ")));
        botTaskList.forEach(task ->
                taskScheduler.scheduleRepeat(task.toRunnable(twitterApi), task.getRepeatDelay(), task.getRepeatTimeUnit())
        );
        logger.info("Tasks schedule completed");
    }

    public static class Builder {

        private TwitterApi twitter;
        private List<BotTask> tasks;

        private Builder() {
            this.tasks = new ArrayList<>();
        }

        public Builder twitter(TwitterApi twitter) {
            this.twitter = twitter;
            return this;
        }

        public Builder task(BotTask task) {
            this.tasks.add(task);
            return this;
        }

        public Builder task(BotTaskRunnable taskRunnable, long timeValue, TimeUnit timeUnit) {
            this.task(BotTask.builder()
                    .task(taskRunnable)
                    .repeatEvery(timeValue, timeUnit)
                    .build());
            return this;
        }

        public TwitterBot build() {
            if (this.twitter == null)
                throw new IllegalArgumentException("Twitter instance can't be null.");
            return new TwitterBot(this.twitter, this.tasks);
        }

    }

}
