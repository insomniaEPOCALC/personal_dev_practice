package Cards;

import java.util.ArrayList;

import main.Hand;
import main.LoveLetter;

public class Prince extends Card {

	public Prince(ArrayList<Hand> players) {
		super("王子", 5, players);
	}

	public void effect(Hand hand, int in) {
		if (in == 1) {
			
			if (players.get(0).equals(hand)) {
				System.out.println(LoveLetter.green +"王子：手札を捨てさせる相手を選んでください（Com番号、自分を選択するときは0）"+ LoveLetter.end);
			}
			
			
			int num = hand.selectP(players);

			Hand selected = players.get(num);
			if (selected.efFlag) {
				players.get(num).justRemove("1", true, true);
				
				if(deck.getDeckSize() > 0) {
				players.get(num).drawCard(deck);
				}else {
				players.get(num).addCard(deck.getFirstCard());	
				}
				
				//選ばれたのがプレイヤーだった場合はその後の手札も見せる
				if(players.get(0).equals(selected)) {
					selected.showHand();
				}
				
			} else {
				System.out.println(LoveLetter.green +selected.getName() + "は侍女に守られているので効果がなかった"+ LoveLetter.end);
			}
		}
	}

}
