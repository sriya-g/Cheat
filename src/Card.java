public class Card implements Comparable<Card> {
private int suit;
private int value;
/**
* Constructor for objects of class Card
*/
public Card() {
suit = 1;
value = 2;
}
public int getSuit() {
	return suit;
}
public void setSuit(int suit) {
	this.suit = suit;
}
public int getValue() {
	return value;
}
public void setValue(int value) {
	this.value = value;
}
public Card(int mySuit, int myValue) {
suit = mySuit;
value = myValue;
}
public String toString() {
	String cardStr = "";
	if (value == 14) {
		cardStr += "A";
	}
	if (value == 11) {
		cardStr += "J";
	}
	if (value == 12) {
		cardStr += "Q";
	}
	if (value == 13) {
		cardStr += "K";
	}
	if (cardStr.equals("")) {
		cardStr += value;
	}
	switch(suit) {
	case 1: cardStr += "♣";
	break;
	case 2: cardStr += "♦";
	break;
	case 3: cardStr += "♥";
	break;
	case 4: cardStr += "♠";
	break;
	}
	return cardStr;
}
public static void main(String[] args) {
Card one = new Card();
Card two = new Card(1,4);
Card three= new Card(1,11);
System.out.println(one);
System.out.println(two);
System.out.println(three);
}
@Override
public int compareTo(Card o) {
	return (value - o.getValue());
}
}