package main;

import java.util.ArrayList;

import Cards.Card;

public class Com extends Hand {

	public int[] exist = { 5, 2, 2, 2, 2, 1, 1, 1 };
	//兵士の推論に使うリスト。すでに兵士で当てたか否かを判定
	public boolean[][] guardList = {{false, false, false, false, false, false, false, false},
									{false, false, false, false, false, false, false, false},
									{false, false, false, false, false, false, false, false},
									{false, false, false, false, false, false, false, false}};
	

	public void reset() {
		this.lFlag = false;
		this.efFlag = true;
		this.flag = false;
		this.hands = new ArrayList<Card>();
		this.memory = new int[4];
		this.exist[0] = 5;
		for (int i = 1; i < 5; i++) {
			this.exist[i] = 2;
		}
		for (int i = 5; i < 8; i++) {
			this.exist[i] = 1;
		}
		
		for(int i = 0; i < 4; i++) {
			for(int j=0; j<8; j++) {
				this.guardList[i][j] = false;
			}
		}
		
	}

	public Com(int i) {
		super("Com" + i);
	}

	public Card getCard(int i) {
		return this.hands.get(i);
	}

	public void remove() {
		String str = "1";
		int i = 0;
		int rand = new java.util.Random().nextInt(100);

		if (this.hands.size() > 1) {
			if (this.getCard(0).getRank() >= this.getCard(1).getRank() && rand > 0) {
				str = "2";
			} else {
				str = "1";
			}
			i = Integer.parseInt(str) - 1;
			int n = Math.abs(i - 1);
			Card removed = this.hands.get(i);
			Card notRemoved = this.hands.get(n);
			if (this.hands.size() > 1) {
				//手札が弱めの時はあえて男爵を選択しない
				if (removed.getRank() == 3 && notRemoved.getRank() <= 4) {
					i = n;
				}

				if (notRemoved.getRank() == 3) {
					for (int j = 0; j < this.memory.length; j++) {
						if (this.memory[j] != 0 && this.memory[j] < removed.getRank()) {
							i = n;
							break;
						}
					}
				}

				//兵士より僧侶を優先的に使う
				if (removed.getRank() == 1 && notRemoved.getRank() == 2) {
					i = n;
				}
				if (notRemoved.getRank() == 5) {
					for (int j = 0; j < this.memory.length; j++) {
						//誰かが姫を持っていることを知っているなら王子を使う
						if (this.memory[j] == 8) {
							i = n;
							break;
						}
					}
				}

				//伯爵夫人は王や王子より優先して捨てる
				if (notRemoved.getRank() == 7 && (removed.getRank() == 6 || removed.getRank() == 5)) {
					i = n;
				}
				//姫は必ず手元に残す
				if (removed.getRank() == 8) {
					i = Math.abs(i - 1);
				}
			}
		}
		System.out.println(
				this.name + "は" + this.getCard(i).getName() + "(" + this.hands.get(i).getRank() + ")" + "を捨てました");
		this.remove("" + (i + 1));
	}

	//Com用にオーバーライドしたプレイヤー選択メゾット
	public int select(ArrayList<Hand> players) {
		int inNum;
		while (true) {
			inNum = new java.util.Random().nextInt(players.size());
			
			
			//姫を持ってそうな相手を積極的に狙いに行く
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).efFlag && this.memory[i] == 8 && !players.get(i).lFlag
						&& !players.get(i).equals(this)) {
					inNum = i;
					break;
				}
			}
			
			//侍女の効果が入っている相手は選ばない
			if(!checkEffectFlag()) {
				if(!players.get(inNum).efFlag) {
					continue;
				}
			}

			//自分自身を選べない
			if (!players.get(inNum).equals(this) && !players.get(inNum).lFlag) {
				System.out.println(this.getName() + "は" + players.get(inNum).getName() + "を選びました");
				break;
			}
		}
		return inNum;
	}

	// 自分自身を選べる選択メゾット（王子で使う）
	public int selectP(ArrayList<Hand> players) {
		int inNum;
		while (true) {
			inNum = new java.util.Random().nextInt(players.size());

			if (this.getCard(0).getRank() == 8 && players.get(inNum).equals(this)) {
				continue;
			}

			for (int i = 0; i < this.memory.length; i++) {
				//姫を持っていることを知っているなら、その相手に使う
				if (this.memory[i] == 8 && !players.get(i).lFlag && !players.get(i).equals(this)) {
					inNum = i;
					break;
				}
			}
			//侍女の効果が入っている相手は選ばない
			if(checkEffectFlag()) {
				if(!players.get(inNum).efFlag) {
					continue;
				}
			}
			
			if (!players.get(inNum).lFlag) {
				System.out.println(this.getName() + "は" + players.get(inNum).getName() + "を選びました");
				break;
			}
		}
		return inNum;
	}

	public int choice() {
		int inNum;
		while (true) {
			int rand = new java.util.Random().nextInt(10);
			inNum = new java.util.Random().nextInt(9);

			//1をはじく
			if (inNum == 1) {
				continue;
			}

			//4以上で自分と同じ数字は選ばない
			if (inNum == this.hands.get(0).getRank() && inNum >= 4) {
				continue;
			}

			//0は選べないのではじく
			if (inNum == 0) {
				continue;
			}

			//デッキにないカードは選ばない
			if (this.exist[inNum - 1] <= 0) {
				continue;
			}

			//自分と同じ数字はあまり狙わない
			if (inNum != this.hands.get(0).getRank() || rand == 0) {
				System.out.println(this.getName() + "は" + inNum + "を選びました");
				break;
			}
		}

		return inNum;
	}

	public void memorize(int player, int rank) {
		memory[player] = rank;
	}

	public void memoryCheck() {
		for (int i = 0; i < players.size(); i++) {
			int rank = this.memory[i];
			while (true) {
				if(rank == 0) {
					break;
				}else if (this.exist[rank - 1] == 0) {
					this.memory[i] = new java.util.Random().nextInt(9);
					if (this.memory[i] != 0) {
						break;
					} else {
						continue;
					}
				} else {
					break;
				}
			}

		}

	}
	
	
	//負けていない他のプレイヤーが全員侍女で守られている状態か否かを判定する
	public boolean checkEffectFlag() {
		for(Hand player: this.players) {
			if(player.efFlag && !player.equals(this) && !player.lFlag) {
				return false;
			}	
		}
		
		return true;
	}
	
	//プレイヤーがガードで当てた記憶をリセットする
	public void resetGuardList(int playerNum) {
		for(int i=0; i < 8; i++) {
			this.guardList[playerNum][i] = false;
		}
		
	}
	
	
	
	
	

}
