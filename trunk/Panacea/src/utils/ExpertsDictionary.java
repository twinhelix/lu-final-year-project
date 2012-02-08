package utils;

import java.util.HashMap;
import java.util.Map;

import agent.IExpert;

public class ExpertsDictionary {
	private Map<String, IExpert> dictionary;
	private static ExpertsDictionary instance;
	private final double prob = 0.2;
	
	public static ExpertsDictionary getInstance() {
		if (instance == null){
			instance = new ExpertsDictionary();
		}
		return instance;
	}

	private ExpertsDictionary() {
		dictionary = new HashMap<String, IExpert>();
		
	}

	public IExpert getExpert(String name) {
		return dictionary.get(name);
	}

}
