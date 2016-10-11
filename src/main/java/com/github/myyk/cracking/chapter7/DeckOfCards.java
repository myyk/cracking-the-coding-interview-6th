package com.github.myyk.cracking.chapter7;

import java.util.ArrayList;
import java.util.Set;

/**
 * Deck of Cards: Design a generic deck of cards. Explain how to subclass the data
 *   structures to implement BlackJack
 *
 * BlackJack:
 *   BlackJack could be implemented by keeping track of players hands. It would also
 *   need to be able to calculate the value of the had from the cards in it. It would
 *   need the concept of hidden versus revealed cards as well. I'm not sure enough how
 *   split and other more advanced blackjack concepts work.
 *
 * Differences with the book's answer:
 *   - I forgot about Enums in Java because of the lack of their usage in Scala. I think
 *   using those for Suits is quite appropriate and better than what I did.
 *   - I don't like that they added dealing of hands to the deck, this seems more of
 *   a responsibility of the game as some games might require them to be dealt a certain
 *   way because stacking the deck can be part of the game. I also don't really think a
 *   Deck should know what a Hand is even.
 *   - I'm not a huge fan of setDeckOfCards, I like setting the cards on the deck which
 *   can be used to restore the deck to be full again when shuffling the hands into it.
 *   Alternatively, a new deck could be created.
 *   - Adding a discard could arguable be part of the Game instead, but they are usually
 *   kind of the same across most games, where some don't care about an ordering and some
 *   do. I think it's addition is not bad.
 *   
 */
// I'm being a bit lazy here by not creating other classes in other files and using
// comments as if they were
public class DeckOfCards<T /*extends Card*/ > {
  private ArrayList<T> deck;
  private ArrayList<T> discard;
  private Set<T> cards;

  public DeckOfCards(Set<T> cards) {
    super();
    this.cards = cards;
  }

  public void shuffle() {}
  public void shuffleDiscardWithDeck() {}

  public PokerCard dealNextCard() {
    return null;
  }

  public int cardsLeftInDeck() { return deck.size(); }
  public int cardsLeftInDiscard() { return discard.size(); }

  public void putOnTop(Card card) {}

  public void putOnBottom(Card card) {}

  public void takeFromDiscard(Card card) {}

  public static interface Card {    
  }

  public static class PokerCard implements Card {
    int num;
    Suit suit;

    public boolean isJack() { return num == 11; }
    public boolean isQueen() { return num == 12; }
    public boolean isKing() { return num == 13; }
    public boolean isAce() { return num == 1; }

    public static interface Suit{}
    public static class Hearts implements Suit {}
    public static class Spades implements Suit {}
    public static class Clubs implements Suit {}
    public static class Diamonds implements Suit {}
  }

  public static DeckOfCards<PokerCard> getDeckOfPokerCards() {
    // create DeckOfCards with 13 PokerCards of each suit
    return null;
  }
}