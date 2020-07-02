package com.navigation.drawer.models;

/**
 * @author dipenp
 *
 */
public class Items {

	private String itemName;
	private String itemDesc;
	//private long iconId;
	private String iconId;

	public Items(String itemName, String itemDesc, String iconId) {
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.iconId = iconId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getIconId() {
		return iconId;
	}

	public void setIconId(String iconId) {
		this.iconId = iconId;
	}
}
