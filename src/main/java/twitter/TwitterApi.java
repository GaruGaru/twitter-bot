package twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by garu on 02/07/17.
 */
public class TwitterApi {

    public static TwitterApi fromTwitter(Twitter twitter){
        return new TwitterApi(twitter);
    }

    private final Twitter twitter;

    private TwitterApi(Twitter twitter) {
        this.twitter = twitter;
    }

    public boolean twit(String message) {
        try {
            twitter.updateStatus(message);
            return true;
        } catch (TwitterException e) {
            e.printStackTrace();
            return false;
        }
    }


}
