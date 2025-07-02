package Cards;
import java.util.ArrayList;

import main.Deck;
import main.Hand;

public abstract class Card {

	private String name;
	private int rank;
	// 表になっているかのフラグ
	public boolean rFlag = true;
	protected ArrayList<Hand> players;
	//このカードを含んでいるデッキの情報
	protected Deck deck;
	
	public Card(String name, int rank, ArrayList<Hand> players) {
		this.name = name;
		this.rank = rank;
		this.players = players;
	}

	//カード効果のクラス
	//勝ち負けはフラグをいじることで決める
	//0は常時発動、1は捨てられた時のみ発動の効果
	public abstract void effect(Hand hand, int i);

	public String getName() {
		return name;
	}

	public int getRank() {
		return rank;
	}
	

	
	public void setFlgUp(boolean b) {
		rFlag = b;
	}
	
	public boolean getFlgUp() {
		return rFlag;
	}
	
	public void setDeck(Deck deck) {
		this.deck = deck;
	}
	
	public Deck getDeck() {
		return this.deck;
	}
	

	
	
	

}
