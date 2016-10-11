package com.github.myyk.cracking.chapter7;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Design a JukeBox
 *
 * Differences with the book's answer:
 *   - The major thing I forgot was a player of some sort. I guess it's 2016 and I
 *   don't really think of the challenges of looking up CDs and kind of just assumed
 *   that a Song could be .play()'d.
 *   - I also didn't explicitly put in the display. Maybe because I'm a backend dude,
 *   I just figured I was building the backend for someone to use when building the
 *   frontend, which might not be entirely wrong.
 *
 * In Scala: I would have made various components into Traits which I could mix in.
 *   For example, I'd have these traits: Catalog, PaymentMachine, AdminInterface
 */
public class JukeBox {
  // assuming this is like an old jukebox with pages and stuff
  private final Catalog catalog;
  private final Map<Integer, Integer> creditPrices;
  private int earnedMoney; // can't be returned
  private int pendingMoney; // in pennies, assuming USD is fine for this
  private int songCredits; // user buys credits, then selects songs
  private Queue<Song> playlist = new LinkedList<Song>();
  private int maxInQueue = Integer.MAX_VALUE;

  // could maybe make edits to catalog through an admin interface
  public JukeBox(List<List<Song>> catalog, Map<Integer, Integer> creditPrices) {
    this.catalog = new Catalog(catalog);
    this.creditPrices = creditPrices;
  }

  public void selectSong(Song song) {
    if (getSongCredits() <= 0) {
      throw new IllegalStateException("Not enough credits to queue a song.");
    } else {
      setSongCredits(getSongCredits() - 1);
      playlist.add(song);
    }
  }

  public int buySongCredits(int numCredits) {
    if (playlist.size() >= maxInQueue) {
      throw new IllegalStateException("Too many songs in queue to add credits.");
    }
    if (!creditPrices.containsKey(numCredits)) {
      throw new RuntimeException("Can't buy [" + numCredits
          + "], programmer error.");
    }
    int price = creditPrices.get(numCredits);
    if (pendingMoney < price) {
      throw new IllegalStateException("Insufficient funds.");
    }
    pendingMoney -= price;
    earnedMoney += price;
    return returnMoney();
  }

  public void addMoney(int pennies) {
    pendingMoney += pennies;
  }

  public int returnMoney() {
    int temp = pendingMoney;
    pendingMoney = 0;
    return temp;
  }

  public int getSongCredits() {
    return songCredits;
  }

  public void setSongCredits(int songCredits) {
    this.songCredits = songCredits;
  }

  public static class Catalog {
    private final List<List<Song>> catalog;
    private int currentPage = 0;

    public Catalog(List<List<Song>> catalog) {
      super();
      this.catalog = catalog;
    }

    public void nextPage() {
      if (currentPage + 1 < catalog.size()) {
        currentPage++;
      }
    }

    public void prevPage() {
      if (currentPage - 1 >= 0) {
        currentPage--;
      }
    }

    public List<Song> getSongsOnPage() {
      return catalog.get(currentPage);
    }

    public List<List<Song>> getCatalog() {
      return catalog;
    }
  }

  public static class Song {
    Artist artist;
    String name;
  }

  // Band or otherwise
  public static class Artist {
    String name;
  }
}
