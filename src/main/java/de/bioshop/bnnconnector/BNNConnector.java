package de.bioshop.bnnconnector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.bioshop.bnnconnector.model.BNN;
import de.bioshop.bnnconnector.model.BNNFile;
import de.bioshop.bnnconnector.model.BNNInfo;
import de.bioshop.bnnconnector.model.BNNItem;
import de.bioshop.bnnconnector.model.BNNRule;
import de.bioshop.bnnconnector.model.InfoType;

public class BNNConnector {
	public static boolean IGNORE_REQUIRED = true;

	@SuppressWarnings("resource")
	public static BNNFile parseBNN(File file) throws IOException {
		BufferedReader csvReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), Charset.forName("IBM857")));
		List<String[]> data = new ArrayList<>();
		String row = "";
		while ((row = csvReader.readLine()) != null) {
			data.add(row.split(";"));
		}
		if (data.size() < 2)
			throw new IllegalArgumentException("A BNNFile needs a header and a last sentence");
		String[] header = data.get(0);
		String[] lastSentence = data.get(data.size() - 1);
		BNNFile bnn = parseBNN(header, lastSentence,
				Arrays.copyOfRange(data.toArray(new String[0][]), 1, data.size() - 1));
		csvReader.close();
		return bnn;
	}

	public static BNNFile parseBNN(String[] header, String[] lastSentence, String[][] data) {
		BNNFile file = new BNNFile();
		Class<?> clazz = file.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			BNNInfo info = field.getAnnotation(BNNInfo.class);
			if (info == null) {
				continue;
			}
			if (info != null) {
				int id = info.id();
				String value = info.infoType() == InfoType.HEADER ? header[id] : lastSentence[id];
				if (info.required() && value.equals("") && !IGNORE_REQUIRED)
					throw new IllegalArgumentException("header-info " + info.tableId() + " is required but not set");
				BNNRule<?> rule = getBNNRule(file, info);
				try {
					field.setAccessible(true);
					Object val = rule.convertIn(field.getType(), value);
					setField(file, field, val);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		for (int line = 0; line < data.length; line++) {
			String[] itemData = data[line];
			BNNItem item = new BNNItem();
			Class<?> type = item.getClass();
			for (Field field : type.getDeclaredFields()) {
				BNN bnn = field.getAnnotation(BNN.class);
				if (bnn == null || bnn.transientBNN())
					continue;
				int id = bnn.id();
				if (id >= itemData.length)
					continue;
				String value = itemData[id];
				if (value.equals("") && bnn.required() && !IGNORE_REQUIRED)
					throw new IllegalArgumentException(
							"info " + bnn.tableId() + " is required but nor set at line " + (line + 2));
				BNNRule<?> rule = getBNNRule(item, bnn);
				try {
					field.setAccessible(true);
					Object val = rule.convertIn(field.getType(), value);
					setField(item, field, val);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			file.add(item);
		}
		return file;
	}

	private static void setField(Object o, Field field, Object value)
			throws IllegalArgumentException, IllegalAccessException {
		if (field.getType().isPrimitive()) {
			if (field.getType().equals(int.class))
				field.setInt(o, value == null ? 0 : ((Integer) value).intValue());
			else if (field.getType().equals(long.class))
				field.setLong(o, value == null ? 0 : ((Long) value).longValue());
			else if (field.getType().equals(byte.class))
				field.setByte(o, value == null ? 0 : ((Byte) value).byteValue());
			else if (field.getType().equals(short.class))
				field.setShort(o, value == null ? 0 : ((Short) value).shortValue());
			else if (field.getType().equals(float.class))
				field.setFloat(o, value == null ? 0 : ((Float) value).floatValue());
			else if (field.getType().equals(double.class))
				field.setDouble(o, value == null ? 0 : ((Double) value).doubleValue());
			else if (field.getType().equals(boolean.class))
				field.setBoolean(o, value == null ? false : ((Boolean) value).booleanValue());
			else if (field.getType().equals(char.class))
				field.setChar(o, value == null ? 0 : ((Character) value).charValue());
		} else
			field.set(o, value);
	}

	public static BNNRule<?> getBNNRule(BNNFile file, BNNInfo info) {
		int id = info.id();
		BNNRule<?> rule = null;
		if (info.infoType() == InfoType.HEADER && file.getHeaderRules().containsKey(id)) {
			rule = file.getHeaderRules().get(id);
		} else if (info.infoType() == InfoType.LAST_SENTENCE && file.getLastSentenceRules().containsKey(id)) {
			rule = file.getLastSentenceRules().get(id);
		} else
			rule = BNNRule.DEFAULT;
		return rule;
	}

	public static BNNRule<?> getBNNRule(BNNItem item, BNN bnn) {
		int id = bnn.id();
		BNNRule<?> rule = null;
		if (item.getBnnRules().containsKey(id)) {
			rule = item.getBnnRules().get(id);
		} else
			rule = BNNRule.DEFAULT;
		return rule;
	}

	@SuppressWarnings("unchecked")
	public static <E> E parseField(Class<?> type, String value) {
		if (value.equals(""))
			return (E) null;
		if (type.equals(String.class)) {
			if (value.startsWith("\"") && value.endsWith("\"")) {
				String val = value.substring(1, value.length() - 1);
				Charset set = Charset.defaultCharset();
				try {
					// set = Charset.forName("ISO-8859-1");
				} catch (Exception e) {
				}
				val = new String(val.getBytes(set));
				return (E) val;
			}
			throw new IllegalArgumentException(value + " is not a string");
		} else if (type.equals(long.class) || type.equals(Long.class)) {
			if (value.matches("\"?\\d+\"?")) {
				return (E) Long
						.valueOf(value.startsWith("\"") && value.endsWith("\"") ? value.substring(1, value.length() - 1)
								: value);
			}
			throw new IllegalArgumentException(value + " is not a long");
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			if (value.matches("\"?\\d+\"?")) {
				return (E) Integer
						.valueOf(value.startsWith("\"") && value.endsWith("\"") ? value.substring(1, value.length() - 1)
								: value);
			}
			throw new IllegalArgumentException(value + " is not a long");
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			if (value.matches("\"?\\d+\\,\\d+\"?")) {
				return (E) Double.valueOf(value.replace(",", "."));
			} else if (value.matches("\\d+")) {
				return (E) Double.valueOf(value + ".0");
			}
			throw new IllegalArgumentException(value + " is not a double");
		} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
			if (value.matches("\"?J\"?") || value.matches("\"?N\"?")) {
				return (E) new Boolean(value.equals("J"));
			}
			throw new IllegalArgumentException(value + " is not a boolean");
		} else if (type.equals(char.class) || type.equals(Character.class)) {
			if (value.length() == 3)
				return (E) new Character(value.charAt(1));
			if (value.length() == 1) {
				return (E) new Character(value.charAt(0));
			}
			throw new IllegalArgumentException(value + " is not a character");
		}
		throw new IllegalArgumentException("Unknown type " + type.getCanonicalName());
	}

	public static String toString(Object e) {
		if (e instanceof String) {
			return "\"" + ((String) e) + "\"";
		} else if (e == null) {
			return "";
		} else if (e.getClass().equals(int.class) || e.getClass().equals(Integer.class)
				|| e.getClass().equals(long.class) || e.getClass().equals(Long.class)) {
			return e.toString();
		} else if (e.getClass().equals(double.class) || e.getClass().equals(Double.class)) {
			return e.toString().replace(".", ",");
		} else if (e.getClass().equals(boolean.class) || e.getClass().equals(Boolean.class)) {
			return (boolean) e ? "J" : "N";
		} else if (e.getClass().equals(char.class) || e.getClass().equals(Character.class)) {
			return new String(new char[] { ((Character) e).charValue() }, 0, 1);
		} else if (e.getClass().equals(BNNFile.class)) {
			String data = "";
			String header = "", lastSentence = "";
			BNNFile file = (BNNFile) e;
			Class<?> type = file.getClass();
			for (Field field : type.getDeclaredFields()) {
				BNNInfo info = field.getAnnotation(BNNInfo.class);
				if (info == null)
					continue;
				BNNRule<?> rule = getBNNRule(file, info);
				try {
					field.setAccessible(true);
					String toString = rule.convertEx(field.get(file), file.getUnicode());
					String add = toString + ";";
					if (info.infoType() == InfoType.HEADER) {
						header += add;
					} else
						lastSentence += add;
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
			data += header.substring(0, header.length() - 1);
			for (BNNItem item : file) {
				data += "\n" + item.toString();
			}
			data += "\n" + lastSentence.substring(0, lastSentence.length() - 1);
			return data;
		} else if (e instanceof BNNItem) {
			String ret = "";
			BNNItem item = (BNNItem) e;
			Class<?> type = item.getClass();
			for (Field field : type.getDeclaredFields()) {
				BNN bnn = field.getAnnotation(BNN.class);
				if (bnn == null || bnn.transientBNN())
					continue;
				BNNRule<?> rule = getBNNRule(item, bnn);
				try {
					field.setAccessible(true);
					String toString = rule.convertEx(field.get(item), "UTF-16");
					ret += toString + ";";
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
			return ret.substring(0, ret.length() - 1);
		}
		throw new ClassCastException();
	}

	public static void saveBNN(BNNFile file, File file2) throws IOException {
		String bnn = toString(file);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
		writer.write(bnn);
		writer.close();
	}

	public static String toString(Object e, String charset) {
		String string = toString(e);
		try {
			string = new String(string.getBytes(charset));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return string;
	}

}
