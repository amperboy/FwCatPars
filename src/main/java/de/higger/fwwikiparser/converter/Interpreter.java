package de.higger.fwwikiparser.converter;


public interface Interpreter {
	
	public <I, O> O interpret(I in);
}
