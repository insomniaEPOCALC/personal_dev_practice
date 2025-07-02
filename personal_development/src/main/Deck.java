package main;

import java.util.ArrayList;
import java.util.Collections;

import Cards.Baron;
import Cards.Bishop;
import Cards.Card;
import Cards.Countess;
import Cards.Guard;
import Cards.King;
import Cards.Maid;
import Cards.Prince;
import Cards.Princess;

public class Deck {
	private ArrayList<Card> cardList = new ArrayList<Card>();
	private Card firstCard;
	

	Deck(ArrayList<Hand> players) {
		for (int i = 0; i < 5; i++) {
			cardList.add(new Guard(players));
		}
		
		for (int i = 0; i < 2; i++) {
			cardList.add(new Baron(players));
			cardList.add(new Bishop(players));
			cardList.add(new Maid(players));
			cardList.add(new Prince(players));
		}
		
		cardList.add(new King(players));
		cardList.add(new Countess(players));
		cardList.add(new Princess(players));
		
		
		for(Card card: this.cardList) {	
			card.setDeck(this);
		}
		
		this.shuffle();
		firstCard = this.drawCard();
		
	}

	public void shuffle() {
		Collections.shuffle(cardList);
	}

	public Card drawCard() {

		if (this.cardList.size() > 0) {
			Card drawed = this.cardList.get(0);
			this.cardList.remove(0);
			return drawed;

		} else {
			return null;
		}
	}
	
	public int getDeckSize() {
		return this.cardList.size();
	}

	public Card getFirstCard() {
		return this.firstCard;
	}
	
}
