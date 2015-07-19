package com.kve.stockquote;

public class StockInfo {


	private String daysLow 			= "";
	private String daysHigh			= "";
	private String yearLow 			= "";
	private String yearHigh 		= "";
	private String name 			="";
	private String lastTradePriceOnly = "";
	private String change 			= "";
	private String daysRange 		= "";

	public StockInfo(String daysLow, String daysHigh, String yearLow,
			String yearHigh, String name, String lastTradePriceOnly,
			String change, String daysRange) {
		super();
		this.daysLow = daysLow;
		this.daysHigh = daysHigh;
		this.yearLow = yearLow;
		this.yearHigh = yearHigh;
		this.name = name;
		this.lastTradePriceOnly = lastTradePriceOnly;
		this.change = change;
		this.daysRange = daysRange;
	}

	public String getDaysLow() {
		return daysLow;
	}

	public void setDaysLow(String daysLow) {
		this.daysLow = daysLow;
	}

	public String getDaysHigh() {
		return daysHigh;
	}

	public void setDaysHigh(String daysHigh) {
		this.daysHigh = daysHigh;
	}

	public String getYearLow() {
		return yearLow;
	}

	public void setYearLow(String yearLow) {
		this.yearLow = yearLow;
	}

	public String getYearHight() {
		return yearHigh;
	}

	public void setYearHight(String yearHight) {
		this.yearHigh = yearHight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastTradePriceOnly() {
		return lastTradePriceOnly;
	}

	public void setLastTradePriceOnly(String lastTradePriceOnly) {
		this.lastTradePriceOnly = lastTradePriceOnly;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getDaysRange() {
		return daysRange;
	}

	public void setDaysRange(String daysRange) {
		this.daysRange = daysRange;
	}


}
