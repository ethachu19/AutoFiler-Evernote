
import com.auto.core.evernote.EvernoteUser;
import static com.auto.core.GlobalUtil.print;
import com.auto.core.evernote.UserFactory;
import com.auto.core.alchemy.Extractor;
import com.auto.core.rss.Feed;
import com.auto.core.rss.FeedManager;
import com.evernote.edam.type.Note;
import java.util.List;

/**
 * It Reads and prints any RSS/Atom feed type.
 * <p>
 * @author Alejandro Abdelnur
 *
 */
public class Main {

    public static void main(String[] args) throws Exception {
        FeedManager.createFeeds("RSSFeeds.txt");
        EvernoteUser ev = UserFactory.createUser();
        int i = 0;
        while (true) {
            for (Feed f : FeedManager.feeds) {
                List<Note> list = f.getEntryData();
                list = Extractor.extractFromURL(list);
                for (Note n : list) {
                    try {
                        ev.addNote(n);
                    } catch (Exception e) {
                        continue;
                    }
                    print("" + ++i);
                }
            }
            Thread.sleep(1);
        }
//        System.out.println(getStringFromDocument(Extractor.alchemy.URLGetRankedKeywords("http://www.nytimes.com/2016/01/29/us/drug-shortages-forcing-hard-decisions-on-rationing-treatments.html?hp&action=click&pgtype=Homepage&clickSource=story-heading&module=first-column-region&region=top-news&WT.nav=top-news")));
//        print(Extractor.extractKeywordsFromURL("http://www.theguardian.com/us-news/2016/jan/29/iowa-caucus-turnout-snow-storm-republicans-democrats-sanders-trump").toString());
//        print(Extractor.getTagsFromURL("http://www.nytimes.com/2016/01/29/us/drug-shortages-forcing-hard-decisions-on-rationing-treatments.html?hp&action=click&pgtype=Homepage&clickSource=story-heading&module=first-column-region&region=top-news&WT.nav=top-news").toString());
//        print(getStringFromDocument(Extractor.alchemy.URLGetText("http://www.theguardian.com/us-news/2016/jan/29/iowa-caucus-turnout-snow-storm-republicans-democrats-sanders-trump")));
    }

}
