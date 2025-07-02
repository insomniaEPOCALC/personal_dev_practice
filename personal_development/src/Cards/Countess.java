package Cards;

import java.util.ArrayList;

import main.Com;
import main.Hand;

public class Countess extends Card {

	public Countess(ArrayList<Hand> players) {
		super("伯爵夫人", 7, players);
	}

	public void effect(Hand hand, int j) {

		if (j == 1) {
			int num = 0;
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).equals(hand)) {
					num = i;
				}
			}
			if(!(hand instanceof Com)) {
				System.out.println( hand.getName() + "は伯爵夫人(7)を捨てました");
			}
			//伯爵夫人を捨てると、他プレイヤーに5以上であることがバレる
			for (Hand player : players) {
				while (true) {
					int rand = new java.util.Random().nextInt(8) + 1;
					if (rand >= 5 && rand != 7 && rand != player.getCard(0).getRank()) {
						player.memorize(num, rand);
						break;
					}
				}
			}

		}

	}

}
