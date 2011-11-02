package expert;

import java.util.Stack;

import environment.GameHistory;

public class TitForTwoTatExpert extends AbstractExpert {

	public TitForTwoTatExpert(int playerNo) {
		super(playerNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean move(GameHistory history) {

		Stack<boolean[]> moves = (Stack<boolean[]>)history.getHistory();
		// Cooperate on first move
		
		// Copies the last move of the other player
		return false; //lastMove[playerNo % 2];
	}

}