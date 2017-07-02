import bot.TwitterBot;
import tasks.rss.RssTask;
import twitter.TwitterApi;
import twitter4j.TwitterFactory;

import java.util.concurrent.TimeUnit;


/**
 * Created by garu on 02/07/17.
 */
public class Main {

    public static void main(String[] args) {

        TwitterBot bot = TwitterBot.builder()
                .twitter(TwitterApi.fromTwitter(TwitterFactory.getSingleton()))
                .task(RssTask.create("https://www.reddit.com/r/programming/.rss"), 6, TimeUnit.HOURS)
                .build();

        bot.run();

    }

}
