package agent;

import environment.GameHistory;

public class BaseAgent implements IAgent {
	private IExpert expert;

	public BaseAgent(IExpert expert) {
		this.expert = expert;
	}

	@Override
	public boolean move(GameHistory history) {
		return expert.move(history);
	}

	@Override
	public int playerNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

}
/*
Tit For Tat		
Tit For Tat / Random	
Tit For Two Tats / Random		
Tit For Two Tats	
Naive Prober		
Remorseful Prober	
Naive Peace Maker		
True Peace Maker	
Random		
lways Defect	
Always Co-operate		
Grudger	
Pavlov		
Pavlov / Random	
Adaptive		
Gradual	
Suspicious Tit For Tat		
Soft Grudger	
	
*/