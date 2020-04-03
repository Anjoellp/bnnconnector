package de.bioshop.bnnconnector.model;

import de.bioshop.bnnconnector.BNNConnector;

public interface BNNRule<E> {
	public static BNNRule<Object> DEFAULT = new BNNRule<Object>() {

		@Override
		public Object convertIn(Class<?> fieldType, String f) {
			return BNNConnector.parseField(fieldType, f);
		}

		@Override
		public String convertEx(Object object, String charset) {
			return BNNConnector.toString(object, charset);
		}
	};

	public E convertIn(Class<?> fieldType, String f);

	public String convertEx(Object object, String charset);
}
