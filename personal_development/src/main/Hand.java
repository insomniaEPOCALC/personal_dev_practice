package main;

import java.util.ArrayList;

import Cards.Card;

public class Hand {

	protected ArrayList<Card> hands = new ArrayList<Card>();
	protected String name;
	protected ArrayList<Hand> players = new ArrayList<Hand>();
	protected int total;
	//勝利のフラグ
	public boolean flag;
	//負けのフラグ
	public boolean lFlag = false;
	//効果を受けるかのフラグ
	public boolean efFlag = true;

	//直前に見たカードを覚える
	public int[] memory = new int[4];

	public int point = 0;

	public Hand(String name) {
		this.name = name;
	}

	public void reset() {
		this.lFlag = false;
		this.efFlag = true;
		this.flag = false;
		this.hands = new ArrayList<Card>();
		this.memory = new int[players.size()];
	}

	public void drawCard(Deck deck) {
		Card card = deck.drawCard();

		if (card != null) {
			this.hands.add(card);
		}
	}

	public void setPlayers(ArrayList<Hand> players) {
		this.players = players;
	}

	public void addCard(Card card) {
		this.hands.add(card);
	}

	public void showHand() {
		this.showHand(false, 500L);
	}

	public String getName() {
		return this.name;
	}

	public int getTotal() {
		return this.total;
	}

	public Card getCard(int i) {
		return this.hands.get(i);
	}

	public int handTotal() {
		return this.hands.size();
	}

	public void showHand(boolean flg, long sec) {
		if (flg) {
			for (int i = 0; i < this.hands.size(); i++) {
				this.hands.get(i).setFlgUp(flg);
			}
		}
		System.out.println("【" + this.name + "の手札】");
		for (int i = 0; i < this.hands.size(); i++) {

			try {
				Thread.sleep(sec);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (this.hands.get(i).getFlgUp()) {
				System.out.println("⇒ " + this.hands.get(i).getName() + "(" + this.hands.get(i).getRank() + ")");
			} else {
				System.out.println("？？？？？？？");

			}
		}

	}

	//記憶するか否かのフラグがついている
	public boolean remove(String str, boolean flag) {
		while (true) {
			String input = str;

			if (input.matches("[1-2]")) {
				int inNum = Integer.parseInt(input) - 1;
				int notInNum = Math.abs(inNum - 1);

				Card removed = this.hands.get(inNum);
				Card notRemoved = this.hands.get(notInNum);
				if (notRemoved.getRank() == 7 && (removed.getRank() == 6 || removed.getRank() == 5)) {
					System.out.println(LoveLetter.green + "伯爵夫人：王(6)や王子(5)がある時、伯爵夫人を捨てなければなりません" + LoveLetter.end);
					return true;
				}
				this.hands.remove(inNum);
				
				int playerNum = 0;
				for(int i = 0; i < players.size(); i++) {
					if(players.get(i).equals(this)) {
						playerNum = i;
					}
				}
				
				//予想が外れていたら一回元に戻す
				for(Hand player : this.players) {
					if(player instanceof Com) {
						if(((Com) player).guardList[playerNum][removed.getRank() - 1]) {
							((Com) player).resetGuardList(playerNum);
						}
					}
				}
				
				removeMem(removed.getRank());

				removed.effect(this, 1);
				return false;
			} else {
				System.out.println(LoveLetter.red + "不正な入力値です。もう一度入力してください　" + LoveLetter.red);
				return true;
			}
		}
	}
	
	public boolean remove(String str) {
		boolean b = remove (str, true);
		return b;
	}
	

	//単純に捨てるだけ
	//memFlagで捨て札を覚えるか否かを設定
	public void justRemove(String input, boolean b, boolean memFlag) {

		int inNum = Integer.parseInt(input) - 1;
		Card removed = this.hands.get(inNum);
		this.hands.remove(inNum);
		
		int playerNum = 0;
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).equals(this)) {
				playerNum = i;
			}
		}
		
		//予想が外れていたら一回元に戻す
		for(Hand player : this.players) {
			if(player instanceof Com) {
				if(((Com) player).guardList[playerNum][removed.getRank() - 1]) {
					((Com) player).resetGuardList(playerNum);
				}
			}
		}
		
		if(memFlag) {
		removeMem(removed.getRank());
		}

		if (b) {
			System.out.println(this.getName() + "は" + removed.getName() + "を捨てました");
			removed.effect(this, 0);
		}
	}

	//特定のランクのカードが一枚デッキから無くなったことを知るメゾット
	public void removeMem(int rank) {
		for (int i = 1; i < this.players.size(); i++) {
			((Com) this.players.get(i)).exist[rank - 1]--;
		}
	}

	//選択メゾット
	public int select(ArrayList<Hand> players) {
		int inNum;

		while (true) {
			String input = new java.util.Scanner(System.in).nextLine();

			if (input.matches("[1-9]")) {
				inNum = Integer.parseInt(input);
				//いない人を選んだらNG
				if (inNum >= players.size() || players.get(inNum).lFlag) {
					System.out.println(LoveLetter.red + "不正な入力値です。もう一度入力してください" + LoveLetter.end);
					continue;
				}
				break;

			} else {
				System.out.println(LoveLetter.red + "不正な入力値です。もう一度入力してください" + LoveLetter.end);
				continue;
			}
		}
		return inNum;
	}

	//自分自身も選べる選択メゾット（王子で使う）
	public int selectP(ArrayList<Hand> players) {
		int inNum;
		while (true) {

			String input = new java.util.Scanner(System.in).nextLine();

			if (input.matches("[0-9]")) {
				inNum = Integer.parseInt(input);

				//いない人を選んだらNG
				if (inNum >= players.size() || players.get(inNum).lFlag) {
					System.out.println(LoveLetter.red + "不正な入力値です。もう一度入力してください" + LoveLetter.end);
					continue;
				}
				break;
			} else {
				System.out.println(LoveLetter.green + "不正な入力値です。もう一度入力してください" + LoveLetter.end);
				continue;
			}
		}
		return inNum;
	}

	public int choice() {
		int inNum;

		while (true) {
			String input = new java.util.Scanner(System.in).nextLine();

			if (input.matches("[1-8]")) {
				inNum = Integer.parseInt(input);
				break;

			} else {
				System.out.println(LoveLetter.red + "不正な入力値です。もう一度入力してください" + LoveLetter.end);
				continue;
			}
		}
		return inNum;
	}

	//Com用のダミー
	public void memorize(int in, int num) {

	}

	public void clearHands() {
		this.hands.clear();
	}

}
