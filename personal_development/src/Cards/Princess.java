package Cards;
import java.util.ArrayList;

import main.Hand;
import main.LoveLetter;

public class Princess extends Card{
	
	public Princess(ArrayList<Hand> players){
		super("姫", 8,players);
	}
	
	public void effect(Hand hand, int j) {
		
		boolean flag = true;
		
		for(int i=0; i < hand.handTotal(); i++) {
			if(hand.getCard(i).getRank() == 8) {
				flag = false;
			}
		}
		
		if(flag) {
			hand.lFlag = true;
			System.out.println(LoveLetter.green +"姫：姫を捨てたので" + hand.getName() +"は負けです" + LoveLetter.end );
		}
		
	}
	

}
