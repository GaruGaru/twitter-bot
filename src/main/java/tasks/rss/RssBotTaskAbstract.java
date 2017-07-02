package tasks.rss;

import bot.BotTaskRunnable;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import persistence.Persister;
import persistence.PreferencePersister;

import java.net.URL;
import java.util.*;

/**
 * Created by garu on 02/07/17.
 */
public abstract class RssBotTaskAbstract implements BotTaskRunnable {

    private Persister<String[]> history;

    public RssBotTaskAbstract() {
        this.history = PreferencePersister.of("rss_history_" + feed(), String[].class);
      //  this.history = new MemoryPersister<>();
    }

    abstract String feed();

    private String getId(SyndEntry entry) {
        return entry.getTitle().trim().toLowerCase().replace(" ", "_");
    }

    private String feedKey() {
        return feed().toLowerCase().replace(" ", "_").replace(".", "_").trim();
    }

    private List<SyndEntry> getFeedEntries() {
        String url = feed();
        try {
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
            return feed.getEntries();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    protected Optional<SyndEntry> getArticle() {
        List<String> historyList = getHistoryList();
        return getFeedEntries().stream()
                .filter(entry -> entry.getTitle() != null)
                .filter(entry -> !historyList.contains(getId(entry)))
                .limit(10)
                .findFirst();
    }

    private List<String> getHistoryList() {
        return new ArrayList<>(Arrays.asList(history.get(feedKey()).orElse(new String[0])));
    }

    void markAsPosted(SyndEntry entry) {
        String id = getId(entry);
        List<String> historyList = getHistoryList();
        historyList.add(id);
        String[] historyArray = historyList.toArray(new String[historyList.size()]);
        history.save(feedKey(), historyArray);
    }


}
