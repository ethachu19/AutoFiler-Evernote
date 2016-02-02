/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auto.core.rss;

import com.sun.syndication.io.FeedException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ethachu19
 */
public class FeedManager {

    public static List<Feed> feeds = new ArrayList<>();

    public static void createFeeds(String filename) {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("[SEVERE] File not found " + filename);
            return;
        }
        String data;
        try {
            data = br.readLine();
        } catch (IOException ex) {
            return;
        }
        while (data != null) {
            try {
                feeds.add(new Feed(data));
            } catch (MalformedURLException ex) {
                System.err.println("[SEVERE] " + data + " is not a valid RSS feed url");
            } catch (IOException | IllegalArgumentException ex) {
                return;
            } catch (FeedException ex) {
                System.err.println("[SEVERE] Could not create RSS feed");
            } finally {
                try {
                    data = br.readLine();
                } catch (IOException ex) {
                    return;
                }
            }
        }
    }
}
