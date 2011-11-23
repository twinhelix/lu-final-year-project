package engine;

import agent.IExpert;

public class RoundRobinEngine {
	private IExpert[] experts;

	public RoundRobinEngine(IExpert[] experts) {
		this.experts = experts;
	}

	public void run() {

		for (int i = 0; i < experts.length; i++) {
			for (int j = i + 1; j < experts.length; j++) {

			}
		}
	}
}
