package Cards;

import java.util.ArrayList;

import main.Com;
import main.Hand;
import main.LoveLetter;

public class Baron extends Card {

	public Baron(ArrayList<Hand> players) {
		super("男爵", 3, players);
	}

	public void effect(Hand hand, int in) {
		if (in == 1) {
			if (players.get(0).equals(hand)) {
				System.out.println(LoveLetter.green + "男爵：手札を比べる相手を選んでください（Com番号）" + LoveLetter.end);
			}

			int num = hand.select(players);
			Hand selected = players.get(num);

			if (selected.efFlag) {

				System.out.println(selected.getName() + "と手札の大きさを比べます");

				if (players.get(0).equals(hand)) {

					LoveLetter.slow();
					System.out.println(hand.getName() + ":" + hand.getCard(0).getName());
					LoveLetter.slow();
					System.out.println(selected.getName() + ":" + selected.getCard(0).getName());
				}
				LoveLetter.slow();

				int playerNum = hand.getCard(0).getRank();
				int selectedNum = selected.getCard(0).getRank();

				if (playerNum > selectedNum) {
					selected.lFlag = true;
					System.out.println(LoveLetter.green + selected.getName() + "は脱落した" + LoveLetter.end);
					selected.showHand();
					infBaron(hand, selected.getCard(0).getRank());
				} else if (playerNum < selectedNum) {
					hand.lFlag = true;
					System.out.println(LoveLetter.green + hand.getName() + "は脱落した" + LoveLetter.end);
					hand.showHand();
					infBaron(selected, hand.getCard(0).getRank());
				} else {
					System.out.println("引き分けだった");
					int mem = hand.getCard(0).getRank();
					hand.memory[num] = mem;

					int player = 0;
					for (int i = 0; i < players.size(); i++) {
						if (players.get(i).equals(hand)) {
							player = i;
						}
						selected.memory[player] = mem;

					}
				}
			} else {
				System.out.println(LoveLetter.green + selected.getName() + "は侍女に守られているので効果がなかった" + LoveLetter.end);
			}
		}
	}

	public void infBaron(Hand hand, int rank) {
		int num = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(hand)) {
				num = i;
			}
		}
		for (Hand player : players) {
			while (true) {
				int rand = new java.util.Random().nextInt(8) + 1;
				if (player instanceof Com) {
					if (((Com) player).exist[rand - 1] == 0) {
						continue;
					}
				}
				if (rand >= rank && rand != player.getCard(0).getRank()) {
					player.memorize(num, rand);
					break;
				}
			}
		}
	}

}
