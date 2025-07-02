package Cards;

import java.util.ArrayList;

import main.Hand;
import main.LoveLetter;

public class King extends Card {

	public King(ArrayList<Hand> players) {
		super("王", 6, players);
	}

	public void effect(Hand hand, int in) {
		if (in == 1) {
			
			if (players.get(0).equals(hand)) {
				System.out.println(LoveLetter.green + "王：手札を交換する相手を選んでください（Com番号）" + LoveLetter.end);
			}
			
			
			
			int inNum = hand.select(this.players);
			
			Hand selected = this.players.get(inNum);

			// 手札入れ替え
			if (selected.efFlag) {

				Card swapMe = hand.getCard(0);
				Card swapYou = selected.getCard(0);
				int swapNum = swapMe.getRank();
				hand.memorize(inNum, swapNum);
				
				int num = 0;
				for (int i = 0; i < players.size(); i++) {
					if (players.get(i).equals(hand)) {
						num = i;
					}
				}
				
				selected.memorize(num, swapYou.getRank());
				hand.justRemove("1", false, false);
				selected.justRemove("1", false, false);
				hand.addCard(swapYou);
				selected.addCard(swapMe);
				LoveLetter.slow();
				System.out.println("手札を入れ替えました");
			} else {
				System.out.println(LoveLetter.green + selected.getName() + "は侍女に守られているので効果がなかった" + LoveLetter.end);
			}

		}

	}
}
