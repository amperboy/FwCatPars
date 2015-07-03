package de.higger.fwwikiparser.converter;


public interface Expression<I, O> {
	
	O convert(I input);
}
