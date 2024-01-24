import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Cheat {
	static Scanner keyboard = new Scanner(System.in);
	public static CheatCPU findCPUByName(String name, Queue<CheatCPU> cpuList) {
		for (CheatCPU c : cpuList) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println("Welcome to Cheat!");
		//generate CPUS
		int numCPU = 500;
		do {System.out.println("How many CPUs will be joining us today? Up to 12 can join!");
		numCPU = keyboard.nextInt();}
		while(numCPU > 12 || numCPU < 1);
		LinkedList<CheatCPU> cpus = new LinkedList<CheatCPU>();
		for (int i = 0; i < numCPU; i++) {
			cpus.add(new CheatCPU("CPU "+(i+1), i+1));
		}
		cpus.add(new CheatCPU("PLAYER", 0)); //represents player
		//randomly determine turn order & create turn queue
		Queue<CheatCPU> turnOrder = new LinkedList<CheatCPU>();
		while (cpus.size() > 0) {
			int randInt = (int)(Math.random()*(cpus.size()));
			turnOrder.offer(cpus.remove(randInt));
		}
		//print player order
		System.out.println("The turn order is: ");
		int count = 0;
		for (CheatCPU c : turnOrder) {
			System.out.print(c.getName());
			count++;
			if (count != turnOrder.size()) {
				System.out.print(", ");
			}
		}
		//deal cards
		System.out.println("\nCards will now be dealt!");
		int numDecks = 1;
		if (numCPU > 6) {
			System.out.println("Since this is a big game, we'll be playing with 2 decks!");
			numDecks = 2;
		}
		if ((numDecks*52) % (numCPU + 1) != 0) {
			System.out.println("The cards can't be divided equally... looks like some players will start with less cards!");
		}
		Deck mainDeck = new Deck();
		mainDeck.completeShuffle();
		if (numDecks == 2) {
			Deck mainDeck2 = new Deck();
			mainDeck2.completeShuffle();
			for (Card c : mainDeck2.getDeck()) {
				mainDeck.addCardToDeck(c);
			}
		}
		while(mainDeck.getNumCards() > 0) {
			CheatCPU current = turnOrder.poll();
			current.addcard(mainDeck.deal());
			turnOrder.offer(current);
		}
		//show player their cards
		System.out.println("\nHere are your cards!");
		ArrayList<Card> playerCards = new ArrayList<Card>();
		for (CheatCPU c : turnOrder) {
			if (c.getName().equals("PLAYER")) {
				System.out.println(c.getCpuCards().getDeck());
				playerCards.addAll(c.getCpuCards().getDeck());
			}
		}
		//explain rules
		System.out.println("\nRULES:");
		System.out.println("Players will take turns placing cards in order by number/rank, starting with aces.");
		System.out.println("If you have multiple of a certain rank, you can choose to place multiple.");
		System.out.println("If you don't have the card you need, you can cheat by placing another card... but you may be caught!.");
		System.out.println("If you're caught cheating, you'll have to take the entire pile of cards.");
		System.out.println("However, false accusations will result in the accuser taking all the cards instead! So be careful!");
		System.out.println("The goal is to be the first to get rid of every card in your hand. Have fun!\n");
		System.out.println("GAME START");
		//start game
		Deck cardPile = new Deck(false);
		boolean cpuWon = false;
		int currentNum = 2;
		while (playerCards.size() > 0 && !cpuWon) {
			currentNum = currentNum % 15;
			for (CheatCPU c : turnOrder) {
				if (currentNum < 11) {
					System.out.println("Current card: "+(currentNum));
				}
				else {
					if (currentNum == 14) {
						System.out.println("Current card: A");
					}
					if (currentNum == 11) {
						System.out.println("Current card: J");
					}
					if (currentNum == 12) {
						System.out.println("Current card: Q");
					}
					if (currentNum == 13) {
						System.out.println("Current card: K");
					}
				}
				if (c.getName().equals("PLAYER")) { //player turn
					System.out.println("It's your turn! Here are your cards:");
					System.out.println(playerCards);
					String[] cardsToPlay = new String[0];
					do {
						System.out.println("Which cards would you like to play? (Enter the positions of the cards from the left, seperated by ' ')");
						keyboard.nextLine();
						cardsToPlay = keyboard.nextLine().split(" ");
					}
					while(cardsToPlay.length < 1);
					String cardAddedStr = "You added ";
					for (int i = 0; i < cardsToPlay.length; i++) {
						cardAddedStr += playerCards.get(Integer.parseInt(cardsToPlay[i]))+" ";
						cardPile.addCardToDeck(playerCards.remove(Integer.parseInt(cardsToPlay[i])));
						for (int r = i; r < cardsToPlay.length; r++) {
							cardsToPlay[r] = Integer.toString(Integer.parseInt(cardsToPlay[r]) - 1);
						}
					}
					System.out.println(cardAddedStr+" to the pile");
				}
				else {
					ArrayList<Card> possibleCards = c.getCpuCards().cardsWithNum(currentNum);
					int numAdded = 0;
					if (possibleCards.isEmpty() || c.getCheatChance() <= Math.random()) { //cpu cheats
						int numToAdd = (int)(Math.random()*4) + 1;
						numAdded = numToAdd;
						cardPile.addCardToDeck(c.getCpuCards().deal());
						System.out.println(c.getName()+" added "+numToAdd+" cards to the pile!");
					}
					else { //cpu plays normally
						for (Card w : possibleCards) {
							cardPile.addCardToDeck(w);
						}
						System.out.println(c.getName()+" added "+possibleCards.size()+" cards to the pile!");
					}
					System.out.println("Would you like to accuse them of cheating? (Y/N) ");
					String willAccuse = keyboard.next();
					if (willAccuse.equalsIgnoreCase("Y")) {
						System.out.println("Checking cards...");
						String cardsAddedStr = "";
						boolean cheated = false;
						if (cardPile.checkTopCard().getValue() == currentNum) {
							Queue<Card> enteredCards = cardPile.checkTopNCards(currentNum);
							for (Card cC : enteredCards) {
								cardsAddedStr += cC.toString();
								if (cC.getValue() != currentNum) {
									cheated = true;
								}
							}
						}
						System.out.println(c.getName() + " placed "+cardsAddedStr);
						if (cheated) {
							System.out.println(c.getName() + " was caught cheating!");
							System.out.println(c.getName() + " took all cards in the pile!");
							Deck cpuNewDeck = c.getCpuCards();
							for (int i = 0; i < cardPile.getDeck().size(); i++) {
								cpuNewDeck.addCardToDeck(cardPile.deal());
							}
							c.setCpuCards(cpuNewDeck);
						}
						else {
							System.out.println(c.getName() + " was innocent!");
							System.out.println("You took all cards in the pile!");
							for (int i = 0; i < cardPile.getDeck().size(); i++) {
								playerCards.add(cardPile.deal());
							}
						}
					}
					else if (c.getAccuseChance() <= Math.random()) {
						int accuseNum = c.getNum();
						do {
							accuseNum = (int)(Math.random()*numCPU) + 1;
						}
						while (accuseNum == c.getNum());
						System.out.println("CPU "+accuseNum+ " accused "+c.getName()+" of cheating!");
						System.out.println("Checking cards...");
						String cardsAddedStr = "";
						boolean cheated = false;
						if (cardPile.checkTopCard().getValue() == currentNum) {
							Queue<Card> enteredCards = cardPile.checkTopNCards(currentNum);
							for (Card cC : enteredCards) {
								cardsAddedStr += cC.toString();
								if (cC.getValue() != currentNum) {
									cheated = true;
								}
							}
						}
						System.out.println(c.getName() + " placed "+cardsAddedStr);
						if (cheated) {
							System.out.println(c.getName() + " was caught cheating!");
							System.out.println(c.getName() + " took all cards in the pile!");
							Deck cpuNewDeck = c.getCpuCards();
							for (int i = 0; i < cardPile.getDeck().size(); i++) {
								cpuNewDeck.addCardToDeck(cardPile.deal());
							}
							c.setCpuCards(cpuNewDeck);
						}
						else {
							System.out.println(c.getName() + " was innocent!");
							System.out.println("CPU "+accuseNum+" took all cards in the pile!");
							Deck cpuNewDeck = findCPUByName("CPU "+accuseNum, turnOrder).getCpuCards();
							for (int i = 0; i < cardPile.getDeck().size(); i++) {
								playerCards.add(cardPile.deal());
							}
							findCPUByName("CPU "+accuseNum, turnOrder).setCpuCards(cpuNewDeck);
						}
					}

					if (c.getCpuCards().getDeck().isEmpty()) {
						cpuWon = true;
						System.out.println("GAME OVER\n"+c.getName()+" won!");
						break;
					}
				}
				currentNum++;
				System.out.println();
			}
		}
	}
}
