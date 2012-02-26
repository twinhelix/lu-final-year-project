package expert.eee;

import agent.IExpert;

/***
 * Variables needed:
 * 
 * A maximal set of consecutive stages during which the same expert is followed
 * is called a phase.
 * 
 * i = Phase number
 * 
 * phase (n) = number of phases expert e has been followed - a phase is the max
 * no. of stages expert is followed
 * 
 * stage (s) = the total number of stages during which expert e has been
 * followed
 * 
 * aveReward (m) = average reward from phases in which expert e has been
 * followed
 */
public class Advisor
{

	IExpert expert;
	double aveReward;
	int phase, stage;

	protected Advisor(IExpert e)
	{

		expert = e;
		aveReward = 0;
		phase = 0;
		stage = 0;
	}
}