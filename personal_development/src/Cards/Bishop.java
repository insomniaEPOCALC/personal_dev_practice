package Cards;

import java.util.ArrayList;

import main.Hand;
import main.LoveLetter;

public class Bishop extends Card {

	public Bishop(ArrayList<Hand> players) {
		super("僧侶", 2, players);
	}

	public void effect(Hand hand, int in) {
		if (in == 1) {
			if (players.get(0).equals(hand)) {
				System.out.println(LoveLetter.green + "僧侶：手札を見る相手を選んでください（Com番号）"+ LoveLetter.end);
			}

			int inNum = hand.select(this.players);
			Hand selected = this.players.get(inNum);

			LoveLetter.slow();
			// 手札を見る
			if (selected.efFlag) {

				if (players.get(0).equals(hand)) {
					
					System.out.println(selected.getName() + "の手札を公開します");
					LoveLetter.slow();
					selected.showHand();
					System.out.println();
				} else {
					
					System.out.println(selected.getName() + "の手札を見ています");
					LoveLetter.slow();
					hand.memorize(inNum, selected.getCard(0).getRank());
					
				}

			} else {
				System.out.println(LoveLetter.green + selected.getName() + "は侍女に守られているので効果がなかった"+ LoveLetter.end);
			}

		}

	}

}
