package eee;

import agent.IExpert;

/***
 * Variables needed:
 * 
 * A maximal set of consecutive stages during which the same expert is followed
 * is called a phase.
 * 
 * i = Phase number
 * 
 * ne = number of phases expert e has been followed - a phase is the max no. of
 * stages expert is followed
 * 
 * se = the total number of stages during which expert e has been followed
 * 
 * me = average reward from phases in which expert e has been followed
 */
public class Advisor {
	
	IExpert expert;
	double me;
	int ne, se;

	protected Advisor(IExpert e) {
		
		expert = e;
		me = 0;
		ne = 0;
		se = 0;
	}
}