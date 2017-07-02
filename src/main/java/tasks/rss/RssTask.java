package tasks.rss;

import com.rometools.rome.feed.synd.SyndEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter.TwitterApi;

import java.util.Optional;

/**
 * Created by garu on 02/07/17.
 */
public class RssTask extends RssBotTaskAbstract {

    public static RssTask create(String feedUri) {
        return new RssTask(feedUri);
    }

    private static final Logger logger = LoggerFactory.getLogger(RssTask.class);

    private final String feed;

    private RssTask(String feed) {
        super();
        this.feed = feed;
    }

    @Override
    public void run(TwitterApi twitter) {
        logger.info("RssTask: {}", feed);
        Optional<SyndEntry> articleOptional = getArticle();

        if (articleOptional.isPresent()) {
            SyndEntry article = articleOptional.get();
            String twit = article.getTitle() + " - " + article.getLink();

            twitter.twit(twit);

            markAsPosted(article);
            logger.info("Posted: {}", twit);
        } else {
            logger.warn("No articles found for: {}", feed());
        }

    }

    @Override
    String feed() {
        return this.feed;
    }
}
