package Cards;

import java.util.ArrayList;

import main.Com;
import main.Hand;
import main.LoveLetter;

public class Guard extends Card {

	public Guard(ArrayList<Hand> players) {
		super("兵士", 1, players);
	}

	public void effect(Hand hand, int in) {
		if (in == 1) {
			if (players.get(0).equals(hand)) {
				System.out.println(LoveLetter.green + "兵士：手札を当てる相手を選んでください（Com番号）" + LoveLetter.end);
			}
			int num = 0;
			int inNum = hand.select(this.players);

			Hand selected = this.players.get(inNum);
			// 手札を当てる
			if (selected.efFlag) {
				while (true) {
					if (!(hand instanceof Com)) {
						LoveLetter.slow();
						System.out.println(selected.getName() + "の手札を予想してください（2-8の数字で入力）");
					}

					if (hand.memory[inNum] != 0 && hand.memory[inNum] != 1 && hand instanceof Com) {
						num = hand.memory[inNum];
					} else {
						num = hand.choice();
					}
					
					if(hand instanceof Com) {
						if(((Com) hand).guardList[inNum][num - 1]) {
							continue;
						}
					}
					

					if (num != 1) {
						break;
					} else {
						if (!(hand instanceof Com)) {
							System.out.println(LoveLetter.red + "不正な入力です" + LoveLetter.end);
						}
					}
				}
				LoveLetter.slow();
				if (num == selected.getCard(0).getRank()) {
					System.out.println(LoveLetter.green + "正解！" + num + "が" + selected.getName() + "の手札でした。"
							+ selected.getName() + "は脱落した" + LoveLetter.end);
						hand.removeMem(num - 1);
					selected.lFlag = true;
				} else {
					System.out.println(LoveLetter.green + "不正解！" + num + "ではないようです" + LoveLetter.end);

					for (Hand player : players) {
						if (player instanceof Com) {
							((Com) player).guardList[inNum][num - 1] = true;
						}
					}

					//今回違かった数字以外を次回から当てに行く
					for (Hand player : players) {
						while (true) {
							int rand = new java.util.Random().nextInt(8) + 1;
							//Comはデッキにあるとわかっているカードだけを予想する
							//また、過去にGuardで当てた手札は使わない
							if (player instanceof Com) {
								if (((Com) player).exist[rand - 1] == 0 || ((Com) player).guardList[inNum][rand - 1]) {
									continue;
								}
							}
							if (rand != num && rand != player.getCard(0).getRank()) {
								player.memory[inNum] = rand;
								break;
							}
						}
					}

				}

				System.out.println();

			} else {
				System.out.println(LoveLetter.green + selected.getName() + "は侍女に守られているので効果がなかった" + LoveLetter.end);
			}

		}

	}
}
