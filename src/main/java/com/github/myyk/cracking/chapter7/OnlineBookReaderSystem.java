package com.github.myyk.cracking.chapter7;

import java.util.Date;
import java.util.Set;

/**
 * Design an online book reader system.
 *
 * The major problem I have with this question is, what the fuck is an online book
 *   reader system. Without being able to chat with an interviewer, it's not obvious
 *   what I'm supposed to build because this isn't like a common concept like a jukebox
 *   or a parking lot.
 *
 * This problem is dumb, I'm leaving it pretty incomplete because it's dumb.
 */
public class OnlineBookReaderSystem {
  public class Book {
    String title;
    long assetId;
    Author author;
    Publisher publisher;
  }
  public class Note {
    Book book;
    long position;
    String text;
  }
  public class Reader {
    String name;
    String language;
    Date birthday;
  }
  public class Library {
    Reader reader;
    Set<Book> books;
    Set<Note> notes;
  }
  public class Author {
    String name;
  }
  public class Publisher {}
  /* BookStore? */

  public class BookFinder {}
  /*
   * Would ask interviewer about asset delivery, search, store, language
   */
}
