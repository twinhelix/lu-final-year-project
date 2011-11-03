package expert;

import environment.GameHistory;

public class DefectExpert extends AbstractExpert {

	public DefectExpert(int playerNo) {
		super(playerNo);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Defect Expert always defects no matter the situation regardless of
	 * history
	 * 
	 * @return
	 */
	public boolean move(GameHistory history) {
		return false;
	}
}
