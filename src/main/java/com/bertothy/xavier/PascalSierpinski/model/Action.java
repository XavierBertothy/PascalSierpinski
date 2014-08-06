package com.bertothy.xavier.PascalSierpinski.model;

public enum Action {
	MULTIPLE, EQUALS, FINISHING, STARTING, CONTAINING, SUPERIOR, INFERIOR;

	public static String[] getValues() {
		int total = Action.values().length;
		String[] array = new String[total];
		for (Action a: Action.values()){
			array[a.ordinal()]=a.toString();
		}
		return array;
	}
}
