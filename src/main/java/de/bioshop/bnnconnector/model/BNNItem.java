package de.bioshop.bnnconnector.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.bioshop.bnnconnector.BNNConnector;
import de.bioshop.bnnconnector.model.BNN.BNNType;

public class BNNItem implements Map<Integer, Object> {
	public static final int COLUMN_COUNT = 69;
	public static int VK_FESTPREIS = 34;
	public static int WGGH = 18;
	public static int HÖHE = 31;
	public static int STAFFEL_MENGE = 40;
	public static int PFAND_NR_LADENEINHEIT = 26;
	public static int GRUNDPREIS_EINHEIT = 65;
	public static int MWST_KENNUNG = 33;
	public static int ERSATZ_ARTIKEL_NR = 19;
	public static int PREIS = 37;
	public static int MIN_BESTELL_MENGE = 20;
	public static int ÄNDERUNGSKENNUNG = 1;
	public static int MHD_RESTLAUFZEIT = 15;
	public static int KONTROLLSTELLE = 14;
	public static int ART_NR = 0;
	public static int WGIFH = 17;
	public static int ÄNDERUNGS_ZEIT = 3;
	public static int EAN_BESTELL = 5;
	public static int PFAND_NR_BESTELLEINHEIT = 27;
	public static int EMPF_VKGH = 36;
	public static int RABATTFÄHIG = 38;
	public static int BESTELLEINHEITS_MENGE = 22;
	public static int QUALITÄT = 13;
	public static int MENGENFAKTOR = 24;
	public static int HERSTELLER = 11;
	public static int BESTELLEINHEIT = 21;
	public static int GEWICHT_BESTELLEINHEIT = 29;
	public static int WG_BNN = 16;
	public static int STAFFEL_PREIS3 = 49;
	public static int STAFFEL_PREIS2 = 45;
	public static int STAFFEL_PREIS5 = 57;
	public static int STAFFEL_PREIS4 = 53;
	public static int SKONTIERFÄHIG3 = 51;
	public static int SKONTIERFÄHIG2 = 47;
	public static int SKONTIERFÄHIG5 = 59;
	public static int SKONTIERFÄHIG4 = 55;
	public static int LIEFERBAR_AB = 67;
	public static int LIEFERBAR_BIS = 68;
	public static int SKONTIERFÄHIG1 = 43;
	public static int EMPF_VK_AKTION = 64;
	public static int AKTIONSPREIS_GÜTIG_AB = 62;
	public static int EMPF_VK = 35;
	public static int HANDELSKLASSE = 9;
	public static int BEZEICHNUNG = 6;
	public static int RABATTFÄHIG5 = 58;
	public static int EAN_LADEN = 4;
	public static int GEWICHTSARTIKEL = 25;
	public static int RABATTFÄHIG4 = 54;
	public static int HERSTELLER_INVERKEHRBRINGER = 10;
	public static int RABATTFÄHIG3 = 50;
	public static int STAFFEL_PREIS = 41;
	public static int RABATTFÄHIG2 = 46;
	public static int BEZEICHNUNG3 = 8;
	public static int RABATTFÄHIG1 = 42;
	public static int BEZEICHNUNG2 = 7;
	public static int ARTIKELART = 60;
	public static int HERKUNFT = 12;
	public static int GRUNDPREIS_FAKTOR = 66;
	public static int GEWICHT_LADENEINHEIT = 28;
	public static int SKONTIERFÄHIG = 39;
	public static int STAFFEL_MENGE5 = 56;
	public static int STAFFEL_MENGE4 = 52;
	public static int BREITE = 30;
	public static int STAFFEL_MENGE3 = 48;
	public static int STAFFEL_MENGE2 = 44;
	public static int TIEFE = 32;
	public static int LADENEINHEIT = 23;
	public static int AKTIONSPREIS_GÜLTIG_BIS = 63;
	public static int ÄNDERUNGS_DATUM = 2;
	public static int AKTIONSPREIS = 61;

	// id of the item
	@BNN(id = 0, tableId = "Art.Nr.", required = true)
	private Long itemId;
	// id for the change of the item (N = new, A = change, X = removed, r = rest
	// stock, v = currently removed, w = readd an existing)
	@BNN(id = 1, tableId = "Änderungskennung", required = true)
	private Character changeId;
	@BNN(id = 2, tableId = "ÄnderungsDatum")
	private Long lastChangeDate;
	@BNN(id = 3, tableId = "ÄnderungsZeit")
	private Long lastChangeTime;
	@BNN(id = 4, tableId = "EANladen")
	private Long shopEAN;
	@BNN(id = 5, tableId = "EANbestell")
	private Long orderEAN;
	@BNN(id = 6, tableId = "Bezeichnung", required = true)
	private String description;
	@BNN(id = 7, tableId = "Bezeichnung2")
	private String description2;
	@BNN(id = 8, tableId = "Bezeichnung3")
	private String description3;
	@BNN(id = 9, tableId = "Handelsklasse")
	private String tradeClass;
	@BNN(id = 10, tableId = "Hersteller/Inverkehrbringer", required = true)
	private String manufacturer;
	@BNN(id = 11, tableId = "Hersteller")
	// if another as on the packing
	private String otherManufacturer;
	@BNN(id = 12, tableId = "Herkunft", required = true)
	private String origin;
	@BNN(id = 13, tableId = "Qualität", required = true)
	private String quality;
	@BNN(id = 14, tableId = "Kontrollstelle")
	private String controlAuthority;
	@BNN(id = 15, tableId = "MHD-Restlaufzeit")
	// in days max 4 characters
	private Integer MHD;
	@BNN(id = 16, tableId = "WG-BNN")
	private Integer WGBNN;
	@BNN(id = 17, tableId = "WG-IfH")
	private Integer WGIfH;
	@BNN(id = 18, tableId = "WG-GH")
	private Integer wggh;
	@BNN(id = 19, tableId = "ErsatzArtikelNr")
	private String replaceItemId;
	@BNN(id = 20, tableId = "MinBestellMenge")
	private Double minOrderCount;
	@BNN(id = 21, tableId = "Bestelleinheit", required = true)
	private String orderUnit;
	@BNN(id = 22, tableId = "Bestelleinheits-Menge", required = true)
	private Double orderUnitCount;
	@BNN(id = 23, tableId = "Ladeneinheit", required = true)
	private String shopUnit;
	@BNN(id = 24, tableId = "Mengenfaktor", required = true)
	private Double countFactor;
	@BNN(id = 25, tableId = "Gewichtsartikel")
	private Boolean onlyWeighCell;
	@BNN(id = 26, tableId = "PfandNrLadeneinheit", required = true)
	private Integer depositNumberShopUnit;
	@BNN(id = 27, tableId = "PfandNrBestelleinheit")
	private Integer depositNumberOrderUnit;
	@BNN(id = 28, tableId = "GewichtLadeneinheit")
	private Double weightShopUnit;
	@BNN(id = 29, tableId = "GewichtBestelleinheit", required = true)
	private Double weightOrderUnit;
	@BNN(id = 30, tableId = "Breite")
	private Integer width;
	@BNN(id = 31, tableId = "Höhe")
	private Integer height;
	@BNN(id = 32, tableId = "Tiefe")
	private Integer depth;
	@BNN(id = 33, tableId = "MwstKennung", required = true)
	private Integer mwstPassword;
	// price data
	@BNN(id = 34, tableId = "VKFestpreis")
	private Double fixedVK;
	@BNN(id = 35, tableId = "EmpfVk")
	private Double reciverVk;
	@BNN(id = 36, tableId = "EmpfVkGH")
	private Double suggestedVk;
	@BNN(id = 37, tableId = "Preis", required = true, type = BNNType.CURRENCY)
	private Double price;
	@BNN(id = 38, tableId = "Rabattfähig")
	private Boolean salable;
	@BNN(id = 39, tableId = "skontierfähig")
	private Boolean undoable;
	@BNN(id = 40, tableId = "staffelMenge")
	private Double packContentCount;
	@BNN(id = 41, tableId = "staffelPreis", type = BNNType.CURRENCY)
	private Double packPrice;
	@BNN(id = 42, tableId = "rabattfähig1")
	private Boolean salable1;
	@BNN(id = 43, tableId = "skontierfähig1")
	private Boolean undoable1;
	@BNN(id = 44, tableId = "staffelMenge2")
	private Double packContentCount2;
	@BNN(id = 45, tableId = "staffelPreis2", type = BNNType.CURRENCY)
	private Double packPrice2;
	@BNN(id = 46, tableId = "rabattfähig2")
	private Boolean salable2;
	@BNN(id = 47, tableId = "skontierfähig2")
	private Boolean undoable2;
	@BNN(id = 48, tableId = "staffelMenge3")
	private Double packContentCount3;
	@BNN(id = 49, tableId = "staffelPreis3", type = BNNType.CURRENCY)
	private Double packPrice3;
	@BNN(id = 50, tableId = "rabattfähig3")
	private Boolean salable3;
	@BNN(id = 51, tableId = "skontierfähig3")
	private Boolean undoable3;
	@BNN(id = 52, tableId = "staffelMenge4")
	private Double packContentCount4;
	@BNN(id = 53, tableId = "staffelPreis4", type = BNNType.CURRENCY)
	private Double packPrice4;
	@BNN(id = 54, tableId = "rabattfähig4")
	private Boolean salable4;
	@BNN(id = 55, tableId = "skontierfähig4")
	private Boolean undoable4;
	@BNN(id = 56, tableId = "staffelMenge5")
	private Double packContentCount5;
	@BNN(id = 57, tableId = "staffelPreis5", type = BNNType.CURRENCY)
	private Double packPrice5;
	@BNN(id = 58, tableId = "rabattfähig5")
	private Boolean salable5;
	@BNN(id = 59, tableId = "skontierfähig5")
	private Boolean undoable5;
	@BNN(id = 60, tableId = "Artikelart")
	private Character itemType;
	@BNN(id = 61, tableId = "Aktionspreis", type = BNNType.CURRENCY)
	private Double salePrice;
	@BNN(id = 62, tableId = "AktionspreisGütigAb")
	private Long salePriceFrom;
	@BNN(id = 63, tableId = "AktionspreisGültigBis")
	private Long salePriceTo;
	@BNN(id = 64, tableId = "empfVk-Aktion")
	private Double reciverVkSale;
	@BNN(id = 65, tableId = "Grundpreis-Einheit", required = true)
	private String basicPriceUnit;
	@BNN(id = 66, tableId = "Grundpreis-Faktor", required = true)
	private Double basicPriceFactor;
	@BNN(id = 67, tableId = "LieferbarAb")
	private Long orderableFrom;
	@BNN(id = 68, tableId = "LieferbarBis")
	private Long orderableTo;

	private Map<Integer, BNNRule<?>> bnnRules = new HashMap<Integer, BNNRule<?>>();

	public BNNItem(Long itemId, Character changeId, String description, String manufacturer, String origin,
			String quality, String orderUnit, Double orderUnitCount, String shopUnit, Double countFactor,
			Integer depositNumberShopUnit, Double weightOrderUnit, Integer mwstPassword, Double price,
			String basicPriceUnit, Double basicPriceFactor) {
		this.itemId = itemId;
		this.changeId = changeId;
		this.description = description;
		this.manufacturer = manufacturer;
		this.origin = origin;
		this.quality = quality;
		this.orderUnit = orderUnit;
		this.orderUnitCount = orderUnitCount;
		this.shopUnit = shopUnit;
		this.countFactor = countFactor;
		this.depositNumberShopUnit = depositNumberShopUnit;
		this.weightOrderUnit = weightOrderUnit;
		this.mwstPassword = mwstPassword;
		this.price = price;
		this.basicPriceUnit = basicPriceUnit;
		this.basicPriceFactor = basicPriceFactor;
	}

	public BNNItem(Long itemId, Character changeId, Long lastChangeDate, Long lastChangeTime, Long shopEAN,
			Long orderEAN, String description, String description2, String description3, String tradeClass,
			String manufacturer, String otherManufacturer, String origin, String quality, String controlAuthority,
			Integer MHD, Integer WGBNN, Integer WGIfH, Integer WGGH, String replaceItemId, Double minOrderCount,
			String orderUnit, Double orderUnitCount, String shopUnit, Double countFactor, Boolean onlyWeighCell,
			Integer depositNumberShopUnit, Integer depositNumberOrderUnit, Double weightShopUnit,
			Double weightOrderUnit, Integer width, Integer height, Integer depth, Integer mwstPassword, Double fixedVK,
			Double reciverVk, Double suggestedVk, Double price, Boolean salable, Boolean undoable,
			Double packContentCount, Double packPrice, Boolean salable1, Boolean undoable1, Double packContentCount2,
			Double packPrice2, Boolean salable2, Boolean undoable2, Double packContentCount3, Double packPrice3,
			Boolean salable3, Boolean undoable3, Double packContentCount4, Double packPrice4, Boolean salable4,
			Boolean undoable4, Double packContentCount5, Double packPrice5, Boolean salable5, Boolean undoable5,
			Character itemType, Double salePrice, Long salePriceFrom, Long salePriceTo, Double reciverVkSale,
			String basicPriceUnit, Double basicPriceFactor, Long orderableFrom, Long orderableTo) {
		this.itemId = itemId;
		this.changeId = changeId;
		this.lastChangeDate = lastChangeDate;
		this.lastChangeTime = lastChangeTime;
		this.shopEAN = shopEAN;
		this.orderEAN = orderEAN;
		this.description = description;
		this.description2 = description2;
		this.description3 = description3;
		this.tradeClass = tradeClass;
		this.manufacturer = manufacturer;
		this.otherManufacturer = otherManufacturer;
		this.origin = origin;
		this.quality = quality;
		this.controlAuthority = controlAuthority;
		this.MHD = MHD;
		this.WGBNN = WGBNN;
		this.WGIfH = WGIfH;
		this.wggh = WGGH;
		this.replaceItemId = replaceItemId;
		this.minOrderCount = minOrderCount;
		this.orderUnit = orderUnit;
		this.orderUnitCount = orderUnitCount;
		this.shopUnit = shopUnit;
		this.countFactor = countFactor;
		this.onlyWeighCell = onlyWeighCell;
		this.depositNumberShopUnit = depositNumberShopUnit;
		this.depositNumberOrderUnit = depositNumberOrderUnit;
		this.weightShopUnit = weightShopUnit;
		this.weightOrderUnit = weightOrderUnit;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.mwstPassword = mwstPassword;
		this.fixedVK = fixedVK;
		this.reciverVk = reciverVk;
		this.suggestedVk = suggestedVk;
		this.price = price;
		this.salable = salable;
		this.undoable = undoable;
		this.packContentCount = packContentCount;
		this.packPrice = packPrice;
		this.salable1 = salable1;
		this.undoable1 = undoable1;
		this.packContentCount2 = packContentCount2;
		this.packPrice2 = packPrice2;
		this.salable2 = salable2;
		this.undoable2 = undoable2;
		this.packContentCount3 = packContentCount3;
		this.packPrice3 = packPrice3;
		this.salable3 = salable3;
		this.undoable3 = undoable3;
		this.packContentCount4 = packContentCount4;
		this.packPrice4 = packPrice4;
		this.salable4 = salable4;
		this.undoable4 = undoable4;
		this.packContentCount5 = packContentCount5;
		this.packPrice5 = packPrice5;
		this.salable5 = salable5;
		this.undoable5 = undoable5;
		this.itemType = itemType;
		this.salePrice = salePrice;
		this.salePriceFrom = salePriceFrom;
		this.salePriceTo = salePriceTo;
		this.reciverVkSale = reciverVkSale;
		this.basicPriceUnit = basicPriceUnit;
		this.basicPriceFactor = basicPriceFactor;
		this.orderableFrom = orderableFrom;
		this.orderableTo = orderableTo;
	}

	public BNNItem(BNNItem bnn) {
		this(bnn.itemId, bnn.changeId, bnn.lastChangeDate, bnn.lastChangeTime, bnn.shopEAN, bnn.orderEAN,
				bnn.description, bnn.description2, bnn.description3, bnn.tradeClass, bnn.manufacturer,
				bnn.otherManufacturer, bnn.origin, bnn.quality, bnn.controlAuthority, bnn.MHD, bnn.WGBNN, bnn.WGIfH,
				bnn.wggh, bnn.replaceItemId, bnn.minOrderCount, bnn.orderUnit, bnn.orderUnitCount, bnn.shopUnit,
				bnn.countFactor, bnn.onlyWeighCell, bnn.depositNumberShopUnit, bnn.depositNumberOrderUnit,
				bnn.weightShopUnit, bnn.weightOrderUnit, bnn.width, bnn.height, bnn.depth, bnn.mwstPassword,
				bnn.fixedVK, bnn.reciverVk, bnn.suggestedVk, bnn.price, bnn.salable, bnn.undoable, bnn.packContentCount,
				bnn.packPrice, bnn.salable1, bnn.undoable1, bnn.packContentCount2, bnn.packPrice2, bnn.salable2,
				bnn.undoable2, bnn.packContentCount3, bnn.packPrice3, bnn.salable3, bnn.undoable3,
				bnn.packContentCount4, bnn.packPrice4, bnn.salable4, bnn.undoable4, bnn.packContentCount5,
				bnn.packPrice5, bnn.salable5, bnn.undoable5, bnn.itemType, bnn.salePrice, bnn.salePriceFrom,
				bnn.salePriceTo, bnn.reciverVkSale, bnn.basicPriceUnit, bnn.basicPriceFactor, bnn.orderableFrom,
				bnn.orderableTo);
	}

	public BNN getBNN(Object key) {
		int index = getId(key);
		if (index == -1)
			return null;
		return getBNNs()[index];
	}

	public Double getBasicPriceFactor() {
		return basicPriceFactor;
	}

	public String getBasicPriceUnit() {
		return basicPriceUnit;
	}

	public Double getFixedVK() {
		return fixedVK;
	}

	public Character getItemType() {
		return itemType;
	}

	public Long getOrderableFrom() {
		return orderableFrom;
	}

	public Long getOrderableTo() {
		return orderableTo;
	}

	public Double getPackContentCount() {
		return packContentCount;
	}

	public Double getPackContentCount2() {
		return packContentCount2;
	}

	public Double getPackContentCount3() {
		return packContentCount3;
	}

	public Double getPackContentCount4() {
		return packContentCount4;
	}

	public Double getPackContentCount5() {
		return packContentCount5;
	}

	public Double getPackPrice() {
		return packPrice;
	}

	public Double getPackPrice2() {
		return packPrice2;
	}

	public Double getPackPrice3() {
		return packPrice3;
	}

	public Double getPackPrice4() {
		return packPrice4;
	}

	public Double getPackPrice5() {
		return packPrice5;
	}

	public Double getPrice() {
		return price;
	}

	public Double getReciverVk() {
		return reciverVk;
	}

	public Double getReciverVkSale() {
		return reciverVkSale;
	}

	public Boolean getSalable() {
		return salable;
	}

	public Boolean getSalable1() {
		return salable1;
	}

	public Boolean getSalable2() {
		return salable2;
	}

	public Boolean getSalable3() {
		return salable3;
	}

	public Boolean getSalable4() {
		return salable4;
	}

	public Boolean getSalable5() {
		return salable5;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public Long getSalePriceFrom() {
		return salePriceFrom;
	}

	public Long getSalePriceTo() {
		return salePriceTo;
	}

	public Double getSuggestedVk() {
		return suggestedVk;
	}

	public Boolean getUndoable() {
		return undoable;
	}

	public Boolean getUndoable1() {
		return undoable1;
	}

	public Boolean getUndoable2() {
		return undoable2;
	}

	public Boolean getUndoable3() {
		return undoable3;
	}

	public Boolean getUndoable4() {
		return undoable4;
	}

	public Boolean getUndoable5() {
		return undoable5;
	}

	public Boolean isOnlyWeighCell() {
		return onlyWeighCell;
	}

	public BNNItem() {
		initRules();
	}

	private void initRules() {

	}

	public Map<Integer, BNNRule<?>> getBnnRules() {
		return bnnRules;
	}

	public Character getChangeId() {
		return changeId;
	}

	public String getControlAuthority() {
		return controlAuthority;
	}

	public Double getCountFactor() {
		return countFactor;
	}

	public Integer getDepositNumberOrderUnit() {
		return depositNumberOrderUnit;
	}

	public Integer getDepositNumberShopUnit() {
		return depositNumberShopUnit;
	}

	public Integer getDepth() {
		return depth;
	}

	public String getDescription() {
		return description;
	}

	public String getDescription2() {
		return description2;
	}

	public String getDescription3() {
		return description3;
	}

	public Integer getHeight() {
		return height;
	}

	public Long getItemId() {
		return itemId;
	}

	public Long getLastChangeDate() {
		return lastChangeDate;
	}

	public Long getLastChangeTime() {
		return lastChangeTime;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public Integer getMHD() {
		return MHD;
	}

	public Double getMinOrderCount() {
		return minOrderCount;
	}

	public Integer getMwstPassword() {
		return mwstPassword;
	}

	public Boolean getOnlyWeighCell() {
		return onlyWeighCell;
	}

	public Long getOrderEAN() {
		return orderEAN;
	}

	public String getOrderUnit() {
		return orderUnit;
	}

	public Double getOrderUnitCount() {
		return orderUnitCount;
	}

	public String getOrigin() {
		return origin;
	}

	public String getOtherManufacturer() {
		return otherManufacturer;
	}

	public String getQuality() {
		return quality;
	}

	public String getReplaceItemId() {
		return replaceItemId;
	}

	public Long getShopEAN() {
		return shopEAN;
	}

	public String getShopUnit() {
		return shopUnit;
	}

	public String getTradeClass() {
		return tradeClass;
	}

	public Double getWeightOrderUnit() {
		return weightOrderUnit;
	}

	public Double getWeightShopUnit() {
		return weightShopUnit;
	}

	public Integer getWGBNN() {
		return WGBNN;
	}

	public Integer getWGGH() {
		return WGGH;
	}

	public Integer getWGIfH() {
		return WGIfH;
	}

	public Integer getWidth() {
		return width;
	}

	public String[] getTableIds() {
		String[] ret = new String[getBNNs().length];
		for (BNN bnn : getBNNs()) {
			ret[bnn.id()] = bnn.tableId();
		}
		return ret;
	}

	public String getTableId(int index) {
		for (BNN bnn : getBNNs()) {
			if (bnn.id() == index)
				return bnn.tableId();
		}
		throw new IndexOutOfBoundsException("unknwn index " + index);
	}

	public int getTableIdIndex(String tableId) {
		if (tableId == null)
			return -1;
		for (BNN bnn : getBNNs()) {
			if (bnn.tableId().equals(tableId))
				return bnn.id();
		}
		return -1;
	}

	public boolean hasTableId(String tableId) {
		return getTableIdIndex(tableId) != -1;
	}

	@Override
	public String toString() {
		return BNNConnector.toString(this);
	}

	@Override
	public int size() {
		return size(false);
	}

	public int requiredSize() {
		return size(true);
	}

	private Map<Field, BNN> getFields() {
		Map<Field, BNN> ret = new HashMap<Field, BNN>();
		for (Field field : BNNItem.class.getDeclaredFields()) {
			BNN bnn = field.getAnnotation(BNN.class);
			if (bnn != null)
				ret.put(field, bnn);
		}
		return ret;
	}

	public BNN[] getBNNs() {
		ArrayList<BNN> bnns = new ArrayList<>(getFields().values());
		bnns.sort(new Comparator<BNN>() {
			@Override
			public int compare(BNN o1, BNN o2) {
				return o1.id() - o2.id();
			}
		});
		return bnns.toArray(new BNN[0]);
	}

	private int size(boolean required) {
		int size = 0;
		for (BNN bnn : getBNNs()) {
			if (!required || bnn.required())
				size++;
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() > 0;
	}

	@Override
	public boolean containsKey(Object key) {
		int id = getId(key);
		if (id == -1)
			return false;
		return getField(id) != null;
	}

	private int getId(Object key) {
		int id = -1;
		if (key instanceof Integer || key.getClass().equals(int.class)) {
			id = ((Integer) key).intValue();
		} else if (key instanceof String) {
			String tableId = (String) key;
			for (BNN bnn : getBNNs()) {
				if (bnn.tableId().equals(tableId))
					id = bnn.id();
			}
		}
		return id;
	}

	@Override
	public boolean containsValue(Object value) {
		for (Field field : getFields().keySet()) {
			field.setAccessible(true);
			try {
				if (Objects.deepEquals(field.get(this), value))
					return true;
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}

	@Override
	public Object get(Object key) {
		int index = getId(key);
		if (index == -1)
			return null;
		try {
			Field field = getField(index);
			return field.get(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object put(Integer key, Object value) {
		Field field = getField(key);
		if (field == null)
			throw new IndexOutOfBoundsException("index " + key + " is out of bounds");
		field.setAccessible(true);
		Object o;
		try {
			o = field.get(this);
			field.set(this, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return o;
	}

	private Field getField(Integer key) {
		for (Entry<Field, BNN> e : getFields().entrySet()) {
			if (e.getValue().id() == key.intValue())
				return e.getKey();
		}
		return null;
	}

	@Override
	public Object remove(Object key) {
		int index = getId(key);
		if (index == -1)
			return null;
		return put(index, null);
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Object> m) {
		for (Entry<? extends Integer, ? extends Object> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	@Override
	public void clear() {
		for (int id = 0;; id++) {
			Field field = getField(id);
			if (field == null)
				break;
			remove(id);
		}
	}

	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = new HashSet<>();
		for (BNN bnn : getBNNs()) {
			ret.add(bnn.id());
		}
		return ret;
	}

	@Override
	public Collection<Object> values() {
		Collection<Object> ret = new ArrayList<Object>();
		for (Field field : getFields().keySet()) {
			field.setAccessible(true);
			try {
				ret.add(field.get(this));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return ret;
	}

	@Override
	public Set<Entry<Integer, Object>> entrySet() {
		Set<Entry<Integer, Object>> ret = new HashSet<>();
		for (Entry<Field, BNN> e : getFields().entrySet()) {
			Field field = e.getKey();
			BNN bnn = e.getValue();
			ret.add(new Entry<Integer, Object>() {
				@Override
				public Integer getKey() {
					return bnn.id();
				}

				@Override
				public Object getValue() {
					try {
						return field.get(BNNItem.this);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}

				@Override
				public Object setValue(Object value) {
					return BNNItem.this.put(bnn.id(), value);
				}
			});
		}
		return ret;
	}

	public Field getFieldBy(Object key) {
		int index = getId(key);
		Field field = getField(index);
		return field;
	}
}
