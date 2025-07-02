package main;
import java.util.ArrayList;

import Cards.Card;
import Cards.Guard;

public class Test {

	public static void main(String[] args) {
		
		Hand hand = new Hand("dammy");
		Com com = new Com(0);
		Com enemyCom = new Com(100);
		ArrayList<Hand> players = new ArrayList<Hand>();
		players.add(hand);
		players.add(com);
		players.add(enemyCom);
		
		
		hand.efFlag = false;
		
		Deck deck = new Deck(players);
		Card test = new Guard(players);
		test.setDeck(deck);
		hand.drawCard(deck);
		enemyCom.drawCard(deck);
		
		com.addCard(test);
		com.addCard(test);
		com.remove();
		com.addCard(test);
		enemyCom.drawCard(deck);
		enemyCom.remove();
		com.remove();
		com.addCard(test);
		com.remove();
		com.addCard(test);
		com.remove();	
	}
	
}
