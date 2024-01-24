import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Deck {
	Queue<Card> deck;
	public Deck (boolean f) {
		deck = new LinkedList<Card>();
	}
	public Deck () {
		deck = new LinkedList<Card>();
		loadDeck();
	}

	public 	Queue<Card> getDeck() {
		return deck;
	}
	public void addCardToDeck(Card c) {
		deck.offer(c);
	}
	// Load the stack with 52 cards in order
	public void loadDeck() {
		for (int suit = 1; suit <= 4; suit++) {
			for (int val = 2; val <= 14; val++) {
				deck.offer(new Card(suit, val));
			}
		}
	}

	public String toString() {
		String deckStr = "";
		int count = 0;
		for (Card c : deck) {
			if (count == 4) {
				deckStr += "\n";
				count = 0;
			}
			deckStr += c + " ";
			count++;
		}
		return deckStr;
	}
	// Return and remove the top card
	public Card deal() {
		return deck.poll();
	}
	public Card checkTopCard() {
		return deck.peek();
	}
	//returns the top n cards from the deck (without removing)
	public Queue<Card> checkTopNCards(int n) {
		Queue<Card> tempDeck = new LinkedList<Card>();
		tempDeck.addAll(deck);
		for (int i = 0; i < tempDeck.size() - n; i++) {
			tempDeck.poll();
		}
		return tempDeck;
	}
	// Take the top half of the deck (26 cards) and alternate card by card with
	// the bottom half
	public void bridgeShuffle() {
		Queue<Card> tempDeck = new LinkedList<Card>();
		for (int i = 0; i < 26; i++) {
			tempDeck.offer(deck.poll());
		}
		Queue<Card> newDeck = new LinkedList<Card>();
		for (int i = 0; i < 52; i++) {
			if (i % 2 == 0) {
				newDeck.offer(deck.poll());
			}
			else {
				newDeck.offer(tempDeck.poll());
			}
		}
		deck = newDeck;
	}
	public int getNumCards() {
		return deck.size();
	}
// split the deck at a random spot. Put the stack of cards above the random
	// spot below the other cards
	public void cut() {
		Queue<Card> tempDeck = new LinkedList<Card>();
		int randSpot = (int)(Math.random()*52) + 1;
		for (int i = 0; i < randSpot; i++) {
			tempDeck.offer(deck.poll());
		}
		while (tempDeck.size() > 0) {
			deck.offer(tempDeck.poll());
		}
	}
	// complete a bridge shuffle and then cut the deck. Repeat 7 times
	public void completeShuffle() {
		for (int i = 0; i < 7; i++) {
			bridgeShuffle();
			cut();
		}
}
	public void organizeDeck() {
		
	}
	// shuffle the deck using your own algorithm
	public void customShuffle() { //split into 4 decks of 13 then choose deck at random to add 1 card back
		Queue<Card> deck1 = new LinkedList<Card>();
		Queue<Card> deck2 = new LinkedList<Card>();
		Queue<Card> deck3 = new LinkedList<Card>();
		Queue<Card> deck4 = new LinkedList<Card>();
		Queue<Card> newDeck = new LinkedList<Card>();
		for (int i = 0; i < 13; i++) {
			deck1.offer(deck.poll());
			deck2.offer(deck.poll());
			deck3.offer(deck.poll());
			deck4.offer(deck.poll());
		}
		while (newDeck.size() < 52) {
			int randNum = (int)(Math.random()*4);
			if (randNum == 0 && deck1.size() > 0) {
				newDeck.offer(deck1.poll());
			}
			if (randNum == 1 && deck2.size() > 0) {
				newDeck.offer(deck2.poll());
			}
			if (randNum == 2 && deck3.size() > 0) {
				newDeck.offer(deck3.poll());
			}
			if (randNum == 3 && deck4.size() > 0) {
				newDeck.offer(deck4.poll());
			}
		}
		deck = newDeck;
	}
	public ArrayList<Card> cardsWithNum(int val) {
		ArrayList<Card> cardsToOffer = new ArrayList<Card>();
		for (Card c : deck) {
			if (c.getValue() == val) {
				cardsToOffer.add(c);
			}
		}
		return cardsToOffer;
	}
	
	public static void main(String[] args) {
		Deck a = new Deck();
System.out.println("Ordered deck");
		System.out.println(a);
a.bridgeShuffle();
System.out.println("After 1 bridge shuffle");
		System.out.println(a);
		System.out.println("After 1 cut");
		a.cut();
		System.out.println(a);
		a.completeShuffle();
		System.out.println("After complete shuffle. ");
		System.out.println(a);
		System.out.println("After custom shuffle. Deal 5");
		a.customShuffle();
for(int i=1; i<=5 ;i++)
				System.out.println(a.deal());

	}
}
