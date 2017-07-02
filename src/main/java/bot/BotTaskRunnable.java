package bot;

import twitter.TwitterApi;

/**
 * Created by garu on 02/07/17.
 */
public interface BotTaskRunnable {
    void run(TwitterApi twitter);
}
