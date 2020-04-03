package de.bioshop.bnnconnector.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bioshop.bnnconnector.BNNConnector;

public class BNNFile extends ArrayList<BNNItem> {
	private static final long serialVersionUID = 1L;
	// first sentence
	@BNNInfo(id = 0, tableId = "Kennung")
	private String id;
	@BNNInfo(id = 1, tableId = "Version")
	private Integer version;
	@BNNInfo(id = 2, tableId = "Zeichensatz")
	private String unicode;
	@BNNInfo(id = 3, tableId = "Versenderadresse")
	private String name;
	@BNNInfo(id = 4, tableId = "Umfang")
	private Character scope;
	@BNNInfo(id = 5, tableId = "Inhalt", required = false)
	private String content;
	@BNNInfo(id = 6, tableId = "Preiswählung")
	private String currency;
	@BNNInfo(id = 7, tableId = "DatumAb", required = false)
	private Long from;
	@BNNInfo(id = 8, tableId = "DatumBis", required = false)
	private Long to;
	@BNNInfo(id = 9, tableId = "Abgabedatum")
	private Long dutyDate;
	@BNNInfo(id = 10, tableId = "Abgabezeit")
	private Long dutyTime;
	@BNNInfo(id = 11, tableId = "Dateizähler")
	private Long fileIndex;
	// last sentence
	@BNNInfo(id = 0, tableId = "Kennung", required = false, infoType = InfoType.LAST_SENTENCE)
	private String password1;
	@BNNInfo(id = 1, tableId = "Kennung", required = false, infoType = InfoType.LAST_SENTENCE)
	private String password2;
	@BNNInfo(id = 2, tableId = "Dateizähler", infoType = InfoType.LAST_SENTENCE)
	private Long nextFileIndex;

	public BNNFile(String id, Integer version, String unicode, String name, Character scope, String currency,
			Long dutyDate, Long dutyTime, Long fileIndex, Long nextFileIndex) {
		this();
		this.id = id;
		this.version = version;
		this.unicode = unicode;
		this.name = name;
		this.scope = scope;
		this.currency = currency;
		this.dutyDate = dutyDate;
		this.dutyTime = dutyTime;
		this.fileIndex = fileIndex;
		this.nextFileIndex = nextFileIndex;
	}

	private Map<Integer, BNNRule<?>> headerRules = new HashMap<Integer, BNNRule<?>>(),
			lastSentenceRules = new HashMap<>();

	public Map<Integer, BNNRule<?>> getHeaderRules() {
		return headerRules;
	}

	public Map<Integer, BNNRule<?>> getLastSentenceRules() {
		return lastSentenceRules;
	}

	public String getContent() {
		return content;
	}

	public String getCurrency() {
		return currency;
	}

	public Long getDutyDate() {
		return dutyDate;
	}

	public Long getDutyTime() {
		return dutyTime;
	}

	public Long getFileIndex() {
		return fileIndex;
	}

	public Long getFrom() {
		return from;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getNextFileIndex() {
		return nextFileIndex;
	}

	public String getPassword1() {
		return password1;
	}

	public String getPassword2() {
		return password2;
	}

	public Character getScope() {
		return scope;
	}

	public Long getTo() {
		return to;
	}

	public String getUnicode() {
		return unicode;
	}

	public Integer getVersion() {
		return version;
	}

	public BNNFile(String id, Integer version, String unicode, String name, Character scope, String content,
			String currency, Long from, Long to, Long dutyDate, Long dutyTime, Long fileIndex, String password1,
			String password2, Long nextFileIndex) {
		this();
		this.id = id;
		this.version = version;
		this.unicode = unicode;
		this.name = name;
		this.scope = scope;
		this.content = content;
		this.currency = currency;
		this.from = from;
		this.to = to;
		this.dutyDate = dutyDate;
		this.dutyTime = dutyTime;
		this.fileIndex = fileIndex;
		this.password1 = password1;
		this.password2 = password2;
		this.nextFileIndex = nextFileIndex;
	}

	public BNNFile() {
		super();
		initRules();
	}

	public BNNFile(BNNFile bnn) {
		this(bnn.id, bnn.version, bnn.unicode, bnn.name, bnn.scope, bnn.content, bnn.currency, bnn.from, bnn.to,
				bnn.dutyDate, bnn.dutyTime, bnn.fileIndex, bnn.password1, bnn.password2, bnn.nextFileIndex);
		addAll(bnn);
	}

	public List<BNNItem> getAll() {
		return new ArrayList<>(this);
	}

	private void initRules() {
		addHeaderRule(0, this::BNNRuleIn, this::BNNRuleEx);
		addHeaderRule(2, this::UnicodeRuleIn, this::UnicodeRuleEx);
	}

	private void addHeaderRule(int id, ConvertRunnableIn<?> in, ConvertRunnableEx ex) {
		headerRules.put(id, getRule(in, ex));
	}

	private <E> BNNRule<E> getRule(ConvertRunnableIn<?> in, ConvertRunnableEx ex) {
		return new BNNRule<E>() {

			@Override
			public String convertEx(Object object, String charset) {
				return ex.convert(object);
			}

			@SuppressWarnings("unchecked")
			@Override
			public E convertIn(Class<?> fieldType, String f) {
				return (E) in.convert(fieldType, f);
			}

		};
	}

	private interface ConvertRunnableIn<E> {
		public E convert(Class<?> type, String str);
	}

	private interface ConvertRunnableEx {
		public String convert(Object o);
	}

	@SuppressWarnings("unchecked")
	private <E> E BNNRuleIn(Class<?> fieldType, String string) {
		if (string.equals("BNN"))
			return (E) "BNN";
		throw new IllegalArgumentException();
	}

	private <E> String BNNRuleEx(E e) {
		return "BNN";
	}

	@SuppressWarnings("unchecked")
	private <E> E UnicodeRuleIn(Class<?> fieldType, String string) {
		if (string.equals("0") || string.equals("1"))
			return (E) (string.equals("0") ? "AscII" : "Ansi");
		throw new IllegalArgumentException();
	}

	private <E> String UnicodeRuleEx(E e) {
		return e == null ? "" : (e.equals("AscII") ? "0" : "1");
	}

	@Override
	public String toString() {
		return BNNConnector.toString(this);
	}
}
