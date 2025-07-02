package Cards;
import java.util.ArrayList;

import main.Hand;
import main.LoveLetter;

public class Maid extends Card{
	
	public Maid(ArrayList<Hand> players){
		super("侍女", 4, players);
	}
	
	public void effect(Hand hand, int in) {
		if(in == 1) {
			hand.efFlag = false;
			LoveLetter.slow();
			System.out.println(LoveLetter.green + "侍女：" + hand.getName() + "は次のターンまで他プレイヤーの影響を受けません" + LoveLetter.end);
			
		}
		
		
	}
	

}
