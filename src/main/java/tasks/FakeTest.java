package tasks;

import bot.BotTaskRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter.TwitterApi;

/**
 * Created by garu on 02/07/17.
 */
public class FakeTest implements BotTaskRunnable {
    public static BotTaskRunnable create() {
        return new FakeTest();
    }

    private FakeTest() {
    }

    private static final Logger logger = LoggerFactory.getLogger(FakeTest.class);

    @Override
    public void run(TwitterApi twitter) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Run fake test :D");
    }
}
