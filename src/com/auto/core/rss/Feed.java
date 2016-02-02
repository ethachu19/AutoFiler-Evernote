/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auto.core.rss;

import static com.auto.core.GlobalUtil.print;
import com.evernote.edam.type.Note;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ethachu19
 */
public class Feed {
    private static SyndFeedInput input = new SyndFeedInput();
    public URL feedURL;
    public SyndFeed feedContent;
    private SyndEntryImpl last;
    
    public Feed(String urlString) throws MalformedURLException, IOException, IllegalArgumentException, FeedException {
        this.feedURL = new URL(urlString);
        feedContent = input.build(new XmlReader(feedURL));
    }
    
    public List<Note> getEntryData() {
        List<Note> res = new ArrayList<>();
        for (SyndEntryImpl list1 : (List<SyndEntryImpl>)feedContent.getEntries()) {
            if (list1 == null || list1.equals(last))
                break;
            Note addition = new Note();
//            print(addition + " " + last);
            addition.setTitle(list1.getTitle());
            addition.setContent(list1.getLink());
            res.add(addition);
        }
        last = (SyndEntryImpl) feedContent.getEntries().get(0);
        return res;
    }
}
