package de.bioshop.bnnconnector.model;

import de.bioshop.bnnconnector.BNNConnector;

public interface DefaultRule<E> extends BNNRule<E> {

	@SuppressWarnings("unused")
	@Override
	public default String convertEx(Object e, String charset) {
		try {
			@SuppressWarnings("unchecked")
			E e2 = (E) e;
		} catch (ClassCastException ex) {
			throw new IllegalArgumentException();
		}
		return BNNConnector.toString(e, charset);
	}

}
